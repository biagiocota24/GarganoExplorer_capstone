import { useEffect } from "react";
import { useAuthStore } from "../../../zustand/authStore";
import { useStruttureStore } from "../../../zustand/struttureStore";
import { Container, Row, Spinner } from "react-bootstrap";
import CardStruttura from "../../../components/componentiGenerali/card-struttura/CardStruttura";
import { useTranslation } from "react-i18next";

const BusinessDashboard = function () {
  const { t } = useTranslation();
  const { user } = useAuthStore();
  const { mieStrutture, mieStruttureLoading, getStruttureByUser } = useStruttureStore();

  useEffect(() => {
    if (user?.id) {
      getStruttureByUser(user.id);
    }
  }, [user?.id]);

  return (
    <Container fluid>
      <h1 className="fw-bold">{t("businessDashboard.letuStrutture")}</h1>
      <p className="fs-5">{t("businessDashboard.struttureRegistrate", { count: mieStrutture.length })}</p>
      <Row className="pt-4 text-black">
        {mieStruttureLoading ? (
          <div className="d-flex justify-content-center py-5">
            <Spinner animation="border" style={{ color: "#1a7a96" }} />
          </div>
        ) : (
          <div
            className="d-flex"
            style={{
              overflowX: "auto",
              overflowY: "hidden",
              gap: "1rem",
              paddingBottom: "1rem",
              WebkitOverflowScrolling: "touch",
            }}
          >
            {mieStrutture.map((s) => (
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
        )}
      </Row>
    </Container>
  );
};

export default BusinessDashboard;
