import { create } from "zustand";
import type { StrutturaResponse } from "../interfaces/struttureInterfaces";
import { persist } from "zustand/middleware";
import { toast } from "react-toastify";

interface StruttureStore {
  strutture: StrutturaResponse[];
  loading: boolean;
  error: string | null;

  currentPage: number;
  totalPages: number;
  totalElements: number;
  pageSize: number;
  isFirst: boolean;
  isLast: boolean;

  mieStrutture: StrutturaResponse[];
  mieStruttureLoading: boolean;

  currentStruttura: StrutturaResponse | null;
  currentStrutturaLoading: boolean;

  getStrutture: (
    page?: number,
    size?: number,
    tipologia?: string,
    cittaId?: string,
  ) => Promise<void>;

  getAllStrutture: () => Promise<StrutturaResponse[]>;

  getStruttureByUser: (userId: string) => Promise<void>;

  getStrutturaById: (id: string) => Promise<void>;

  clearCurrentStruttura: () => void;

  patchStruttura: (
    id: string,
    payload: Record<string, unknown>,
  ) => Promise<StrutturaResponse | null>;

  deleteStruttura: (id: string) => Promise<boolean>;

  createStruttura: (
    payload: Record<string, unknown>,
  ) => Promise<StrutturaResponse | null>;
}

export const useStruttureStore = create<StruttureStore>()(
  persist(
    (set) => ({
      strutture: [],
      loading: false,
      error: null,

      currentPage: 0,
      totalPages: 0,
      totalElements: 0,
      pageSize: 12,
      isFirst: true,
      isLast: false,

      mieStrutture: [],
      mieStruttureLoading: false,

      currentStruttura: null,
      currentStrutturaLoading: false,

      getStrutture: async (
        page = 0,
        size?: number,
        tipologia?: string,
        cittaId?: string,
      ) => {
        const params = new URLSearchParams();
        if (page) params.append("page", page.toString());
        if (size) params.append("size", size.toString());
        if (tipologia) params.append("tipologia", tipologia);
        if (cittaId) params.append("cittaId", cittaId);

        set({ loading: true, error: null });
        try {
          const api_url = import.meta.env.VITE_API_URL;
          const response = await fetch(
            `${api_url}/strutture?${params.toString()}`,
          );

          if (!response.ok) throw new Error(`HTTP ${response.status}`);

          const data = await response.json();

          set({
            strutture: data.content || [],
            currentPage: data.number ?? 0,
            totalPages: data.totalPages ?? 0,
            totalElements: data.totalElements ?? 0,
            pageSize: data.size ?? 12,
            isFirst: data.first ?? true,
            isLast: data.last ?? false,
            loading: false,
          });
        } catch (error) {
          const errorMsg =
            error instanceof Error ? error.message : "Errore sconosciuto";
          console.error("Errore fetch strutture:", errorMsg);
          toast.error(errorMsg);
          set({ error: errorMsg, loading: false, strutture: [] });
        }
      },

      getAllStrutture: async (): Promise<StrutturaResponse[]> => {
        let allData: StrutturaResponse[] = [];
        let page = 0;
        let hasMore = true;

        set({ loading: true, error: null });

        try {
          const api_url = import.meta.env.VITE_API_URL;

          while (hasMore) {
            const response = await fetch(
              `${api_url}/strutture?page=${page}&size=100`,
            );

            if (!response.ok) throw new Error(`HTTP ${response.status}`);

            const data = await response.json();
            allData.push(...(data.content || []));
            hasMore = data.totalPages > page + 1;
            page++;
          }

          set({ strutture: allData, totalElements: allData.length, loading: false });
          return allData;
        } catch (error) {
          const errorMsg =
            error instanceof Error ? error.message : "Errore sconosciuto";
          console.error("Errore fetch tutte le strutture:", errorMsg);
          toast.error(errorMsg);
          set({ error: errorMsg, loading: false, strutture: [] });
          throw error;
        }
      },

      getStruttureByUser: async (userId: string) => {
        set({ mieStruttureLoading: true });
        try {
          const api_url = import.meta.env.VITE_API_URL;
          const response = await fetch(`${api_url}/strutture/user/${userId}`, {
            credentials: "include",
          });
          if (!response.ok) throw new Error(`HTTP ${response.status}`);
          const data: StrutturaResponse[] = await response.json();
          set({ mieStrutture: data, mieStruttureLoading: false });
        } catch (error) {
          const msg =
            error instanceof Error ? error.message : "Errore sconosciuto";
          console.error("Errore fetch strutture utente:", msg);
          toast.error(msg);
          set({ mieStruttureLoading: false });
        }
      },

      getStrutturaById: async (id: string) => {
        set({ currentStrutturaLoading: true, currentStruttura: null });
        try {
          const api_url = import.meta.env.VITE_API_URL;
          const response = await fetch(`${api_url}/strutture/${id}`, {
            credentials: "include",
          });
          if (!response.ok) throw new Error(`HTTP ${response.status}`);
          const data: StrutturaResponse = await response.json();
          set({ currentStruttura: data, currentStrutturaLoading: false });
        } catch (error) {
          const msg =
            error instanceof Error ? error.message : "Errore sconosciuto";
          console.error("Errore fetch struttura:", msg);
          toast.error(msg);
          set({ currentStrutturaLoading: false });
        }
      },

      clearCurrentStruttura: () => {
        set({ currentStruttura: null, currentStrutturaLoading: false });
      },

      patchStruttura: async (
        id: string,
        payload: Record<string, unknown>,
      ): Promise<StrutturaResponse | null> => {
        try {
          const api_url = import.meta.env.VITE_API_URL;
          const response = await fetch(`${api_url}/strutture/${id}`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(payload),
          });
          if (!response.ok) {
            const text = await response.text();
            const msg = text ? (JSON.parse(text) as { message?: string })?.message : null;
            toast.error(msg ?? `${response.status}`);
            return null;
          }
          const updated: StrutturaResponse = await response.json();
          set({ currentStruttura: updated });
          return updated;
        } catch (error) {
          const msg =
            error instanceof Error ? error.message : "Errore sconosciuto";
          console.error("Errore patch struttura:", msg);
          toast.error(msg);
          return null;
        }
      },

      deleteStruttura: async (id: string): Promise<boolean> => {
        try {
          const api_url = import.meta.env.VITE_API_URL;
          const response = await fetch(`${api_url}/strutture/${id}`, {
            method: "DELETE",
            credentials: "include",
          });
          if (!response.ok) throw new Error(`HTTP ${response.status}`);
          set({ currentStruttura: null });
          return true;
        } catch (error) {
          const msg =
            error instanceof Error ? error.message : "Errore sconosciuto";
          console.error("Errore delete struttura:", msg);
          toast.error(msg);
          return false;
        }
      },

      createStruttura: async (
        payload: Record<string, unknown>,
      ): Promise<StrutturaResponse | null> => {
        try {
          const api_url = import.meta.env.VITE_API_URL;
          const response = await fetch(`${api_url}/strutture`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(payload),
          });
          if (!response.ok) {
            const text = await response.text();
            const msg = text ? (JSON.parse(text) as { message?: string })?.message : null;
            toast.error(msg ?? `${response.status}`);
            return null;
          }
          return (await response.json()) as StrutturaResponse;
        } catch (error) {
          const msg =
            error instanceof Error ? error.message : "Errore sconosciuto";
          console.error("Errore crea struttura:", msg);
          toast.error(msg);
          return null;
        }
      },
    }),
    {
      name: "strutture-storage",
      partialize: (state) => ({
        strutture: state.strutture,
        currentPage: state.currentPage,
        totalPages: state.totalPages,
        totalElements: state.totalElements,
        pageSize: state.pageSize,
        isFirst: state.isFirst,
        isLast: state.isLast,
      }),
    },
  ),
);
