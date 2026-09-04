import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useStruttureStore } from "../../../zustand/struttureStore";
import {
  FiPhone, FiMail, FiGlobe, FiMapPin, FiClock,
  FiStar, FiCheck, FiX, FiExternalLink,
} from "react-icons/fi";
import { MdOutlineAccessible } from "react-icons/md";
import "./strutturaDetails.css";

const GIORNI_IT: Record<number, string> = {
  1: "Lunedì", 2: "Martedì", 3: "Mercoledì", 4: "Giovedì",
  5: "Venerdì", 6: "Sabato",  7: "Domenica",
};

const GIORNI_FALLBACK: Record<string, string> = {
  LUNEDI: "Lunedì", MARTEDI: "Martedì", MERCOLEDI: "Mercoledì",
  GIOVEDI: "Giovedì", VENERDI: "Venerdì", SABATO: "Sabato", DOMENICA: "Domenica",
  MONDAY: "Lunedì", TUESDAY: "Martedì", WEDNESDAY: "Mercoledì",
  THURSDAY: "Giovedì", FRIDAY: "Venerdì", SATURDAY: "Sabato", SUNDAY: "Domenica",
};

const normalizzaGiorno = (g: string): string =>
  GIORNI_FALLBACK[g.toUpperCase().replace(/[ÀÁ]/g, "A").replace(/[ÈÉ]/g, "E")] ?? g;

const giornoOggi = (): string => {
  const d = new Date().getDay();
  return GIORNI_IT[d === 0 ? 7 : d];
};

const parseTime = (t: unknown): string => {
  if (t == null) return "?";
  if (typeof t === "string") return t.slice(0, 5);
  if (Array.isArray(t)) {
    const h = String(t[0] ?? 0).padStart(2, "0");
    const m = String(t[1] ?? 0).padStart(2, "0");
    return `${h}:${m}`;
  }
  if (typeof t === "number") return `${String(t).padStart(2, "0")}:00`;
  return "?";
};

const formattaOrario = (
  o: {
    apertura?: unknown;
    chiusura?: unknown;
    orarioInizio?: unknown;
    orarioFine?: unknown;
    chiuso: boolean;
  },
  chiusoLabel: string,
): string => {
  if (o.chiuso) return chiusoLabel;
  return `${parseTime(o.apertura ?? o.orarioInizio)} – ${parseTime(o.chiusura ?? o.orarioFine)}`;
};

const formatTipologia = (tip: unknown): string => {
  if (tip == null) return "";
  const raw =
    typeof tip === "object"
      ? ((tip as { label?: string }).label ?? (tip as { value?: string }).value ?? "")
      : String(tip);
  return raw
    .toLowerCase()
    .replace(/_/g, " ")
    .replace(/^\w/, (c) => c.toUpperCase());
};

const formattaUrl = (url: string): string =>
  url.replace(/^https?:\/\//, "").replace(/\/$/, "");

const ServiceChip = ({
  ok,
  label,
}: {
  ok: boolean | null | undefined;
  label: string;
}) => {
  if (ok == null) return null;
  return (
    <span className={`sd-chip ${ok ? "sd-chip-ok" : "sd-chip-no"}`}>
      {ok ? <FiCheck size={12} /> : <FiX size={12} />}
      {label}
    </span>
  );
};

const InfoItem = ({
  label,
  value,
}: {
  label: string;
  value?: string | number | null;
}) => {
  if (value == null || value === "") return null;
  return (
    <div className="sd-info-item">
      <span className="sd-info-label">{label}</span>
      <span className="sd-info-value">{value}</span>
    </div>
  );
};

const TipoSection = ({ struttura }: { struttura: StrutturaResponse }) => {
  const { t } = useTranslation();

  /* HOTEL */
  if (
    struttura.stelle != null ||
    struttura.prezzoMedioNotte != null ||
    struttura.wifi != null ||
    struttura.parcheggioPrivato != null ||
    struttura.piscina != null ||
    struttura.animaliAmmessi != null
  ) {
    return (
      <div className="sd-card">
        <h2 className="sd-section-title">
          <FiStar /> {t("struttura.hotel.titolo")}
        </h2>
        {struttura.stelle != null && (
          <div className="sd-stelle mb-3">
            {Array.from({ length: struttura.stelle }).map((_, i) => (
              <FiStar key={i} fill="#FFD700" color="#FFD700" size={20} />
            ))}
            <span className="ms-2">{struttura.stelle} {t("struttura.stelle")}</span>
          </div>
        )}
        <div className="sd-info-grid mb-3">
          <InfoItem
            label={t("struttura.hotel.prezzoNotte")}
            value={struttura.prezzoMedioNotte != null ? `€ ${struttura.prezzoMedioNotte}` : null}
          />
        </div>
        <div className="sd-chips">
          <ServiceChip ok={struttura.wifi} label={t("struttura.hotel.wifi")} />
          <ServiceChip ok={struttura.parcheggioPrivato} label={t("struttura.hotel.parcheggio")} />
          <ServiceChip ok={struttura.piscina} label={t("struttura.hotel.piscina")} />
          <ServiceChip ok={struttura.animaliAmmessi} label={t("struttura.hotel.animali")} />
        </div>
      </div>
    );
  }

  /* RISTORANTE / BAR / AGRITURISMO */
  if (
    struttura.specialita != null ||
    struttura.fasciaPrezzoMedio != null ||
    struttura.tipologiaCucina != null ||
    struttura.prenotazioniOnline != null ||
    struttura.delivery != null
  ) {
    return (
      <div className="sd-card">
        <h2 className="sd-section-title">{t("struttura.ristorante.titolo")}</h2>
        <div className="sd-info-grid mb-3">
          <InfoItem label={t("struttura.ristorante.tipoCucina")} value={struttura.tipologiaCucina?.label} />
          <InfoItem label={t("struttura.ristorante.specialita")} value={struttura.specialita} />
          {struttura.fasciaPrezzoMedio && (
            <InfoItem
              label={t("struttura.ristorante.fascia")}
              value={`€ ${struttura.fasciaPrezzoMedio.prezzoMin} – € ${struttura.fasciaPrezzoMedio.prezzoMax}`}
            />
          )}
        </div>
        <div className="sd-chips">
          <ServiceChip ok={struttura.prenotazioniOnline} label={t("struttura.ristorante.prenotazioni")} />
          <ServiceChip ok={struttura.delivery} label={t("struttura.ristorante.delivery")} />
        </div>
      </div>
    );
  }

  /* STABILIMENTO BALNEARE */
  if (
    struttura.tipoSpiaggia != null ||
    struttura.prezzoOmbrellone != null ||
    struttura.docciaPresente != null ||
    struttura.barPresente != null ||
    struttura.ristorazionePresente != null
  ) {
    return (
      <div className="sd-card">
        <h2 className="sd-section-title">{t("struttura.stabilimento.titolo")}</h2>
        <div className="sd-info-grid mb-3">
          <InfoItem label={t("struttura.stabilimento.tipoSpiaggia")} value={struttura.tipoSpiaggia?.label} />
          <InfoItem
            label={t("struttura.stabilimento.prezzoOmbrellone")}
            value={struttura.prezzoOmbrellone != null ? `€ ${struttura.prezzoOmbrellone}` : null}
          />
        </div>
        <div className="sd-chips">
          <ServiceChip ok={struttura.docciaPresente} label={t("struttura.stabilimento.docce")} />
          <ServiceChip ok={struttura.barPresente} label={t("struttura.stabilimento.bar")} />
          <ServiceChip ok={struttura.ristorazionePresente} label={t("struttura.stabilimento.ristorazione")} />
        </div>
      </div>
    );
  }

  /* NEGOZIO */
  if (struttura.tipiMerce?.length || struttura.spedizioni != null) {
    return (
      <div className="sd-card">
        <h2 className="sd-section-title">{t("struttura.negozio.titolo")}</h2>
        {struttura.tipiMerce?.length ? (
          <div className="mb-3">
            <span className="sd-info-label d-block mb-2">{t("struttura.negozio.categorie")}</span>
            <div className="sd-chips">
              {struttura.tipiMerce.map((m) => (
                <span key={m.value} className="sd-chip sd-chip-neutral">
                  {m.label}
                </span>
              ))}
            </div>
          </div>
        ) : null}
        <div className="sd-chips">
          <ServiceChip ok={struttura.spedizioni} label={t("struttura.negozio.spedizioni")} />
        </div>
      </div>
    );
  }

  /* SERVIZIO */
  if (struttura.tipoServizio != null || struttura.h24 != null) {
    return (
      <div className="sd-card">
        <h2 className="sd-section-title">{t("struttura.servizio.titolo")}</h2>
        <div className="sd-info-grid mb-3">
          <InfoItem label={t("struttura.servizio.tipo")} value={struttura.tipoServizio?.label} />
          {struttura.tipoServizio?.descrizione && (
            <InfoItem label={t("struttura.servizio.descrizione")} value={struttura.tipoServizio.descrizione} />
          )}
        </div>
        <div className="sd-chips">
          <ServiceChip ok={struttura.h24} label={t("struttura.servizio.h24")} />
        </div>
      </div>
    );
  }

  /* TRASPORTO */
  if (struttura.tipoTrasporto != null || struttura.pagamentoDigitale != null) {
    return (
      <div className="sd-card">
        <h2 className="sd-section-title">{t("struttura.trasporto.titolo")}</h2>
        <div className="sd-info-grid mb-3">
          <InfoItem label={t("struttura.trasporto.tipo")} value={struttura.tipoTrasporto?.label} />
        </div>
        <div className="sd-chips">
          <ServiceChip ok={struttura.pagamentoDigitale} label={t("struttura.trasporto.digitale")} />
        </div>
      </div>
    );
  }

  /* ATTRAZIONE PRINCIPALE */
  if (struttura.tipoAttrazione != null || struttura.bigliettoEntrata != null) {
    return (
      <div className="sd-card">
        <h2 className="sd-section-title">{t("struttura.attrazione.titolo")}</h2>
        <div className="sd-info-grid">
          <InfoItem label={t("struttura.attrazione.tipo")} value={struttura.tipoAttrazione?.label} />
          {struttura.tipoAttrazione?.descrizione && (
            <InfoItem label={t("struttura.attrazione.categoria")} value={struttura.tipoAttrazione.categoria} />
          )}
          {struttura.bigliettoEntrata != null && (
            <InfoItem
              label={t("struttura.attrazione.biglietto")}
              value={
                struttura.bigliettoEntrata === 0
                  ? t("struttura.attrazione.gratuito")
                  : `€ ${struttura.bigliettoEntrata}`
              }
            />
          )}
        </div>
      </div>
    );
  }

  return null;
};

const StrutturaDetails = function () {
  const params = useParams();
  const { t } = useTranslation();
  const { currentStruttura, currentStrutturaLoading, getStrutturaById, clearCurrentStruttura } = useStruttureStore();
  const [imagePrincipale, setImagePrincipale] = useState<string | null>(null);

  useEffect(() => {
    if (!params.strutturaId) return;
    getStrutturaById(params.strutturaId);
    return () => clearCurrentStruttura();
  }, [params.strutturaId]);

  useEffect(() => {
    if (currentStruttura?.fotoUrls?.length) {
      setImagePrincipale(currentStruttura.fotoUrls[0]);
    }
  }, [currentStruttura]);

  if (currentStrutturaLoading) {
    return (
      <div className="sd-loading">
        <div className="sd-loading-spinner" />
        <span>{t("struttura.caricamento")}</span>
      </div>
    );
  }
  if (!currentStruttura) {
    return <div className="sd-not-found">{t("struttura.non_trovata")}</div>;
  }

  const struttura = currentStruttura;

  const verificaApertura = (): boolean => {
    if (!struttura.orariApertura?.length) return false;
    const adesso    = new Date();
    const nomeOggi  = giornoOggi();
    const orarioOggi = struttura.orariApertura.find((o) => normalizzaGiorno(o.giorno) === nomeOggi);
    if (!orarioOggi || orarioOggi.chiuso) return false;
    const oraAttuale = `${String(adesso.getHours()).padStart(2, "0")}:${String(adesso.getMinutes()).padStart(2, "0")}`;
    const apertura = parseTime(orarioOggi.apertura ?? orarioOggi.orarioInizio) || "00:00";
    const chiusura = parseTime(orarioOggi.chiusura ?? orarioOggi.orarioFine)   || "23:59";
    return oraAttuale >= apertura && oraAttuale <= chiusura;
  };
  const isOpen = struttura.orariApertura?.length ? verificaApertura() : null;

  const tipologiaLabel = formatTipologia(struttura.tipologia);

  const orariOrdinati = struttura.orariApertura
    ? [...struttura.orariApertura].sort((a, b) => a.ordine - b.ordine)
    : [];

  const oggi = giornoOggi();
  const isToday = (giorno: string) => normalizzaGiorno(giorno) === oggi;
  const chiusoLabel = t("struttura.chiuso");

  const cta = struttura.sitoWebURL
    ? { label: t("struttura.visitaSito"), href: struttura.sitoWebURL }
    : null;

  return (
    <div className="sd-container">

      <div className="sd-gallery">
        {imagePrincipale ? (
          <img
            src={imagePrincipale}
            alt={struttura.name}
            className="sd-img-hero"
          />
        ) : (
          <div className="sd-img-placeholder">{t("struttura.nessuna_foto")}</div>
        )}

        {struttura.fotoUrls?.length > 1 && (
          <div className="sd-thumbnails">
            {struttura.fotoUrls.map((url, idx) => (
              <img
                key={idx}
                src={url}
                alt=""
                className={`sd-thumb ${imagePrincipale === url ? "sd-thumb-active" : ""}`}
                onClick={() => setImagePrincipale(url)}
              />
            ))}
          </div>
        )}
      </div>

      <div className="sd-card sd-header-card">
        <div className="sd-badges">
          {isOpen !== null && (
            <span className={`sd-badge-status ${isOpen ? "sd-open" : "sd-closed"}`}>
              <span
                style={{
                  display: "inline-block",
                  width: 7,
                  height: 7,
                  borderRadius: "50%",
                  background: "currentColor",
                  marginRight: 4,
                }}
              />
              {isOpen ? t("struttura.aperto") : t("struttura.chiuso")}
            </span>
          )}
          <span className="sd-badge-tipo">{tipologiaLabel}</span>
        </div>

        <h1 className="sd-title">{struttura.name}</h1>

        {(struttura.via || struttura.cittaNome) && (
          <div className="sd-location">
            <FiMapPin size={15} />
            <span>
              {struttura.via && struttura.numeroCivico
                ? `${struttura.via}, ${struttura.numeroCivico} – `
                : struttura.via
                  ? `${struttura.via} – `
                  : ""}
              {struttura.cittaNome ?? ""}
            </span>
          </div>
        )}

        {struttura.stelle != null && (
          <div className="sd-stelle">
            {Array.from({ length: struttura.stelle }).map((_, i) => (
              <FiStar key={i} fill="#FFD700" color="#FFD700" size={18} />
            ))}
            <span className="ms-2 fw-semibold">{struttura.stelle} {t("struttura.stelle")}</span>
          </div>
        )}

        <p className="sd-descrizione">{struttura.descrizione}</p>

        {cta && (
          <a
            href={cta.href}
            target="_blank"
            rel="noreferrer"
            className="sd-cta-btn"
          >
            <FiExternalLink size={15} />
            {cta.label}
          </a>
        )}
      </div>

      <div className="sd-sections">

        <TipoSection struttura={struttura} />

        {orariOrdinati.length > 0 && (
          <div className="sd-card">
            <h2 className="sd-section-title">
              <FiClock /> {t("struttura.orari")}
            </h2>
            <table className="sd-orari-table">
              <tbody>
                {orariOrdinati.map((o) => {
                  return (
                    <tr key={o.giorno} className={isToday(o.giorno) ? "sd-oggi" : ""}>
                      <td className="sd-giorno">{normalizzaGiorno(o.giorno)}</td>
                      <td className={o.chiuso ? "sd-orario-chiuso" : "sd-orario-aperto"}>
                        {formattaOrario(o, chiusoLabel)}
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        )}

        <div className="sd-card">
          <h2 className="sd-section-title">
            <FiPhone /> {t("struttura.contatti")}
          </h2>
          <ul className="sd-contatti">
            {struttura.telefono && (
              <li>
                <FiPhone size={15} />
                <a href={`tel:${struttura.telefono}`}>{struttura.telefono}</a>
              </li>
            )}
            {struttura.email && (
              <li>
                <FiMail size={15} />
                <a href={`mailto:${struttura.email}`}>{struttura.email}</a>
              </li>
            )}
            {struttura.sitoWebURL && (
              <li>
                <FiGlobe size={15} />
                <a href={struttura.sitoWebURL} target="_blank" rel="noreferrer">
                  {formattaUrl(struttura.sitoWebURL)}
                </a>
              </li>
            )}
          </ul>

          {struttura.accessoDisabili != null && (
            <div className="sd-accesso-disabili">
              <span
                className={`sd-chip ${struttura.accessoDisabili ? "sd-chip-ok" : "sd-chip-no"}`}
              >
                <MdOutlineAccessible size={14} />
                {struttura.accessoDisabili ? t("struttura.accessibile") : t("struttura.nonAccessibile")}
              </span>
            </div>
          )}
        </div>

      </div>
    </div>
  );
};

export default StrutturaDetails;
