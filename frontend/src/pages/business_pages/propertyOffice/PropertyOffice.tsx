import { useState, useEffect, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Button, Card, Form, Modal, Spinner } from "react-bootstrap";
import {
  FiHome,
  FiPhone,
  FiMail,
  FiMapPin,
  FiGlobe,
  FiClock,
  FiCamera,
  FiX,
  FiPlus,
  FiEdit,
  FiSave,
  FiTrash2,
  FiCheck,
  FiArrowLeft,
} from "react-icons/fi";
import { FaStar } from "react-icons/fa";
import { toast as toastify } from "react-toastify";
import { useCittaStore } from "../../../zustand/cittaStore";
import { useEnumsStore } from "../../../zustand/enumsStore";
import ToastNotification from "../../../components/componentiGenerali/ToastNotification";
import type { StrutturaResponse } from "../../../interfaces/struttureInterfaces";

type BackendOrario = {
  giorno: string;
  apertura: unknown;
  chiusura: unknown;
  chiuso: boolean;
  ordine?: number;
};
import "./propertyOffice.css";

const RISTORAZIONE = ["RISTORANTE", "BAR_CAFFE", "AGRITURISMO"];
const SPIAGGE = ["SPIAGGIA", "STABILIMENTO_BALNEARE"];
const GIORNI = [
  "LUNEDI",
  "MARTEDI",
  "MERCOLEDI",
  "GIOVEDI",
  "VENERDI",
  "SABATO",
  "DOMENICA",
];
const GIORNO_LABEL: Record<string, string> = {
  LUNEDI: "Lunedì",
  MARTEDI: "Martedì",
  MERCOLEDI: "Mercoledì",
  GIOVEDI: "Giovedì",
  VENERDI: "Venerdì",
  SABATO: "Sabato",
  DOMENICA: "Domenica",
};
const GIORNO_TO_KEY: Record<string, string> = {
  Lunedì: "LUNEDI",
  Martedì: "MARTEDI",
  Mercoledì: "MERCOLEDI",
  Giovedì: "GIOVEDI",
  Venerdì: "VENERDI",
  Sabato: "SABATO",
  Domenica: "DOMENICA",
  LUNEDI: "LUNEDI",
  MARTEDI: "MARTEDI",
  MERCOLEDI: "MERCOLEDI",
  GIOVEDI: "GIOVEDI",
  VENERDI: "VENERDI",
  SABATO: "SABATO",
  DOMENICA: "DOMENICA",
  MONDAY: "LUNEDI",
  TUESDAY: "MARTEDI",
  WEDNESDAY: "MERCOLEDI",
  THURSDAY: "GIOVEDI",
  FRIDAY: "VENERDI",
  SATURDAY: "SABATO",
  SUNDAY: "DOMENICA",
};

const parseTime = (t: unknown): string => {
  if (t == null) return "?";
  if (typeof t === "string") return t.slice(0, 5);
  if (Array.isArray(t))
    return `${String(t[0] ?? 0).padStart(2, "0")}:${String(t[1] ?? 0).padStart(2, "0")}`;
  return "?";
};

const parseTimeForInput = (t: unknown): string => {
  if (t == null) return "";
  if (typeof t === "string") return t.slice(0, 5);
  if (Array.isArray(t))
    return `${String(t[0] ?? 0).padStart(2, "0")}:${String(t[1] ?? 0).padStart(2, "0")}`;
  return "";
};

const extractLabel = (v: unknown): string => {
  if (!v) return "";
  if (typeof v === "string") return v;
  if (typeof v === "object" && v !== null && "label" in v)
    return (v as { label: string }).label;
  return String(v);
};

const getTipValue = (tip: unknown): string => {
  if (!tip) return "";
  if (typeof tip === "string") return tip;
  if (typeof tip === "object" && tip !== null && "value" in tip)
    return (tip as { value: string }).value;
  return "";
};

type FormState = {
  name: string;
  descrizione: string;
  tipologia: string;
  via: string;
  numeroCivico: string;
  cap: string;
  cittaId: string;
  telefono: string;
  email: string;
  sitoWebURL: string;
  accessoDisabili: boolean;
  orariApertura: {
    giorno: string;
    apertura: string;
    chiusura: string;
    chiuso: boolean;
  }[];
  stelle: string;
  prezzoMedioNotte: string;
  wifi: boolean;
  parcheggioPrivato: boolean;
  piscina: boolean;
  animaliAmmessi: boolean;
  specialita: string;
  prezzoMin: string;
  prezzoMax: string;
  tipologiaCucina: string;
  prenotazioniOnline: boolean;
  delivery: boolean;
  tipoSpiaggia: string;
  prezzoOmbrellone: string;
  docciaPresente: boolean;
  barPresente: boolean;
  ristorazionePresente: boolean;
  spedizioni: boolean;
  tipoServizio: string;
  h24: boolean;
  tipoTrasporto: string;
  pagamentoDigitale: boolean;
  tipoAttrazione: string;
  bigliettoEntrata: string;
};

const orarioDefault = (g: string) => ({
  giorno: g,
  apertura: g === "SABATO" || g === "DOMENICA" ? "10:00" : "09:00",
  chiusura: g === "SABATO" || g === "DOMENICA" ? "22:00" : "18:00",
  chiuso: false,
});

const buildForm = (
  s: StrutturaResponse,
): { form: FormState; tipiMerce: string[] } => {
  const orariMap: Record<
    string,
    { apertura: string; chiusura: string; chiuso: boolean }
  > = {};
  (s.orariApertura as unknown as BackendOrario[]).forEach((o) => {
    const key = GIORNO_TO_KEY[o.giorno] ?? o.giorno?.toUpperCase?.() ?? "";
    if (key) {
      orariMap[key] = {
        apertura: o.chiuso
          ? orarioDefault(key).apertura
          : parseTimeForInput(o.apertura),
        chiusura: o.chiuso
          ? orarioDefault(key).chiusura
          : parseTimeForInput(o.chiusura),
        chiuso: o.chiuso ?? false,
      };
    }
  });

  return {
    form: {
      name: s.name ?? "",
      descrizione: s.descrizione ?? "",
      tipologia: getTipValue(s.tipologia),
      via: s.via ?? "",
      numeroCivico: s.numeroCivico ?? "",
      cap: "",
      cittaId: s.cittaId ?? "",
      telefono: s.telefono ?? "",
      email: s.email ?? "",
      sitoWebURL: s.sitoWebURL ?? "",
      accessoDisabili: s.accessoDisabili ?? false,
      orariApertura: GIORNI.map((g) =>
        orariMap[g] ? { giorno: g, ...orariMap[g] } : orarioDefault(g),
      ),
      stelle: s.stelle != null ? String(s.stelle) : "",
      prezzoMedioNotte:
        s.prezzoMedioNotte != null ? String(s.prezzoMedioNotte) : "",
      wifi: s.wifi ?? false,
      parcheggioPrivato: s.parcheggioPrivato ?? false,
      piscina: s.piscina ?? false,
      animaliAmmessi: s.animaliAmmessi ?? false,
      specialita: s.specialita ?? "",
      prezzoMin:
        s.fasciaPrezzoMedio?.prezzoMin != null
          ? String(s.fasciaPrezzoMedio.prezzoMin)
          : "",
      prezzoMax:
        s.fasciaPrezzoMedio?.prezzoMax != null
          ? String(s.fasciaPrezzoMedio.prezzoMax)
          : "",
      tipologiaCucina: extractLabel(s.tipologiaCucina),
      prenotazioniOnline: s.prenotazioniOnline ?? false,
      delivery: s.delivery ?? false,
      tipoSpiaggia: extractLabel(s.tipoSpiaggia),
      prezzoOmbrellone:
        s.prezzoOmbrellone != null ? String(s.prezzoOmbrellone) : "",
      docciaPresente: s.docciaPresente ?? false,
      barPresente: s.barPresente ?? false,
      ristorazionePresente: s.ristorazionePresente ?? false,
      spedizioni: s.spedizioni ?? false,
      tipoServizio: extractLabel(s.tipoServizio),
      h24: s.h24 ?? false,
      tipoTrasporto: extractLabel(s.tipoTrasporto),
      pagamentoDigitale: s.pagamentoDigitale ?? false,
      tipoAttrazione: extractLabel(s.tipoAttrazione),
      bigliettoEntrata:
        s.bigliettoEntrata != null ? String(s.bigliettoEntrata) : "",
    },
    tipiMerce: (s.tipiMerce ?? []).map(extractLabel),
  };
};

const BoolCheck = ({
  name,
  label,
  checked,
  onChange,
}: {
  name: string;
  label: string;
  checked: boolean;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
}) => (
  <label className="po-check-label">
    <input type="checkbox" name={name} checked={checked} onChange={onChange} />
    {label}
  </label>
);

const Chip = ({
  ok,
  label,
}: {
  ok: boolean | null | undefined;
  label: string;
}) => (
  <span className={`po-chip ${ok ? "po-chip-ok" : "po-chip-no"}`}>
    {ok ? <FiCheck size={11} /> : <FiX size={11} />} {label}
  </span>
);

const PropertyOffice = () => {
  const { strutturaId } = useParams<{ strutturaId: string }>();
  const navigate = useNavigate();
  const { citta, getCitta } = useCittaStore();
  const {
    tipologieStruttura,
    tipiAttrazione,
    tipiMerce: tipiMerceEnum,
    tipiServizio,
    tipiSpiaggia,
    tipiTrasporto,
    tipologieCucina,
    fetchAllEnums,
  } = useEnumsStore();

  const [struttura, setStruttura] = useState<StrutturaResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [editing, setEditing] = useState(false);
  const [saving, setSaving] = useState(false);
  const [deleting, setDeleting] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [isUploading, setIsUploading] = useState(false);
  const [formData, setFormData] = useState<FormState | null>(null);
  const [fotoUrls, setFotoUrls] = useState<string[]>([]);
  const fotoUrlsRef = useRef<string[]>([]);
  const [tipiMerceSelezionati, setTipiMerceSelezionati] = useState<string[]>(
    [],
  );
  const [toast, setToast] = useState<{
    show: boolean;
    type: "success" | "error" | "info";
    title: string;
    message: string;
  }>({ show: false, type: "info", title: "", message: "" });

  const showToast = (
    type: "success" | "error" | "info",
    title: string,
    message: string,
  ) => setToast({ show: true, type, title, message });

  useEffect(() => {
    fetchAllEnums();
    getCitta();
  }, [fetchAllEnums, getCitta]);

  useEffect(() => {
    if (!strutturaId) return;
    (async () => {
      setLoading(true);
      try {
        const res = await fetch(
          `${import.meta.env.VITE_API_URL}/strutture/${strutturaId}`,
          { credentials: "include" },
        );
        if (!res.ok) throw new Error(`${res.status}`);
        const data: StrutturaResponse = await res.json();
        setStruttura(data);
        const { form, tipiMerce: tm } = buildForm(data);
        setFormData(form);
        setTipiMerceSelezionati(tm);
        fotoUrlsRef.current = data.fotoUrls ?? [];
        setFotoUrls([...fotoUrlsRef.current]);
      } catch {
        showToast("error", "Errore", "Impossibile caricare la struttura");
      } finally {
        setLoading(false);
      }
    })();
  }, [strutturaId]);

  /* ── Handlers ────────────────────────────── */
  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >,
  ) => {
    const { name, value, type } = e.target;
    const checked = (e.target as HTMLInputElement).checked;
    setFormData((prev) =>
      prev ? { ...prev, [name]: type === "checkbox" ? checked : value } : prev,
    );
  };

  const handleOrarioChange = (
    idx: number,
    field: string,
    value: string | boolean,
  ) => {
    setFormData((prev) => {
      if (!prev) return prev;
      const orari = [...prev.orariApertura];
      orari[idx] = { ...orari[idx], [field]: value };
      return { ...prev, orariApertura: orari };
    });
  };

  const handleTipoMerceToggle = (val: string) =>
    setTipiMerceSelezionati((prev) =>
      prev.includes(val) ? prev.filter((v) => v !== val) : [...prev, val],
    );

  const handleFileUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(e.target.files ?? []);
    if (!files.length) return;
    setIsUploading(true);
    for (const file of files) {
      const body = new FormData();
      body.append("file", file);
      body.append("upload_preset", "gargano_explorer");
      try {
        const res = await fetch(
          "https://api.cloudinary.com/v1_1/csamygr3/image/upload",
          { method: "POST", body },
        );
        const data = await res.json();
        if (data.secure_url) {
          fotoUrlsRef.current = [
            ...fotoUrlsRef.current,
            data.secure_url as string,
          ];
          setFotoUrls([...fotoUrlsRef.current]);
        } else {
          showToast("error", "Errore", data.error?.message ?? "Upload fallito");
        }
      } catch {
        showToast("error", "Errore", "Errore di rete durante l'upload");
      }
    }
    setIsUploading(false);
    e.target.value = "";
  };

  const handleStartEdit = () => {
    if (!struttura) return;
    const { form, tipiMerce: tm } = buildForm(struttura);
    setFormData(form);
    setTipiMerceSelezionati(tm);
    fotoUrlsRef.current = struttura.fotoUrls ?? [];
    setFotoUrls([...fotoUrlsRef.current]);
    setEditing(true);
  };

  const handleCancelEdit = () => setEditing(false);

  const handleSave = async () => {
    if (!formData || !struttura) return;
    setSaving(true);
    try {
      const cittaSelezionata = citta.find((c) => c.id === formData.cittaId);
      const payload: Record<string, unknown> = {
        name: formData.name,
        descrizione: formData.descrizione,
        tipologia: formData.tipologia,
        indirizzo: {
          via: formData.via,
          numeroCivico: formData.numeroCivico,
          cap: formData.cap,
          citta: cittaSelezionata?.name ?? "",
        },
        cittaId: formData.cittaId,
        telefono: formData.telefono,
        email: formData.email,
        sitoWebURL: formData.sitoWebURL || null,
        accessoDisabili: formData.accessoDisabili,
        orariApertura: formData.orariApertura.map((o, idx) => ({
          giorno: GIORNO_LABEL[o.giorno],
          apertura: o.chiuso ? null : `${o.apertura}:00`,
          chiusura: o.chiuso ? null : `${o.chiusura}:00`,
          chiuso: o.chiuso,
          ordine: idx,
        })),
        fotoUrls: [...fotoUrlsRef.current],
      };

      if (formData.tipologia === "HOTEL")
        Object.assign(payload, {
          stelle: formData.stelle ? Number(formData.stelle) : null,
          prezzoMedioNotte: formData.prezzoMedioNotte
            ? Number(formData.prezzoMedioNotte)
            : null,
          wifi: formData.wifi,
          parcheggioPrivato: formData.parcheggioPrivato,
          piscina: formData.piscina,
          animaliAmmessi: formData.animaliAmmessi,
        });
      if (RISTORAZIONE.includes(formData.tipologia))
        Object.assign(payload, {
          specialita: formData.specialita || "",
          prezzoMin: formData.prezzoMin ? Number(formData.prezzoMin) : null,
          prezzoMax: formData.prezzoMax ? Number(formData.prezzoMax) : null,
          tipologiaCucina: formData.tipologiaCucina || null,
          prenotazioniOnline: formData.prenotazioniOnline,
          delivery: formData.delivery,
        });
      if (SPIAGGE.includes(formData.tipologia))
        Object.assign(payload, {
          tipoSpiaggia: formData.tipoSpiaggia || null,
          prezzoOmbrellone: formData.prezzoOmbrellone
            ? Number(formData.prezzoOmbrellone)
            : null,
          docciaPresente: formData.docciaPresente,
          barPresente: formData.barPresente,
          ristorazionePresente: formData.ristorazionePresente,
        });
      if (formData.tipologia === "NEGOZIO")
        Object.assign(payload, {
          tipiMerce: tipiMerceSelezionati,
          spedizioni: formData.spedizioni,
        });
      if (formData.tipologia === "SERVIZIO")
        Object.assign(payload, {
          tipoServizio: formData.tipoServizio || null,
          h24: formData.h24,
        });
      if (formData.tipologia === "TRASPORTO")
        Object.assign(payload, {
          tipoTrasporto: formData.tipoTrasporto || null,
          pagamentoDigitale: formData.pagamentoDigitale,
        });
      if (formData.tipologia === "ATTRAZIONE_PRINCIPALE")
        Object.assign(payload, {
          tipoAttrazione: formData.tipoAttrazione || null,
          bigliettoEntrata:
            formData.bigliettoEntrata !== ""
              ? Number(formData.bigliettoEntrata)
              : null,
        });

      const res = await fetch(
        `${import.meta.env.VITE_API_URL}/strutture/${struttura.id}`,
        {
          method: "PATCH",
          headers: { "Content-Type": "application/json" },
          credentials: "include",
          body: JSON.stringify(payload),
        },
      );
      if (!res.ok) {
        const text = await res.text();
        const msg = text ? JSON.parse(text)?.message : null;
        showToast(
          "error",
          "Errore",
          msg ?? `${res.status} – Salvataggio fallito`,
        );
        return;
      }
      const updated: StrutturaResponse = await res.json();
      setStruttura(updated);
      setEditing(false);
      showToast("success", "Salvato", "Modifiche salvate con successo");
    } catch {
      showToast("error", "Errore", "Errore di rete");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = () => {
    if (!struttura) return;
    setShowDeleteModal(true);
  };

  const confirmDelete = async () => {
    if (!struttura) return;
    setShowDeleteModal(false);
    setDeleting(true);
    try {
      const res = await fetch(
        `${import.meta.env.VITE_API_URL}/strutture/${struttura.id}`,
        {
          method: "DELETE",
          credentials: "include",
        },
      );
      if (!res.ok) throw new Error(`${res.status}`);
      toastify.success("Struttura eliminata con successo");
      navigate("/business/dashboard");
    } catch {
      showToast("error", "Errore", "Impossibile eliminare la struttura");
      setDeleting(false);
    }
  };

  if (loading)
    return (
      <div className="po-page">
        <div className="po-feedback">
          <Spinner animation="border" style={{ color: "#1a7a96" }} />
          <span>Caricamento struttura…</span>
        </div>
      </div>
    );

  if (!struttura)
    return (
      <div className="po-page">
        <div className="po-feedback">
          <span>Struttura non trovata.</span>
          <button
            className="po-btn po-btn-outline"
            onClick={() => navigate("/business/home")}
          >
            <FiArrowLeft size={15} /> Torna alla home
          </button>
        </div>
      </div>
    );

  
  const activeTipologia =
    editing && formData ? formData.tipologia : getTipValue(struttura.tipologia);
  const tipLabel = extractLabel(struttura.tipologia) || activeTipologia;
  const isRistorazione = RISTORAZIONE.includes(activeTipologia);
  const isSpiaggia = SPIAGGE.includes(activeTipologia);

  
  const viewMode = (
    <>
      <Card className="po-card">
        <div className="po-card-header">
          <FiHome size={16} /> Informazioni generali
        </div>
        <Card.Body className="pt-3">
          <div className="po-info-grid">
            <div className="po-info-item">
              <span className="po-info-label">Nome</span>
              <span className="po-info-value">{struttura.name}</span>
            </div>
            <div className="po-info-item">
              <span className="po-info-label">Tipologia</span>
              <span className="po-info-value">{tipLabel}</span>
            </div>
            <div className="po-info-item">
              <span className="po-info-label">Città</span>
              <span className="po-info-value">
                {struttura.cittaNome ?? "—"}
              </span>
            </div>
            <div className="po-info-item">
              <span className="po-info-label">Indirizzo</span>
              <span className="po-info-value">
                {[struttura.via, struttura.numeroCivico]
                  .filter(Boolean)
                  .join(" ") || "—"}
              </span>
            </div>
          </div>
          <div className="po-chips">
            <Chip
              ok={struttura.accessoDisabili}
              label="Accessibile ai disabili"
            />
          </div>
          {struttura.descrizione && (
            <p
              style={{
                marginTop: "1rem",
                color: "#495057",
                fontSize: "0.9rem",
                lineHeight: 1.6,
              }}
            >
              {struttura.descrizione}
            </p>
          )}
        </Card.Body>
      </Card>

      <Card className="po-card">
        <div className="po-card-header">
          <FiPhone size={16} /> Contatti
        </div>
        <Card.Body className="pt-3">
          <div className="po-info-grid">
            <div className="po-info-item">
              <span className="po-info-label">Telefono</span>
              <span className="po-info-value">{struttura.telefono || "—"}</span>
            </div>
            <div className="po-info-item">
              <span className="po-info-label">Email</span>
              <span className="po-info-value">{struttura.email || "—"}</span>
            </div>
            {struttura.sitoWebURL && (
              <div className="po-info-item">
                <span className="po-info-label">Sito web</span>
                <span className="po-info-value">{struttura.sitoWebURL}</span>
              </div>
            )}
          </div>
        </Card.Body>
      </Card>

      <Card className="po-card">
        <div className="po-card-header">
          <FiClock size={16} /> Orari di apertura
        </div>
        <Card.Body className="pt-3">
          {struttura.orariApertura && struttura.orariApertura.length > 0 ? (
            <table className="po-orari-table">
              <tbody>
                {[...(struttura.orariApertura as unknown as BackendOrario[])]
                  .sort((a, b) => (a.ordine ?? 0) - (b.ordine ?? 0))
                  .map((o) => (
                    <tr key={o.giorno}>
                      <td className="po-giorno">{o.giorno}</td>
                      <td>
                        {o.chiuso ? (
                          <span className="po-orario-chiuso">Chiuso</span>
                        ) : (
                          <span className="po-orario-aperto">
                            {parseTime(o.apertura)} – {parseTime(o.chiusura)}
                          </span>
                        )}
                      </td>
                    </tr>
                  ))}
              </tbody>
            </table>
          ) : (
            <p className="text-muted small mb-0">Nessun orario impostato</p>
          )}
        </Card.Body>
      </Card>

      {getTipValue(struttura.tipologia) === "HOTEL" && (
        <Card className="po-card">
          <div className="po-card-header">
            <FaStar size={14} /> Dettagli Hotel
          </div>
          <Card.Body className="pt-3">
            <div className="po-info-grid">
              {struttura.stelle != null && (
                <div className="po-info-item">
                  <span className="po-info-label">Stelle</span>
                  <span className="po-info-value">
                    {"★".repeat(struttura.stelle)}
                  </span>
                </div>
              )}
              {struttura.prezzoMedioNotte != null && (
                <div className="po-info-item">
                  <span className="po-info-label">Prezzo medio/notte</span>
                  <span className="po-info-value">
                    € {struttura.prezzoMedioNotte}
                  </span>
                </div>
              )}
            </div>
            <div className="po-chips">
              <Chip ok={struttura.wifi} label="Wi-Fi" />
              <Chip ok={struttura.parcheggioPrivato} label="Parcheggio" />
              <Chip ok={struttura.piscina} label="Piscina" />
              <Chip ok={struttura.animaliAmmessi} label="Animali ammessi" />
            </div>
          </Card.Body>
        </Card>
      )}

      {RISTORAZIONE.includes(getTipValue(struttura.tipologia)) && (
        <Card className="po-card">
          <div className="po-card-header">🍽️ Dettagli Ristorazione</div>
          <Card.Body className="pt-3">
            <div className="po-info-grid">
              {struttura.specialita && (
                <div className="po-info-item">
                  <span className="po-info-label">Specialità</span>
                  <span className="po-info-value">{struttura.specialita}</span>
                </div>
              )}
              {struttura.tipologiaCucina && (
                <div className="po-info-item">
                  <span className="po-info-label">Cucina</span>
                  <span className="po-info-value">
                    {extractLabel(struttura.tipologiaCucina)}
                  </span>
                </div>
              )}
              {struttura.fasciaPrezzoMedio && (
                <div className="po-info-item">
                  <span className="po-info-label">Fascia prezzo</span>
                  <span className="po-info-value">
                    € {struttura.fasciaPrezzoMedio.prezzoMin} – €{" "}
                    {struttura.fasciaPrezzoMedio.prezzoMax}
                  </span>
                </div>
              )}
            </div>
            <div className="po-chips">
              <Chip
                ok={struttura.prenotazioniOnline}
                label="Prenotazioni online"
              />
              <Chip ok={struttura.delivery} label="Delivery" />
            </div>
          </Card.Body>
        </Card>
      )}

      {SPIAGGE.includes(getTipValue(struttura.tipologia)) && (
        <Card className="po-card">
          <div className="po-card-header">
            🏖️ Dettagli Spiaggia / Stabilimento
          </div>
          <Card.Body className="pt-3">
            <div className="po-info-grid">
              {struttura.tipoSpiaggia && (
                <div className="po-info-item">
                  <span className="po-info-label">Tipo spiaggia</span>
                  <span className="po-info-value">
                    {extractLabel(struttura.tipoSpiaggia)}
                  </span>
                </div>
              )}
              {struttura.prezzoOmbrellone != null && (
                <div className="po-info-item">
                  <span className="po-info-label">Ombrellone</span>
                  <span className="po-info-value">
                    € {struttura.prezzoOmbrellone}
                  </span>
                </div>
              )}
            </div>
            <div className="po-chips">
              <Chip ok={struttura.docciaPresente} label="Docce" />
              <Chip ok={struttura.barPresente} label="Bar" />
              <Chip ok={struttura.ristorazionePresente} label="Ristorazione" />
            </div>
          </Card.Body>
        </Card>
      )}

      {getTipValue(struttura.tipologia) === "NEGOZIO" && (
        <Card className="po-card">
          <div className="po-card-header">🏪 Dettagli Negozio</div>
          <Card.Body className="pt-3">
            {struttura.tipiMerce && struttura.tipiMerce.length > 0 && (
              <div
                className="po-chips"
                style={{ marginTop: 0, marginBottom: "0.5rem" }}
              >
                {struttura.tipiMerce.map((m, i) => (
                  <span key={i} className="po-chip po-chip-neutral">
                    {extractLabel(m)}
                  </span>
                ))}
              </div>
            )}
            <div className="po-chips" style={{ marginTop: 0 }}>
              <Chip ok={struttura.spedizioni} label="Spedizioni" />
            </div>
          </Card.Body>
        </Card>
      )}

      {getTipValue(struttura.tipologia) === "SERVIZIO" && (
        <Card className="po-card">
          <div className="po-card-header">🛎️ Dettagli Servizio</div>
          <Card.Body className="pt-3">
            <div className="po-info-grid">
              {struttura.tipoServizio && (
                <div className="po-info-item">
                  <span className="po-info-label">Tipo servizio</span>
                  <span className="po-info-value">
                    {extractLabel(struttura.tipoServizio)}
                  </span>
                </div>
              )}
            </div>
            <div className="po-chips">
              <Chip ok={struttura.h24} label="Aperto H24" />
            </div>
          </Card.Body>
        </Card>
      )}

      {getTipValue(struttura.tipologia) === "TRASPORTO" && (
        <Card className="po-card">
          <div className="po-card-header">🚗 Dettagli Trasporto</div>
          <Card.Body className="pt-3">
            <div className="po-info-grid">
              {struttura.tipoTrasporto && (
                <div className="po-info-item">
                  <span className="po-info-label">Tipo trasporto</span>
                  <span className="po-info-value">
                    {extractLabel(struttura.tipoTrasporto)}
                  </span>
                </div>
              )}
            </div>
            <div className="po-chips">
              <Chip
                ok={struttura.pagamentoDigitale}
                label="Pagamento digitale"
              />
            </div>
          </Card.Body>
        </Card>
      )}

      {getTipValue(struttura.tipologia) === "ATTRAZIONE_PRINCIPALE" && (
        <Card className="po-card">
          <div className="po-card-header">🎭 Dettagli Attrazione</div>
          <Card.Body className="pt-3">
            <div className="po-info-grid">
              {struttura.tipoAttrazione && (
                <div className="po-info-item">
                  <span className="po-info-label">Tipo attrazione</span>
                  <span className="po-info-value">
                    {extractLabel(struttura.tipoAttrazione)}
                  </span>
                </div>
              )}
              {struttura.bigliettoEntrata != null && (
                <div className="po-info-item">
                  <span className="po-info-label">Biglietto</span>
                  <span className="po-info-value">
                    {struttura.bigliettoEntrata === 0
                      ? "Gratuito"
                      : `€ ${struttura.bigliettoEntrata}`}
                  </span>
                </div>
              )}
            </div>
          </Card.Body>
        </Card>
      )}

      <Card className="po-card">
        <div className="po-card-header">
          <FiCamera size={16} /> Foto
        </div>
        <Card.Body className="pt-3">
          {struttura.fotoUrls && struttura.fotoUrls.length > 0 ? (
            <div className="po-foto-grid">
              {struttura.fotoUrls.map((url, i) => (
                <div key={i} className="po-foto-item">
                  <img src={url} alt={`foto-${i}`} />
                </div>
              ))}
            </div>
          ) : (
            <div className="po-foto-empty">
              <FiCamera size={28} />
              <p className="mb-0">Nessuna foto caricata</p>
            </div>
          )}
        </Card.Body>
      </Card>
    </>
  );

  /* ── Edit mode ───────────────────────────── */
  const editMode = formData ? (
    <div className="d-flex flex-column gap-4">
      <Card className="po-card">
        <div className="po-card-header">
          <FiHome size={16} /> Informazioni generali
        </div>
        <Card.Body className="d-flex flex-column gap-3 pt-3">
          <div className="po-input-wrap">
            <FiHome className="po-icon" size={15} />
            <Form.Control
              type="text"
              name="name"
              placeholder="Nome struttura"
              value={formData.name}
              onChange={handleChange}
              maxLength={200}
            />
          </div>
          <div>
            <Form.Control
              as="textarea"
              name="descrizione"
              rows={4}
              placeholder="Descrizione"
              value={formData.descrizione}
              onChange={handleChange}
              maxLength={1000}
            />
            <small className="text-muted">
              {formData.descrizione.length}/1000
            </small>
          </div>
          <Form.Group>
            <Form.Label className="fw-semibold small mb-1">
              Tipologia
            </Form.Label>
            <Form.Select
              name="tipologia"
              value={formData.tipologia}
              onChange={handleChange}
            >
              <option value="">Seleziona tipologia</option>
              {tipologieStruttura.map((tip) => (
                <option key={tip.value} value={tip.value}>
                  {tip.label}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
          <label className="po-check-label" style={{ width: "fit-content" }}>
            <input
              type="checkbox"
              name="accessoDisabili"
              checked={formData.accessoDisabili}
              onChange={handleChange}
            />
            ♿ Accessibile ai disabili
          </label>
        </Card.Body>
      </Card>

      <Card className="po-card">
        <div className="po-card-header">
          <FiPhone size={16} /> Contatti
        </div>
        <Card.Body className="d-flex flex-column gap-3 pt-3">
          <div className="po-input-wrap">
            <FiPhone className="po-icon" size={15} />
            <Form.Control
              type="tel"
              name="telefono"
              placeholder="Telefono"
              value={formData.telefono}
              onChange={handleChange}
            />
          </div>
          <div className="po-input-wrap">
            <FiMail className="po-icon" size={15} />
            <Form.Control
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleChange}
            />
          </div>
          <div className="po-input-wrap">
            <FiGlobe className="po-icon" size={15} />
            <Form.Control
              type="text"
              name="sitoWebURL"
              placeholder="Sito web"
              value={formData.sitoWebURL}
              onChange={handleChange}
            />
          </div>
        </Card.Body>
      </Card>

      <Card className="po-card">
        <div className="po-card-header">
          <FiMapPin size={16} /> Indirizzo
        </div>
        <Card.Body className="d-flex flex-column gap-3 pt-3">
          <Form.Control
            type="text"
            name="via"
            placeholder="Via / Strada"
            value={formData.via}
            onChange={handleChange}
          />
          <div className="d-flex gap-2">
            <Form.Control
              className="flex-fill"
              type="text"
              name="numeroCivico"
              placeholder="N° civico"
              value={formData.numeroCivico}
              onChange={handleChange}
            />
            <Form.Control
              style={{ width: "140px" }}
              type="text"
              name="cap"
              placeholder="CAP"
              value={formData.cap}
              onChange={handleChange}
            />
          </div>
          <Form.Select
            name="cittaId"
            value={formData.cittaId}
            onChange={handleChange}
          >
            <option value="">Seleziona città</option>
            {citta.map((c) => (
              <option key={c.id} value={c.id}>
                {c.name}
              </option>
            ))}
          </Form.Select>
        </Card.Body>
      </Card>

      <Card className="po-card">
        <div className="po-card-header">
          <FiClock size={16} /> Orari di apertura
        </div>
        <Card.Body className="pt-3">
          {formData.orariApertura.map((o, idx) => (
            <div key={o.giorno} className="po-orari-row">
              <span style={{ fontWeight: 600, fontSize: "0.9rem" }}>
                {GIORNO_LABEL[o.giorno]}
              </span>
              {o.chiuso ? (
                <span className="text-muted small fst-italic">Chiuso</span>
              ) : (
                <div className="po-orari-times">
                  <Form.Control
                    type="time"
                    size="sm"
                    value={o.apertura}
                    onChange={(e) =>
                      handleOrarioChange(idx, "apertura", e.target.value)
                    }
                    style={{ width: "110px" }}
                  />
                  <span>–</span>
                  <Form.Control
                    type="time"
                    size="sm"
                    value={o.chiusura}
                    onChange={(e) =>
                      handleOrarioChange(idx, "chiusura", e.target.value)
                    }
                    style={{ width: "110px" }}
                  />
                </div>
              )}
              <Form.Check
                type="switch"
                id={`po-chiuso-${idx}`}
                label="Chiuso"
                checked={o.chiuso}
                onChange={(e) =>
                  handleOrarioChange(idx, "chiuso", e.target.checked)
                }
                style={{ whiteSpace: "nowrap" }}
              />
            </div>
          ))}
        </Card.Body>
      </Card>

      {activeTipologia === "HOTEL" && (
        <Card className="po-card">
          <div className="po-card-header">
            <FaStar size={14} /> Hotel
          </div>
          <Card.Body className="d-flex flex-column gap-3 pt-3">
            <div className="d-flex gap-2">
              <Form.Group className="flex-fill">
                <Form.Label className="small fw-semibold">Stelle</Form.Label>
                <Form.Control
                  type="number"
                  name="stelle"
                  min="1"
                  max="5"
                  placeholder="1 – 5"
                  value={formData.stelle}
                  onChange={handleChange}
                />
              </Form.Group>
              <Form.Group className="flex-fill">
                <Form.Label className="small fw-semibold">
                  Prezzo medio/notte
                </Form.Label>
                <Form.Control
                  type="number"
                  name="prezzoMedioNotte"
                  min="0"
                  step="0.01"
                  placeholder="€"
                  value={formData.prezzoMedioNotte}
                  onChange={handleChange}
                />
              </Form.Group>
            </div>
            <div className="po-check-grid">
              <BoolCheck
                name="wifi"
                label="📶 Wi-Fi"
                checked={formData.wifi}
                onChange={handleChange}
              />
              <BoolCheck
                name="parcheggioPrivato"
                label="🅿️ Parcheggio"
                checked={formData.parcheggioPrivato}
                onChange={handleChange}
              />
              <BoolCheck
                name="piscina"
                label="🏊 Piscina"
                checked={formData.piscina}
                onChange={handleChange}
              />
              <BoolCheck
                name="animaliAmmessi"
                label="🐕 Animali ammessi"
                checked={formData.animaliAmmessi}
                onChange={handleChange}
              />
            </div>
          </Card.Body>
        </Card>
      )}

      {isRistorazione && (
        <Card className="po-card">
          <div className="po-card-header">🍽️ Ristorazione</div>
          <Card.Body className="d-flex flex-column gap-3 pt-3">
            <Form.Control
              type="text"
              name="specialita"
              placeholder="Specialità"
              value={formData.specialita}
              onChange={handleChange}
            />
            <div className="d-flex gap-2">
              <Form.Group className="flex-fill">
                <Form.Label className="small fw-semibold">
                  Prezzo min
                </Form.Label>
                <Form.Control
                  type="number"
                  name="prezzoMin"
                  min="0"
                  step="0.01"
                  placeholder="€"
                  value={formData.prezzoMin}
                  onChange={handleChange}
                />
              </Form.Group>
              <Form.Group className="flex-fill">
                <Form.Label className="small fw-semibold">
                  Prezzo max
                </Form.Label>
                <Form.Control
                  type="number"
                  name="prezzoMax"
                  min="0"
                  step="0.01"
                  placeholder="€"
                  value={formData.prezzoMax}
                  onChange={handleChange}
                />
              </Form.Group>
            </div>
            <Form.Select
              name="tipologiaCucina"
              value={formData.tipologiaCucina}
              onChange={handleChange}
            >
              <option value="">Tipologia cucina</option>
              {tipologieCucina.map((c) => (
                <option key={c.value} value={c.label}>
                  {c.label}
                </option>
              ))}
            </Form.Select>
            <div className="po-check-grid">
              <BoolCheck
                name="prenotazioniOnline"
                label="📅 Prenotazioni online"
                checked={formData.prenotazioniOnline}
                onChange={handleChange}
              />
              <BoolCheck
                name="delivery"
                label="🚚 Delivery"
                checked={formData.delivery}
                onChange={handleChange}
              />
            </div>
          </Card.Body>
        </Card>
      )}

      {isSpiaggia && (
        <Card className="po-card">
          <div className="po-card-header">🏖️ Spiaggia / Stabilimento</div>
          <Card.Body className="d-flex flex-column gap-3 pt-3">
            <div className="d-flex gap-2">
              <Form.Group className="flex-fill">
                <Form.Label className="small fw-semibold">
                  Tipo spiaggia
                </Form.Label>
                <Form.Select
                  name="tipoSpiaggia"
                  value={formData.tipoSpiaggia}
                  onChange={handleChange}
                >
                  <option value="">Seleziona</option>
                  {tipiSpiaggia.map((s) => (
                    <option key={s.value} value={s.label}>
                      {s.label}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
              <Form.Group className="flex-fill">
                <Form.Label className="small fw-semibold">
                  Prezzo ombrellone
                </Form.Label>
                <Form.Control
                  type="number"
                  name="prezzoOmbrellone"
                  min="0"
                  step="0.01"
                  placeholder="€"
                  value={formData.prezzoOmbrellone}
                  onChange={handleChange}
                />
              </Form.Group>
            </div>
            <div className="po-check-grid">
              <BoolCheck
                name="docciaPresente"
                label="🚿 Docce"
                checked={formData.docciaPresente}
                onChange={handleChange}
              />
              <BoolCheck
                name="barPresente"
                label="🍹 Bar"
                checked={formData.barPresente}
                onChange={handleChange}
              />
              <BoolCheck
                name="ristorazionePresente"
                label="🍽️ Ristorazione"
                checked={formData.ristorazionePresente}
                onChange={handleChange}
              />
            </div>
          </Card.Body>
        </Card>
      )}

      {activeTipologia === "NEGOZIO" && (
        <Card className="po-card">
          <div className="po-card-header">🏪 Negozio</div>
          <Card.Body className="d-flex flex-column gap-3 pt-3">
            <Form.Label className="small fw-semibold mb-0">
              Categorie merci
            </Form.Label>
            <div className="po-check-grid">
              {tipiMerceEnum.map((m) => (
                <label key={m.value} className="po-check-label">
                  <input
                    type="checkbox"
                    checked={tipiMerceSelezionati.includes(m.label)}
                    onChange={() => handleTipoMerceToggle(m.label)}
                  />
                  {m.label}
                </label>
              ))}
            </div>
            <BoolCheck
              name="spedizioni"
              label="📦 Spedizioni"
              checked={formData.spedizioni}
              onChange={handleChange}
            />
          </Card.Body>
        </Card>
      )}

      {activeTipologia === "SERVIZIO" && (
        <Card className="po-card">
          <div className="po-card-header">🛎️ Servizio</div>
          <Card.Body className="d-flex flex-column gap-3 pt-3">
            <Form.Select
              name="tipoServizio"
              value={formData.tipoServizio}
              onChange={handleChange}
            >
              <option value="">Tipo servizio</option>
              {tipiServizio.map((s) => (
                <option key={s.value} value={s.label}>
                  {s.label}
                </option>
              ))}
            </Form.Select>
            <BoolCheck
              name="h24"
              label="🕐 Aperto H24"
              checked={formData.h24}
              onChange={handleChange}
            />
          </Card.Body>
        </Card>
      )}

      {activeTipologia === "TRASPORTO" && (
        <Card className="po-card">
          <div className="po-card-header">🚗 Trasporto</div>
          <Card.Body className="d-flex flex-column gap-3 pt-3">
            <Form.Select
              name="tipoTrasporto"
              value={formData.tipoTrasporto}
              onChange={handleChange}
            >
              <option value="">Tipo trasporto</option>
              {tipiTrasporto.map((t2) => (
                <option key={t2.value} value={t2.label}>
                  {t2.label}
                </option>
              ))}
            </Form.Select>
            <BoolCheck
              name="pagamentoDigitale"
              label="💳 Pagamento digitale"
              checked={formData.pagamentoDigitale}
              onChange={handleChange}
            />
          </Card.Body>
        </Card>
      )}

      {activeTipologia === "ATTRAZIONE_PRINCIPALE" && (
        <Card className="po-card">
          <div className="po-card-header">🎭 Attrazione principale</div>
          <Card.Body className="d-flex flex-column gap-3 pt-3">
            <Form.Select
              name="tipoAttrazione"
              value={formData.tipoAttrazione}
              onChange={handleChange}
            >
              <option value="">Tipo attrazione</option>
              {tipiAttrazione.map((a) => (
                <option key={a.value} value={a.label}>
                  {a.label}
                </option>
              ))}
            </Form.Select>
            <Form.Group>
              <Form.Label className="small fw-semibold">
                Biglietto di entrata
              </Form.Label>
              <Form.Control
                type="number"
                name="bigliettoEntrata"
                min="0"
                step="0.01"
                placeholder="€ (0 = gratuito)"
                value={formData.bigliettoEntrata}
                onChange={handleChange}
              />
            </Form.Group>
          </Card.Body>
        </Card>
      )}

      <Card className="po-card">
        <div className="po-card-header">
          <FiCamera size={16} /> Foto
        </div>
        <Card.Body className="pt-3">
          <label
            className="po-upload-btn"
            style={{ cursor: isUploading ? "wait" : "pointer" }}
          >
            {isUploading ? (
              <>
                <Spinner
                  as="span"
                  size="sm"
                  animation="border"
                  className="me-1"
                />{" "}
                Caricamento…
              </>
            ) : (
              <>
                <FiPlus size={15} /> Aggiungi foto
              </>
            )}
            <input
              type="file"
              accept="image/*"
              multiple
              style={{ display: "none" }}
              onChange={handleFileUpload}
              disabled={isUploading}
            />
          </label>
          {fotoUrls.length === 0 ? (
            <div className="po-foto-empty">
              <FiCamera size={28} />
              <p className="mb-0">Nessuna foto caricata</p>
            </div>
          ) : (
            <div className="po-foto-grid">
              {fotoUrls.map((url, i) => (
                <div key={i} className="po-foto-item">
                  <img src={url} alt={`foto-${i}`} />
                  <button
                    type="button"
                    className="po-foto-remove"
                    onClick={() => {
                      fotoUrlsRef.current = fotoUrlsRef.current.filter(
                        (_, j) => j !== i,
                      );
                      setFotoUrls([...fotoUrlsRef.current]);
                    }}
                  >
                    <FiX size={12} />
                  </button>
                </div>
              ))}
            </div>
          )}
        </Card.Body>
      </Card>
    </div>
  ) : null;

  /* ── Render ──────────────────────────────── */
  return (
    <>
      <ToastNotification
        show={toast.show}
        type={toast.type}
        title={toast.title}
        message={toast.message}
        onClose={() => setToast((prev) => ({ ...prev, show: false }))}
      />
      <div className="po-page">
        <div className="po-container">
          <div className="po-header">
            <div className="po-header-info">
              <h1>{struttura.name}</h1>
              <span className="po-subtitle">
                {struttura.cittaNome ?? ""}
                {struttura.dataRegistrazione && (
                  <>
                    {" "}
                    · Registrata il{" "}
                    {new Date(struttura.dataRegistrazione).toLocaleDateString(
                      "it-IT",
                    )}
                  </>
                )}
              </span>
            </div>
            <div className="po-header-actions">
              {editing ? (
                <>
                  <button
                    type="button"
                    className="po-btn po-btn-outline"
                    onClick={handleCancelEdit}
                    disabled={saving}
                  >
                    <FiX size={15} /> Annulla
                  </button>
                  <button
                    type="button"
                    className="po-btn po-btn-primary"
                    onClick={handleSave}
                    disabled={saving}
                  >
                    {saving ? (
                      <Spinner as="span" size="sm" animation="border" />
                    ) : (
                      <FiSave size={15} />
                    )}
                    {saving ? " Salvataggio…" : " Salva modifiche"}
                  </button>
                </>
              ) : (
                <>
                  <button
                    type="button"
                    className="po-btn po-btn-danger"
                    onClick={handleDelete}
                    disabled={deleting}
                  >
                    {deleting ? (
                      <Spinner as="span" size="sm" animation="border" />
                    ) : (
                      <FiTrash2 size={15} />
                    )}
                    {deleting ? " Eliminazione…" : " Elimina"}
                  </button>
                  <button
                    type="button"
                    className="po-btn po-btn-primary"
                    onClick={handleStartEdit}
                  >
                    <FiEdit size={15} /> Modifica struttura
                  </button>
                </>
              )}
            </div>
          </div>

          {editing ? editMode : viewMode}
        </div>
      </div>

      <Modal show={showDeleteModal} onHide={() => setShowDeleteModal(false)} centered>
        <Modal.Header closeButton>
          <Modal.Title>Elimina struttura</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Eliminare definitivamente <strong>&ldquo;{struttura?.name}&rdquo;</strong>?
          L&apos;azione non è reversibile.
        </Modal.Body>
        <Modal.Footer>
          <Button variant="outline-secondary" onClick={() => setShowDeleteModal(false)}>
            Annulla
          </Button>
          <Button variant="danger" onClick={confirmDelete}>
            Elimina
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default PropertyOffice;
