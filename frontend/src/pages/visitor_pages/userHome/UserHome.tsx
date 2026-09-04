import { Col, Container, Row } from "react-bootstrap";
import { useAuthStore } from "../../../zustand/authStore";
import "./userHome.css";
import { useNavigate } from "react-router-dom";
import { useStruttureStore } from "../../../zustand/struttureStore";
import CardStruttura from "../../../components/componentiGenerali/card-struttura/CardStruttura";
import { useTranslation } from "react-i18next";

const UserHome = function () {
  const { t } = useTranslation();
  const navigate = useNavigate();

  const { user } = useAuthStore();
  const { strutture } = useStruttureStore();
  const ultimeCinqueStrutture = [...strutture]
    .sort((a, b) => {
      return (
        new Date(b.dataRegistrazione).getTime() -
        new Date(a.dataRegistrazione).getTime()
      );
    })
    .slice(0, 10);

  const categoriePrincipali = [
    {
      label: t("userHome.spiagge"),
      value: "SPIAGGIA",
      icon: "🏖️",
      unita: t("userHome.unitaSpiagge"),
    },
    {
      label: t("userHome.ristoranti"),
      value: "RISTORANTE",
      icon: "🍽️",
      unita: t("userHome.unitaRistoranti"),
    },
    {
      label: t("userHome.hotels"),
      value: "HOTEL",
      icon: "🏨",
      unita: t("userHome.unitaHotels"),
    },
    {
      label: t("userHome.shopping"),
      value: "NEGOZIO",
      icon: "🛍️",
      unita: t("userHome.unitaAttivita"),
    },
  ];

  return (
    <Container fluid>
      <div className="userHome-hero d-flex flex-column justify-content-center align-items-center">
        <span className="fs-4 rounded-5 px-4 py-1 transparent-bg border">
          {t("userHome.ciao", { name: user ? user.name : "" })}
        </span>
        <h1 className="fw-bold">{t("userHome.titolo")}</h1>
        <p className="fs-3 text-center">{t("userHome.sottotitolo")}</p>
        <div className="d-flex gap-3">
          <button
            className="userHero-button white-bg"
            onClick={() => navigate("/visitor/esplora")}
          >
            {t("userHome.esploraAttrazioni")}
          </button>
        </div>
      </div>
      <Row className="userMain-section p-3 justify-content-center">
        {categoriePrincipali.map((c) => {
          return (
            <Col xs={6} lg={3} className="text-black g-3 mt-4" key={c.value}>
              <div
                className="d-flex flex-column align-items-start gap-2 rounded-2 bg-white px-3 py-5  category"
                style={{ cursor: "pointer" }}
                onClick={() =>
                  navigate(`/visitor/esplora?tipologia=${c.value}`)
                }
              >
                <span className="fs-1 categories-icons">{c.icon}</span>
                <span className="fw-bold fs-4">{c.label}</span>
                <span>
                  {
                    strutture.filter((s) => s.tipologia.toString() === c.value)
                      .length
                  }{" "}
                  {c.unita}
                </span>
              </div>
            </Col>
          );
        })}
        <Col className="text-black g-3 mt-4" xs={12} xl={6}>
          <div
            className="d-flex flex-column align-items-center justify-content-center gap-2 rounded-2 px-3"
            id="seeAll-btn"
            style={{ position: "sticky", top: "20px", height: "40px" }}
            onClick={() => navigate("/visitor/strutture")}
          >
            <span>{t("userHome.vediTutte")}</span>
          </div>
        </Col>
      </Row>
      <Row className="userMain-section pt-4 text-black">
        <h2 className="fw-bold">{t("userHome.ultimeAggiunte")}</h2>
        <div
          className="d-flex"
          style={{
            overflowX: "auto",
            overflowY: "hidden",
            gap: "1rem",
            paddingBottom: "1rem",
          }}
        >
          {ultimeCinqueStrutture.map((s) => (
            <div
              key={s.id}
              style={{
                minWidth: "280px",
                flex: "0 0 280px",
              }}
            >
              <CardStruttura struttura={s} />
            </div>
          ))}
        </div>
      </Row>
    </Container>
  );
};

export default UserHome;
