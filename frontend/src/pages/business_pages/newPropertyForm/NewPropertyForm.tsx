import { useState, useEffect, useRef } from "react";
import { Card, Form, Spinner } from "react-bootstrap";
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
} from "react-icons/fi";
import { FaStar } from "react-icons/fa";
import { useTranslation } from "react-i18next";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { useCittaStore } from "../../../zustand/cittaStore";
import { useEnumsStore } from "../../../zustand/enumsStore";
import { useAuthStore } from "../../../zustand/authStore";
import { useStruttureStore } from "../../../zustand/struttureStore";
import type { BusinessOwner } from "../../../interfaces/intefaces";
import "./newPropertyForm.css";

const RISTORAZIONE = ["RISTORANTE", "BAR_CAFFE", "AGRITURISMO"];

const iconStyle = {
  position: "absolute" as const,
  left: 12,
  top: "50%",
  transform: "translateY(-50%)",
  color: "#1a7a96",
  pointerEvents: "none" as const,
};

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

const orarioDefault = (g: string) => ({
  giorno: g,
  apertura: g === "SABATO" || g === "DOMENICA" ? "10:00" : "09:00",
  chiusura: g === "SABATO" || g === "DOMENICA" ? "22:00" : "18:00",
  chiuso: false,
});

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

const defaultForm = (): FormState => ({
  name: "",
  descrizione: "",
  tipologia: "",
  via: "",
  numeroCivico: "",
  cap: "",
  cittaId: "",
  telefono: "",
  email: "",
  sitoWebURL: "",
  accessoDisabili: false,
  orariApertura: GIORNI.map(orarioDefault),
  stelle: "",
  prezzoMedioNotte: "",
  wifi: false,
  parcheggioPrivato: false,
  piscina: false,
  animaliAmmessi: false,
  specialita: "",
  prezzoMin: "",
  prezzoMax: "",
  tipologiaCucina: "",
  prenotazioniOnline: false,
  delivery: false,
  tipoSpiaggia: "",
  prezzoOmbrellone: "",
  docciaPresente: false,
  barPresente: false,
  ristorazionePresente: false,
  spedizioni: false,
  tipoServizio: "",
  h24: false,
  tipoTrasporto: "",
  pagamentoDigitale: false,
  tipoAttrazione: "",
  bigliettoEntrata: "",
});

type BoolCheckProps = {
  name: string;
  label: string;
  checked: boolean;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
};

const BoolCheck = ({ name, label, checked, onChange }: BoolCheckProps) => (
  <label className="np-check-label">
    <input type="checkbox" name={name} checked={checked} onChange={onChange} />
    {label}
  </label>
);

const NewPropertyForm = function () {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { user } = useAuthStore();
  const { createStruttura } = useStruttureStore();
  const { citta, getCitta } = useCittaStore();
  const {
    tipologieStruttura,
    tipiAttrazione,
    tipiMerce,
    tipiServizio,
    tipiSpiaggia,
    tipiTrasporto,
    tipologieCucina,
    fetchAllEnums,
  } = useEnumsStore();

  const [formData, setFormData] = useState<FormState>(defaultForm);
  const [fotoUrls, setFotoUrls] = useState<string[]>([]);
  const fotoUrlsRef = useRef<string[]>([]);
  const [tipiMerceSelezionati, setTipiMerceSelezionati] = useState<string[]>(
    [],
  );
  const [isLoading, setIsLoading] = useState(false);
  const [isUploading, setIsUploading] = useState(false);
  const [errors, setErrors] = useState<Record<string, string>>({});

  useEffect(() => {
    fetchAllEnums();
    getCitta();
  }, [fetchAllEnums, getCitta]);

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >,
  ) => {
    const { name, value, type } = e.target;
    const checked = (e.target as HTMLInputElement).checked;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
    if (errors[name]) setErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const handleOrarioChange = (
    idx: number,
    field: string,
    value: string | boolean,
  ) => {
    setFormData((prev) => {
      const orari = [...prev.orariApertura];
      orari[idx] = { ...orari[idx], [field]: value };
      return { ...prev, orariApertura: orari };
    });
  };

  const handleTipologiaChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const tipologia = e.target.value;
    setFormData((prev) => ({ ...defaultForm(), ...prev, tipologia }));
    setTipiMerceSelezionati([]);
    if (errors.tipologia) setErrors((prev) => ({ ...prev, tipologia: "" }));
  };

  const handleTipoMerceToggle = (value: string) => {
    setTipiMerceSelezionati((prev) =>
      prev.includes(value) ? prev.filter((v) => v !== value) : [...prev, value],
    );
  };

  const handleFileUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(e.target.files ?? []);
    if (!files.length) return;
    setIsUploading(true);

    const urls: string[] = [];
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
          urls.push(data.secure_url as string);
        } else {
          toast.error(data.error?.message ?? t("newProperty.uploadFallito"));
        }
      } catch {
        toast.error(t("newProperty.erroreReteUpload"));
      }
    }

    if (urls.length) {
      fotoUrlsRef.current = [...fotoUrlsRef.current, ...urls];
      setFotoUrls([...fotoUrlsRef.current]);
    }

    setIsUploading(false);
    e.target.value = "";
  };

  const validate = (): boolean => {
    const e: Record<string, string> = {};
    if (!formData.name.trim()) e.name = t("newProperty.nomeObbligatorio");
    if (!formData.descrizione.trim())
      e.descrizione = t("newProperty.descrizioneObbligatoria");
    if (!formData.tipologia)
      e.tipologia = t("newProperty.tipologiaObbligatoria");
    if (!formData.via.trim()) e.via = t("newProperty.indirizzoObbligatorio");
    if (!formData.telefono) e.telefono = t("newProperty.telefonoObbligatorio");
    if (!formData.email) e.email = t("newProperty.emailObbligatoria");
    if (!formData.cittaId) e.cittaId = t("newProperty.cittaObbligatoria");
    setErrors(e);
    return Object.keys(e).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) {
      toast.error(t("Correggi gli errori nel modulo"));
      return;
    }
    setIsLoading(true);
    try {
      const businessOwner = user as BusinessOwner | null;
      const cittaSelezionata = citta.find((c) => c.id === formData.cittaId);

      const payload: Record<string, unknown> = {
        name: formData.name,
        descrizione: formData.descrizione,
        tipologia: formData.tipologia,
        indirizzo: {
          via: formData.via,
          numeroCivico: formData.numeroCivico,
          cap: formData.cap,
          citta: cittaSelezionata?.name || "", 
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
        businessOwnerId: businessOwner?.id ?? "",
      };

      //  HOTEL
      if (formData.tipologia === "HOTEL") {
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
      }

      //  RISTORAZIONE (ristorante, bar, agriturismo)
      if (RISTORAZIONE.includes(formData.tipologia)) {
        Object.assign(payload, {
          specialita: formData.specialita || "",
          prezzoMin: formData.prezzoMin ? Number(formData.prezzoMin) : null,
          prezzoMax: formData.prezzoMax ? Number(formData.prezzoMax) : null,
          tipologiaCucina: formData.tipologiaCucina || null,
          prenotazioniOnline: formData.prenotazioniOnline,
          delivery: formData.delivery,
        });
      }

      //  SPIAGGIA
      if (formData.tipologia === "SPIAGGIA") {
        Object.assign(payload, {
          tipoSpiaggia: formData.tipoSpiaggia || null,
          prezzoOmbrellone: formData.prezzoOmbrellone
            ? Number(formData.prezzoOmbrellone)
            : null,
          docciaPresente: formData.docciaPresente,
          barPresente: formData.barPresente,
          ristorazionePresente: formData.ristorazionePresente,
        });
      }

      //  STABILIMENTO BALNEARE
      if (formData.tipologia === "STABILIMENTO_BALNEARE") {
        Object.assign(payload, {
          tipoSpiaggia: formData.tipoSpiaggia || null,
          prezzoOmbrellone: formData.prezzoOmbrellone
            ? Number(formData.prezzoOmbrellone)
            : null,
          docciaPresente: formData.docciaPresente,
          barPresente: formData.barPresente,
          ristorazionePresente: formData.ristorazionePresente,
        });
      }

      //  NEGOZIO
      if (formData.tipologia === "NEGOZIO") {
        Object.assign(payload, {
          tipiMerce: tipiMerceSelezionati,
          spedizioni: formData.spedizioni,
        });
      }

      //  SERVIZIO
      if (formData.tipologia === "SERVIZIO") {
        Object.assign(payload, {
          tipoServizio: formData.tipoServizio || null,
          h24: formData.h24,
        });
      }

      //  TRASPORTO
      if (formData.tipologia === "TRASPORTO") {
        Object.assign(payload, {
          tipoTrasporto: formData.tipoTrasporto || null,
          pagamentoDigitale: formData.pagamentoDigitale,
        });
      }

      //  ATTRAZIONE
      if (formData.tipologia === "ATTRAZIONE_PRINCIPALE") {
        Object.assign(payload, {
          tipoAttrazione: formData.tipoAttrazione || null,
          bigliettoEntrata:
            formData.bigliettoEntrata !== ""
              ? Number(formData.bigliettoEntrata)
              : null,
        });
      }

      const result = await createStruttura(payload);
      if (result) {
        toast.success(t("newProperty.creataSuccesso"));
        setTimeout(() => navigate("/business/dashboard"), 2000);
      }
    } finally {
      setIsLoading(false);
    }
  };

  const isRistorazione = RISTORAZIONE.includes(formData.tipologia);

  return (
    <>
      <div className="np-page">
        <div className="np-container">
          <div className="np-header">
            <h2>{t("newProperty.titolo")}</h2>
            <p>{t("newProperty.sottotitolo")}</p>
          </div>

          <Form onSubmit={handleSubmit} className="d-flex flex-column gap-4">
            {/* GENERALI */}
            <Card className="np-card">
              <div className="np-card-header">
                <FiHome size={17} /> {t("newProperty.infoGenerali")}
              </div>
              <Card.Body className="d-flex flex-column gap-3 pt-3">
                <Form.Group>
                  <div className="np-input-wrap">
                    <FiHome style={iconStyle} size={15} />
                    <Form.Control
                      type="text"
                      name="name"
                      placeholder={t("newProperty.nome")}
                      value={formData.name}
                      onChange={handleChange}
                      isInvalid={!!errors.name}
                      maxLength={200}
                    />
                    <Form.Control.Feedback type="invalid">
                      {errors.name}
                    </Form.Control.Feedback>
                  </div>
                </Form.Group>

                <Form.Group>
                  <Form.Control
                    as="textarea"
                    name="descrizione"
                    rows={4}
                    placeholder={t("newProperty.descrizione")}
                    value={formData.descrizione}
                    onChange={handleChange}
                    isInvalid={!!errors.descrizione}
                    maxLength={1000}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.descrizione}
                  </Form.Control.Feedback>
                  <small className="text-muted">
                    {formData.descrizione.length}/1000
                  </small>
                </Form.Group>

                <Form.Group>
                  <Form.Label className="fw-semibold small mb-1">
                    {t("newProperty.tipologia")}
                  </Form.Label>
                  <Form.Select
                    name="tipologia"
                    value={formData.tipologia}
                    onChange={handleTipologiaChange}
                    isInvalid={!!errors.tipologia}
                  >
                    <option value="">
                      {t("newProperty.selezionaTipologia")}
                    </option>
                    {tipologieStruttura.map((tip) => (
                      <option key={tip.value} value={tip.value}>
                        {tip.label}
                      </option>
                    ))}
                  </Form.Select>
                  <Form.Control.Feedback type="invalid">
                    {errors.tipologia}
                  </Form.Control.Feedback>
                </Form.Group>
              </Card.Body>
            </Card>

            {/* CONTATTI */}
            <Card className="np-card">
              <div className="np-card-header">
                <FiPhone size={17} /> {t("newProperty.contatti")}
              </div>
              <Card.Body className="d-flex flex-column gap-3 pt-3">
                <Form.Group>
                  <div className="np-input-wrap">
                    <FiPhone style={iconStyle} size={15} />
                    <Form.Control
                      type="tel"
                      name="telefono"
                      placeholder={t("newProperty.telefono")}
                      value={formData.telefono}
                      onChange={handleChange}
                      isInvalid={!!errors.telefono}
                    />
                    <Form.Control.Feedback type="invalid">
                      {errors.telefono}
                    </Form.Control.Feedback>
                  </div>
                </Form.Group>

                <Form.Group>
                  <div className="np-input-wrap">
                    <FiMail style={iconStyle} size={15} />
                    <Form.Control
                      type="email"
                      name="email"
                      placeholder={t("login.email")}
                      value={formData.email}
                      onChange={handleChange}
                      isInvalid={!!errors.email}
                    />
                    <Form.Control.Feedback type="invalid">
                      {errors.email}
                    </Form.Control.Feedback>
                  </div>
                </Form.Group>

                <Form.Group>
                  <div className="np-input-wrap">
                    <FiGlobe style={iconStyle} size={15} />
                    <Form.Control
                      type="text"
                      name="sitoWebURL"
                      placeholder={t("newProperty.sitoWeb")}
                      value={formData.sitoWebURL}
                      onChange={handleChange}
                    />
                  </div>
                </Form.Group>
              </Card.Body>
            </Card>

            {/*  INDIRIZZO  */}
            <Card className="np-card">
              <div className="np-card-header">
                <FiMapPin size={17} /> {t("newProperty.indirizzo")}
              </div>
              <Card.Body className="d-flex flex-column gap-3 pt-3">
                <Form.Group>
                  <Form.Control
                    type="text"
                    name="via"
                    placeholder={t("newProperty.via")}
                    value={formData.via}
                    onChange={handleChange}
                    isInvalid={!!errors.via}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.via}
                  </Form.Control.Feedback>
                </Form.Group>

                <div className="d-flex gap-2">
                  <Form.Group className="flex-fill">
                    <Form.Control
                      type="text"
                      name="numeroCivico"
                      placeholder={t("newProperty.numeroCivico")}
                      value={formData.numeroCivico}
                      onChange={handleChange}
                    />
                  </Form.Group>
                  <Form.Group style={{ width: "140px" }}>
                    <Form.Control
                      type="text"
                      name="cap"
                      placeholder={t("newProperty.cap")}
                      value={formData.cap}
                      onChange={handleChange}
                    />
                  </Form.Group>
                </div>

                <Form.Group>
                  <Form.Select
                    name="cittaId"
                    value={formData.cittaId}
                    onChange={handleChange}
                    isInvalid={!!errors.cittaId}
                  >
                    <option value="">{t("newProperty.selezionaCitta")}</option>
                    {citta.map((c) => (
                      <option key={c.id} value={c.id}>
                        {c.name}
                      </option>
                    ))}
                  </Form.Select>
                  <Form.Control.Feedback type="invalid">
                    {errors.cittaId}
                  </Form.Control.Feedback>
                </Form.Group>

                <label
                  className="np-check-label"
                  style={{ width: "fit-content" }}
                >
                  <input
                    type="checkbox"
                    name="accessoDisabili"
                    checked={formData.accessoDisabili}
                    onChange={handleChange}
                  />
                  ♿ {t("newProperty.accessibile")}
                </label>
              </Card.Body>
            </Card>

            {/*  ORARI  */}
            <Card className="np-card">
              <div className="np-card-header">
                <FiClock size={17} /> {t("newProperty.orari")}
              </div>
              <Card.Body className="pt-3">
                {formData.orariApertura.map((o, idx) => (
                  <div key={o.giorno} className="np-orari-row">
                    <span className="np-orari-day">
                      {GIORNO_LABEL[o.giorno]}
                    </span>
                    {o.chiuso ? (
                      <span className="text-muted small fst-italic">
                        {t("newProperty.chiuso")}
                      </span>
                    ) : (
                      <div className="np-orari-times">
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
                      id={`chiuso-${idx}`}
                      label={t("newProperty.chiuso")}
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

            {/*  HOTEL  */}
            {formData.tipologia === "HOTEL" && (
              <Card className="np-card">
                <div className="np-card-header">
                  <FaStar size={15} /> {t("newProperty.hotel.titolo")}
                </div>
                <Card.Body className="d-flex flex-column gap-3 pt-3">
                  <div className="d-flex gap-2">
                    <Form.Group className="flex-fill">
                      <Form.Label className="small fw-semibold">
                        {t("newProperty.hotel.stelle")}
                      </Form.Label>
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
                        {t("newProperty.hotel.prezzoNotte")}
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
                  <div className="np-check-grid">
                    <BoolCheck
                      name="wifi"
                      label={`📶 ${t("newProperty.hotel.wifi")}`}
                      checked={formData.wifi}
                      onChange={handleChange}
                    />
                    <BoolCheck
                      name="parcheggioPrivato"
                      label={`🅿️ ${t("newProperty.hotel.parcheggio")}`}
                      checked={formData.parcheggioPrivato}
                      onChange={handleChange}
                    />
                    <BoolCheck
                      name="piscina"
                      label={`🏊 ${t("newProperty.hotel.piscina")}`}
                      checked={formData.piscina}
                      onChange={handleChange}
                    />
                    <BoolCheck
                      name="animaliAmmessi"
                      label={`🐕 ${t("newProperty.hotel.animali")}`}
                      checked={formData.animaliAmmessi}
                      onChange={handleChange}
                    />
                  </div>
                </Card.Body>
              </Card>
            )}

            {/*  RISTORAZIONE  */}
            {isRistorazione && (
              <Card className="np-card">
                <div className="np-card-header">
                  🍽️ {t("newProperty.ristorante.titolo")}
                </div>
                <Card.Body className="d-flex flex-column gap-3 pt-3">
                  <Form.Group>
                    <Form.Control
                      type="text"
                      name="specialita"
                      placeholder={t("newProperty.ristorante.specialita")}
                      value={formData.specialita}
                      onChange={handleChange}
                    />
                  </Form.Group>
                  <div className="d-flex gap-2">
                    <Form.Group className="flex-fill">
                      <Form.Label className="small fw-semibold">
                        {t("newProperty.ristorante.prezzoMin")}
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
                        {t("newProperty.ristorante.prezzoMax")}
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
                  <Form.Group>
                    <Form.Label className="small fw-semibold">
                      {t("newProperty.ristorante.tipologiaCucina")}
                    </Form.Label>
                    <Form.Select
                      name="tipologiaCucina"
                      value={formData.tipologiaCucina}
                      onChange={handleChange}
                    >
                      <option value="">{t("newProperty.seleziona")}</option>
                      {tipologieCucina.map((c) => (
                        <option key={c.value} value={c.label}>
                          {c.label}
                        </option>
                      ))}
                    </Form.Select>
                  </Form.Group>
                  <div className="np-check-grid">
                    <BoolCheck
                      name="prenotazioniOnline"
                      label={`📅 ${t("newProperty.ristorante.prenotazioni")}`}
                      checked={formData.prenotazioniOnline}
                      onChange={handleChange}
                    />
                    <BoolCheck
                      name="delivery"
                      label={`🚚 ${t("newProperty.ristorante.delivery")}`}
                      checked={formData.delivery}
                      onChange={handleChange}
                    />
                  </div>
                </Card.Body>
              </Card>
            )}

            {/*  SPIAGGIA  */}
            {formData.tipologia === "SPIAGGIA" && (
              <Card className="np-card">
                <div className="np-card-header">
                  🏖️ {t("newProperty.spiaggia.titolo")}
                </div>
                <Card.Body className="d-flex flex-column gap-3 pt-3">
                  <div className="d-flex gap-2">
                    <Form.Group className="flex-fill">
                      <Form.Label className="small fw-semibold">
                        {t("newProperty.spiaggia.tipo")}
                      </Form.Label>
                      <Form.Select
                        name="tipoSpiaggia"
                        value={formData.tipoSpiaggia}
                        onChange={handleChange}
                      >
                        <option value="">{t("newProperty.seleziona")}</option>
                        {tipiSpiaggia.map((s) => (
                          <option key={s.value} value={s.label}>
                            {s.label}
                          </option>
                        ))}
                      </Form.Select>
                    </Form.Group>
                    <Form.Group className="flex-fill">
                      <Form.Label className="small fw-semibold">
                        {t("newProperty.spiaggia.prezzoOmbrellone")}
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
                  <div className="np-check-grid">
                    <BoolCheck
                      name="docciaPresente"
                      label={`🚿 ${t("newProperty.spiaggia.docce")}`}
                      checked={formData.docciaPresente}
                      onChange={handleChange}
                    />
                    <BoolCheck
                      name="barPresente"
                      label={`🍹 ${t("newProperty.spiaggia.bar")}`}
                      checked={formData.barPresente}
                      onChange={handleChange}
                    />
                    <BoolCheck
                      name="ristorazionePresente"
                      label={`🍽️ ${t("newProperty.spiaggia.ristorazione")}`}
                      checked={formData.ristorazionePresente}
                      onChange={handleChange}
                    />
                  </div>
                </Card.Body>
              </Card>
            )}

            {/*  STABILIMENTO BALNEARE  */}
            {formData.tipologia === "STABILIMENTO_BALNEARE" && (
              <Card className="np-card">
                <div className="np-card-header">
                  🏝️ {t("newProperty.stabilimento.titolo")}
                </div>
                <Card.Body className="d-flex flex-column gap-3 pt-3">
                  <div className="d-flex gap-2">
                    <Form.Group className="flex-fill">
                      <Form.Label className="small fw-semibold">
                        {t("newProperty.spiaggia.tipo")}
                      </Form.Label>
                      <Form.Select
                        name="tipoSpiaggia"
                        value={formData.tipoSpiaggia}
                        onChange={handleChange}
                      >
                        <option value="">{t("newProperty.seleziona")}</option>
                        {tipiSpiaggia.map((s) => (
                          <option key={s.value} value={s.label}>
                            {s.label}
                          </option>
                        ))}
                      </Form.Select>
                    </Form.Group>
                    <Form.Group className="flex-fill">
                      <Form.Label className="small fw-semibold">
                        {t("newProperty.spiaggia.prezzoOmbrellone")}
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
                  <div className="np-check-grid">
                    <BoolCheck
                      name="docciaPresente"
                      label={`🚿 ${t("newProperty.spiaggia.docce")}`}
                      checked={formData.docciaPresente}
                      onChange={handleChange}
                    />
                    <BoolCheck
                      name="barPresente"
                      label={`🍹 ${t("newProperty.spiaggia.bar")}`}
                      checked={formData.barPresente}
                      onChange={handleChange}
                    />
                    <BoolCheck
                      name="ristorazionePresente"
                      label={`🍽️ ${t("newProperty.spiaggia.ristorazione")}`}
                      checked={formData.ristorazionePresente}
                      onChange={handleChange}
                    />
                  </div>
                </Card.Body>
              </Card>
            )}

            {/*  NEGOZIO  */}
            {formData.tipologia === "NEGOZIO" && (
              <Card className="np-card">
                <div className="np-card-header">
                  🏪 {t("newProperty.negozio.titolo")}
                </div>
                <Card.Body className="d-flex flex-column gap-3 pt-3">
                  <Form.Group>
                    <Form.Label className="small fw-semibold">
                      {t("newProperty.negozio.categorie")}
                    </Form.Label>
                    <div className="np-check-grid mt-1">
                      {tipiMerce.map((m) => (
                        <label key={m.value} className="np-check-label">
                          <input
                            type="checkbox"
                            checked={tipiMerceSelezionati.includes(m.label)}
                            onChange={() => handleTipoMerceToggle(m.label)}
                          />
                          {m.label}
                        </label>
                      ))}
                    </div>
                  </Form.Group>
                  <div className="np-check-grid">
                    <BoolCheck
                      name="spedizioni"
                      label={`📦 ${t("newProperty.negozio.spedizioni")}`}
                      checked={formData.spedizioni}
                      onChange={handleChange}
                    />
                  </div>
                </Card.Body>
              </Card>
            )}

            {/*  SERVIZIO  */}
            {formData.tipologia === "SERVIZIO" && (
              <Card className="np-card">
                <div className="np-card-header">
                  🛎️ {t("newProperty.servizio.titolo")}
                </div>
                <Card.Body className="d-flex flex-column gap-3 pt-3">
                  <Form.Group>
                    <Form.Label className="small fw-semibold">
                      {t("newProperty.servizio.tipo")}
                    </Form.Label>
                    <Form.Select
                      name="tipoServizio"
                      value={formData.tipoServizio}
                      onChange={handleChange}
                    >
                      <option value="">{t("newProperty.seleziona")}</option>
                      {tipiServizio.map((s) => (
                        <option key={s.value} value={s.label}>
                          {s.label}
                        </option>
                      ))}
                    </Form.Select>
                  </Form.Group>
                  <div className="np-check-grid">
                    <BoolCheck
                      name="h24"
                      label={`🕐 ${t("newProperty.servizio.h24")}`}
                      checked={formData.h24}
                      onChange={handleChange}
                    />
                  </div>
                </Card.Body>
              </Card>
            )}

            {/*  TRASPORTO  */}
            {formData.tipologia === "TRASPORTO" && (
              <Card className="np-card">
                <div className="np-card-header">
                  🚗 {t("newProperty.trasporto.titolo")}
                </div>
                <Card.Body className="d-flex flex-column gap-3 pt-3">
                  <Form.Group>
                    <Form.Label className="small fw-semibold">
                      {t("newProperty.trasporto.tipo")}
                    </Form.Label>
                    <Form.Select
                      name="tipoTrasporto"
                      value={formData.tipoTrasporto}
                      onChange={handleChange}
                    >
                      <option value="">{t("newProperty.seleziona")}</option>
                      {tipiTrasporto.map((t2) => (
                        <option key={t2.value} value={t2.label}>
                          {t2.label}
                        </option>
                      ))}
                    </Form.Select>
                  </Form.Group>
                  <div className="np-check-grid">
                    <BoolCheck
                      name="pagamentoDigitale"
                      label={`💳 ${t("newProperty.trasporto.digitale")}`}
                      checked={formData.pagamentoDigitale}
                      onChange={handleChange}
                    />
                  </div>
                </Card.Body>
              </Card>
            )}

            {/*  ATTRAZIONE  */}
            {formData.tipologia === "ATTRAZIONE_PRINCIPALE" && (
              <Card className="np-card">
                <div className="np-card-header">
                  🎭 {t("newProperty.attrazione.titolo")}
                </div>
                <Card.Body className="d-flex flex-column gap-3 pt-3">
                  <Form.Group>
                    <Form.Label className="small fw-semibold">
                      {t("newProperty.attrazione.tipo")}
                    </Form.Label>
                    <Form.Select
                      name="tipoAttrazione"
                      value={formData.tipoAttrazione}
                      onChange={handleChange}
                    >
                      <option value="">{t("newProperty.seleziona")}</option>
                      {tipiAttrazione.map((a) => (
                        <option key={a.value} value={a.label}>
                          {a.label}
                        </option>
                      ))}
                    </Form.Select>
                  </Form.Group>
                  <Form.Group>
                    <Form.Label className="small fw-semibold">
                      {t("newProperty.attrazione.biglietto")}
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

            {/*  FOTO  */}
            <Card className="np-card">
              <div className="np-card-header">
                <FiCamera size={17} /> {t("newProperty.foto")}
              </div>
              <Card.Body className="pt-3">
                <label className="np-upload-btn" style={{ cursor: isUploading ? "wait" : "pointer" }}>
                  {isUploading ? (
                    <><Spinner as="span" size="sm" animation="border" className="me-1" /> {t("upload.caricamento")}</>
                  ) : (
                    <><FiPlus size={15} /> {t("newProperty.aggiungi")}</>
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
                  <div className="np-foto-empty">
                    <FiCamera size={28} />
                    <p className="mb-0">{t("newProperty.nessuna_foto")}</p>
                  </div>
                ) : (
                  <div className="np-foto-grid">
                    {fotoUrls.map((url, i) => (
                      <div key={i} className="np-foto-item">
                        <img src={url} alt={`foto-${i}`} />
                        <button
                          type="button"
                          className="np-foto-remove"
                          onClick={() => {
                            fotoUrlsRef.current = fotoUrlsRef.current.filter((_, j) => j !== i);
                            setFotoUrls([...fotoUrlsRef.current]);
                          }}
                          title={t("newProperty.rimuovi")}
                        >
                          <FiX size={12} />
                        </button>
                      </div>
                    ))}
                  </div>
                )}
              </Card.Body>
            </Card>

            {/*  SUBMIT  */}
            <button
              type="submit"
              disabled={isLoading}
              className="np-submit btn"
            >
              {isLoading ? (
                <>
                  <Spinner
                    as="span"
                    size="sm"
                    animation="border"
                    className="me-2"
                  />
                  {t("newProperty.creazione")}
                </>
              ) : (
                t("newProperty.crea")
              )}
            </button>
          </Form>
        </div>
      </div>
    </>
  );
};

export default NewPropertyForm;
