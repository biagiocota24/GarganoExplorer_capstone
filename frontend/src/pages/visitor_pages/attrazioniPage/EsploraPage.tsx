import { Col, Container, Form, Row, Spinner } from "react-bootstrap";
import "./AttrazionePage.css";
import { useAuthStore } from "../../../zustand/authStore";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useStruttureStore } from "../../../zustand/struttureStore";
import CardStruttura from "../../../components/componentiGenerali/card-struttura/CardStruttura";
import { useCittaStore } from "../../../zustand/cittaStore";
import { useEnumsStore } from "../../../zustand/enumsStore";
import { useSearchParams } from "react-router-dom";

const EsploraPage = function () {
  const { t } = useTranslation();
  const [searchParams] = useSearchParams();
  const tipologiaFromUrl = searchParams.get("tipologia");
  const [isLoading, setIsLoading] = useState(false);
  const { user } = useAuthStore();
  const { citta, getCitta } = useCittaStore();
  const { tipologieStruttura } = useEnumsStore();
  const { strutture, loading: struttureLoading, getAllStrutture } = useStruttureStore();

  const [cittaSelezionata, setCittaSelezionata] = useState("");
  const [searchValue, setSearchValue] = useState("");
  const [query, setQuery] = useState("");

  const getTipologiaLabel = (value: string) => {
    if (!value) return "";
    const found = tipologieStruttura.find((tipo) => tipo.value === value);
    return found ? found.label : "";
  };

  const [tipologiaSelezionata, setTipologiaSelezionata] = useState(
    getTipologiaLabel(tipologiaFromUrl || ""),
  );

  useEffect(() => {
    if (tipologiaFromUrl) {
      setTipologiaSelezionata(getTipologiaLabel(tipologiaFromUrl));
    }
  }, [tipologiaFromUrl, tipologieStruttura]);

  useEffect(() => {
    getAllStrutture();
    getCitta();
  }, [getAllStrutture]);

  const filtraStrutture = () => {
    const struttureFiltrate = strutture.filter(
      (s) =>
        s.name.toLowerCase().includes(query.toLowerCase()) &&
        s.cittaNome.toLowerCase().includes(cittaSelezionata.toLowerCase()) &&
        s.tipologia
          .toString()
          .toLowerCase()
          .includes(tipologiaSelezionata.toLowerCase()),
    );
    return struttureFiltrate;
  };

  const struttureFiltrate = filtraStrutture();

  const handleUpdateSearch = (e: React.SubmitEvent) => {
    e.preventDefault();
    setQuery(searchValue);
  };

  return (
    <Container fluid>
      <div className="hero-section px-3">
        <span>
          {t("esplora.saluto", { name: user?.name || t("esplora.turista") })}
        </span>
        <h1>{t("esplora.titolo")}</h1>
        <p>{t("esplora.sottotitolo")}</p>
        <div className="hero-filters">
          <div className="select-wrapper">
            <select
              id="citta-select"
              name="citta"
              className="select-gargano"
              value={cittaSelezionata}
              onChange={(e) => setCittaSelezionata(e.target.value)}
            >
              <option value="">{t("esplora.selezionaCitta")}</option>
              {citta.map((c) => (
                <option key={c.cap} value={c.name}>
                  {c.name}
                </option>
              ))}
            </select>
          </div>

          <div className="select-wrapper">
            <select
              id="categoria-select"
              name="categoria"
              className="select-gargano"
              value={tipologiaSelezionata}
              onChange={(e) => setTipologiaSelezionata(e.target.value)}
            >
              <option value="">{t("esplora.selezionaCategoria")}</option>
              {tipologieStruttura.map((t) => (
                <option key={t.value} value={t.label}>
                  {t.label}
                </option>
              ))}
            </select>
          </div>
        </div>
        <Form className="form-wrapper" onSubmit={handleUpdateSearch}>
          <input
            className="border-0 w-100 h-50"
            type="text"
            placeholder={t("esplora.cercaPlaceholder")}
            value={searchValue}
            onChange={(e) => setSearchValue(e.target.value)}
          />
          <button
            type="submit"
            disabled={isLoading}
            className="role-button role-button-active mt-2 w-100 py-2 h-50"
          >
            {isLoading ? (
              <>
                <Spinner
                  as="span"
                  size="sm"
                  animation="border"
                  className="me-2"
                  role="status"
                />
                {t("esplora.ricercaInCorso")}
              </>
            ) : (
              t("esplora.cerca")
            )}
          </button>
        </Form>
      </div>
      <div className="risultati-ricerca">
        {struttureLoading ? (
          <div className="d-flex justify-content-center py-5">
            <Spinner animation="border" style={{ color: "#1a7a96" }} />
          </div>
        ) : struttureFiltrate.length === 0 ? (
          <div className="text-center fs-2 fw-bold">{t("esplora.nessunaTrovata")}</div>
        ) : (
          <Row xs={1} md={3} lg={4}>
            {struttureFiltrate.map((str) => (
              <Col key={str.id} className="g-3">
                <CardStruttura struttura={str} />
              </Col>
            ))}
          </Row>
        )}
      </div>
    </Container>
  );
};

export default EsploraPage;
