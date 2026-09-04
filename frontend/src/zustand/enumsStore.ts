import { create } from "zustand";
import { persist } from "zustand/middleware";
import { toast } from "react-toastify";
import type {
  GiornoSettimata,
  Nazionalita,
  StatoAzione,
  StatoMessaggio,
  TipoAttrazione,
  TipoAzione,
  TipologiaCucina,
  TipologiaStruttura,
  TipoMerce,
  TipoServizio,
  TipoSpiaggia,
  TipoTrasporto,
} from "../interfaces/enumsInterfaces";

interface EnumsStore {
  NazionalitaEnums: Nazionalita[];
  giorniSettimana: GiornoSettimata[];
  statiAzione: StatoAzione[];
  statiMessaggio: StatoMessaggio[];
  tipiAttrazione: TipoAttrazione[];
  tipiAzione: TipoAzione[];
  tipiMerce: TipoMerce[];
  tipiServizio: TipoServizio[];
  tipiSpiaggia: TipoSpiaggia[];
  tipiTrasporto: TipoTrasporto[];
  tipologieCucina: TipologiaCucina[];
  tipologieStruttura: TipologiaStruttura[];

  loading: boolean;
  lastFetch: number | null;

  fetchAllEnums: () => Promise<void>;
}

const CACHE_TIME = 60 * 60 * 1000;

export const useEnumsStore = create<EnumsStore>()(
  persist(
    (set, get) => ({
      NazionalitaEnums: [],
      giorniSettimana: [],
      statiAzione: [],
      statiMessaggio: [],
      tipiAttrazione: [],
      tipiAzione: [],
      tipiMerce: [],
      tipiServizio: [],
      tipiSpiaggia: [],
      tipiTrasporto: [],
      tipologieCucina: [],
      tipologieStruttura: [],

      loading: false,
      lastFetch: null,

      fetchAllEnums: async () => {
        const { lastFetch } = get();
        const now = Date.now();

        if (lastFetch && now - lastFetch < CACHE_TIME) {
          return;
        }

        set({ loading: true });

        try {
          const api_url = import.meta.env.VITE_API_URL;
          const response = await fetch(`${api_url}/enums`);
          const data = await response.json();

          set({
            NazionalitaEnums: data.nazionalita || [],
            giorniSettimana: data.giorniSettimana || [],
            statiAzione: data.statiAzione || [],
            statiMessaggio: data.statiMessaggio || [],
            tipiAttrazione: data.tipiAttrazione || [],
            tipiAzione: data.tipiAzione || [],
            tipiMerce: data.tipiMerce || [],
            tipiServizio: data.tipiServizio || [],
            tipiSpiaggia: data.tipiSpiaggia || [],
            tipiTrasporto: data.tipiTrasporto || [],
            tipologieCucina: data.tipologieCucina || [],
            tipologieStruttura: data.tipologieStruttura || [],

            loading: false,
            lastFetch: now,
          });
        } catch (error) {
          const msg = error instanceof Error ? error.message : "Errore sconosciuto";
          console.error("Errore fetch enums:", msg);
          toast.error(msg);
          set({ loading: false });
        }
      },
    }),
    { name: "enum-storage" },
  ),
);
