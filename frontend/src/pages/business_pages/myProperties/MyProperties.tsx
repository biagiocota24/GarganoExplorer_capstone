import { useEffect } from "react";
import { Col, Container, Row, Spinner } from "react-bootstrap";
import CardStruttura from "../../../components/componentiGenerali/card-struttura/CardStruttura";
import { useAuthStore } from "../../../zustand/authStore";
import { useStruttureStore } from "../../../zustand/struttureStore";
import { useTranslation } from "react-i18next";

const MyProperties = function () {
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
      <h2 className="text-center mt-3">{t("businessDashboard.letuStrutture")}</h2>
      <Row className="mt-4 g-3">
        {mieStruttureLoading ? (
          <div className="d-flex justify-content-center py-5">
            <Spinner animation="border" style={{ color: "#1a7a96" }} />
          </div>
        ) : mieStrutture.length !== 0 ? (
          mieStrutture.map((str) => (
            <Col xs={12} md={6} lg={4} xl={3} key={str.id}>
              <CardStruttura struttura={str} />
            </Col>
          ))
        ) : (
          <div className="fs-4 text-center">{t("businessDashboard.nessunStruttura")}</div>
        )}
      </Row>
    </Container>
  );
};

export default MyProperties;
