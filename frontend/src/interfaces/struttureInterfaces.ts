export interface Struttura {
  id: string;
  name: string;
  descrizione: string;
  tipologia: TipologiaStruttura;
  indirizzo: Indirizzo;
  città: { id: string; nome: string };
  raccoltaFoto: { id: number; url: string; posizione: number }[];
  telefono: string;
  email: string;
  sitoWebURL: string | null;
  attributiAggiuntivi: Record<string, unknown>;
  accessoDisabili: boolean;
  orariApertura: OrarioApertura[];
  businessOwner: { id: string } | null;
}

export interface Hotel extends Struttura {
  stelle: number | null;
  prezzoMedioNotte: number | null;
  wifi: boolean | null;
  parcheggioPrivato: boolean | null;
  piscina: boolean | null;
  animaliAmmessi: boolean | null;
}

// copre anche BAR_CAFFE e AGRITURISMO (stessa entity Java)
export interface Ristorante extends Struttura {
  specialita: string | null;
  fasciaPrezzoMedio: RangePrezzo | null;
  tipologiaCucina: TipologiaCucina | null;
  prenotazioniOnline: boolean | null;
  delivery: boolean | null;
}

export interface StabilimentoBalneare extends Struttura {
  tipoSpiaggia: TipoSpiaggia | null;
  prezzoOmbrellone: number | null;
  docciaPresente: boolean | null;
  barPresente: boolean | null;
  ristorazionePresente: boolean | null;
}

export interface Negozio extends Struttura {
  tipiMerce: TipoMerce[];
  spedizioni: boolean | null;
}

export interface Servizio extends Struttura {
  tipoServizio: TipoServizio | null;
  h24: boolean | null;
}

export interface Trasporto extends Struttura {
  tipoTrasporto: TipoTrasporto | null;
  pagamentoDigitale: boolean | null;
}

export interface AttrazionePrincipale extends Struttura {
  tipoAttrazione: TipoAttrazione | null;
  bigliettoEntrata: number | null;
}

export type StrutturaTipo =
  | Hotel
  | Ristorante
  | StabilimentoBalneare
  | Negozio
  | Servizio
  | Trasporto
  | AttrazionePrincipale;

import type {
  TipologiaStruttura,
  TipoAttrazione,
  TipoMerce,
  TipologiaCucina,
  TipoServizio,
  TipoSpiaggia,
  TipoTrasporto,
} from "./enumsInterfaces";

// Range prezzo (per Ristorante)
export interface RangePrezzo {
  prezzoMin: number;
  prezzoMax: number;
}

export interface OrarioApertura {
  giorno: string; // "Lunedì", "Martedì", etc.
  orarioInizio: string; // "09:00"
  orarioFine: string; // "18:00"
  chiuso: boolean; // Se il giorno è chiuso
  ordine:number
}

// Interfaccia UNICA che rispecchia StrutturaResponse del backend
export interface StrutturaResponse {
  // CAMPI BASE (sempre presenti)
  id: string;
  name: string;
  descrizione: string;
  tipologia: TipologiaStruttura;
  telefono: string;
  email: string;
  sitoWebURL?: string;
  accessoDisabili?: boolean;
  fotoUrls: string[];
  dataRegistrazione:string

  // ORARI DI APERTURA (opzionale)
  orariApertura?: OrarioApertura[];

  // CITTÀ (opzionali)
  cittaId?: string;
  cittaNome?: string;

  // INDIRIZZO (opzionali)
  via?: string;
  numeroCivico?: string;

  // ATTRAZIONE PRINCIPALE (opzionali)
  tipoAttrazione?: TipoAttrazione;
  bigliettoEntrata?: number;

  // HOTEL (opzionali)
  stelle?: number;
  prezzoMedioNotte?: number;
  wifi?: boolean;
  parcheggioPrivato?: boolean;
  piscina?: boolean;
  animaliAmmessi?: boolean;

  // NEGOZIO (opzionali)
  tipiMerce?: TipoMerce[];
  spedizioni?: boolean;

  // RISTORANTE (opzionali)
  specialita?: string;
  fasciaPrezzoMedio?: RangePrezzo;
  tipologiaCucina?: TipologiaCucina;
  prenotazioniOnline?: boolean;
  delivery?: boolean;

  // SERVIZIO (opzionali)
  tipoServizio?: TipoServizio;
  h24?: boolean;

  // STABILIMENTO BALNEARE (opzionali)
  tipoSpiaggia?: TipoSpiaggia;
  prezzoOmbrellone?: number;
  docciaPresente?: boolean;
  barPresente?: boolean;
  ristorazionePresente?: boolean;

  // TRASPORTO (opzionali)
  tipoTrasporto?: TipoTrasporto;
  pagamentoDigitale?: boolean;
}
