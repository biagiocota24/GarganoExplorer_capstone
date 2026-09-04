export type TipologiaStruttura = string;
export type TipologiaCucina = string;
export type TipoMerce = string;
export type TipoAttrazione = string;
export type TipoSpiaggia = string;
export type TipoServizio = string;
export type TipoTrasporto = string;

export interface IndirizzoPayload {
  via: string;
  numeroCivico: string;
  cap: string;
  citta: string;
}

export interface OrarioAperturaPayload {
  giorno: string;
  orarioInizio: string;
  orarioFine: string;
  chiuso: boolean;
  ordine: number;
}

export interface StrutturaBasePayload {
  name: string;
  descrizione: string;
  tipologia: TipologiaStruttura;
  indirizzo: IndirizzoPayload;
  cittaId: string;
  telefono: string;
  email: string;
  sitoWebURL?: string;
  accessoDisabili: boolean;
  orariApertura: OrarioAperturaPayload[];
  businessOwnerId: string;
  attributiAggiuntivi?: Record<string, unknown>;
}

export interface StrutturaCreatePayload extends StrutturaBasePayload {
  fotoUrls?: string[];

  // Hotel
  stelle?: number;
  prezzoMedioNotte?: number;
  wifi?: boolean;
  parcheggioPrivato?: boolean;
  piscina?: boolean;
  animaliAmmessi?: boolean;

  // Ristorante / Bar / Agriturismo
  specialita?: string;
  prezzoMin?: number;
  prezzoMax?: number;
  tipologiaCucina?: TipologiaCucina;
  prenotazioniOnline?: boolean;
  delivery?: boolean;

  // Negozio
  tipiMerce?: TipoMerce[];
  spedizioni?: boolean;

  // Attrazione
  tipoAttrazione?: TipoAttrazione;
  bigliettoEntrata?: number;

  // Spiaggia
  tipoSpiaggia?: TipoSpiaggia;
  prezzoOmbrellone?: number;
  docciaPresente?: boolean;
  barPresente?: boolean;
  ristorazionePresente?: boolean;

  // Servizio
  tipoServizio?: TipoServizio;
  h24?: boolean;

  // Trasporto
  tipoTrasporto?: TipoTrasporto;
  pagamentoDigitale?: boolean;
}
