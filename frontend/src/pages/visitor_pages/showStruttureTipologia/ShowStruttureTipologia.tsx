import { useParams } from "react-router-dom";
import { useStruttureStore } from "../../../zustand/struttureStore";
import type { StrutturaResponse } from "../../../interfaces/struttureInterfaces";
import { Col, Container, Row } from "react-bootstrap";
import CardStruttura from "../../../components/componentiGenerali/card-struttura/CardStruttura";
import { useTranslation } from "react-i18next";

const ShowStruttureTipologia = function () {
  const { t } = useTranslation();
  const params = useParams();
  const { strutture } = useStruttureStore();

  const strutturePerCategoria: StrutturaResponse[] = strutture.filter((str) => {
    return (
      str.tipologia.toString().toLowerCase() ===
      params.strutturaTipologia.toLowerCase()
    );
  });

  return (
    <Container fluid className="mt-4">
      <Row className="g-3">
        {strutturePerCategoria.length === 0 ? (
          <Col xs={12}>
            <div className="fs-2 fw-bold text-center mt-5">{t("esplora.nessunaTrovata")}</div>
          </Col>
        ) : (
          strutturePerCategoria.map((str) => {
            return (
              <Col xs={12} md={6} lg={4} xl={3} key={str.id}>
                <CardStruttura struttura={str} />
              </Col>
            );
          })
        )}
      </Row>
    </Container>
  );
};

export default ShowStruttureTipologia;
