import { create } from "zustand";
import type { CittaInterface } from "../interfaces/cittaInterface";
import { persist } from "zustand/middleware";
import { toast } from "react-toastify";

interface CittaStore {
  citta: CittaInterface[];
  loading: boolean;
  error: string | null;

  getCitta: () => Promise<void>;
}

export const useCittaStore = create<CittaStore>()(
  persist((set) => ({
    citta: [],
    loading: false,
    error: null,

    getCitta: async () => {
      try {
        set({
          loading: true,
          error: null,
        });
        const api_url = import.meta.env.VITE_API_URL;
        const response = await fetch(`${api_url}/citta`);
        if (!response.ok) {
          throw new Error(`HTTP ${response.status}`);
        }

        const data = await response.json();

        set({
          citta: data,
          loading: false,
        });
      } catch (error) {
        const errormsg =
          error instanceof Error ? error.message : "Errore sconosciuto";
        set({
          error: errormsg,
          loading: false,
          citta: [],
        });
        console.error("Errore durante la fetch:", errormsg);
        toast.error(errormsg);
      }
    },
  })),
);
