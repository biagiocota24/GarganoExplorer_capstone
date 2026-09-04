import { Card, Col, Container, Row } from "react-bootstrap";
import { useEnumsStore } from "../../../zustand/enumsStore";
import { useEffect } from "react";
import type { TipologiaStruttura } from "../../../interfaces/enumsInterfaces";
import "./StrutturaPage.css";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";

const StrutturaPage = function () {
  const { tipologieStruttura, fetchAllEnums } = useEnumsStore();
  const navigate = useNavigate();
  const { t } = useTranslation();

  useEffect(() => {
    fetchAllEnums();
  }, [fetchAllEnums]);

  return (
    <Container className="struttura-page py-4">
      <h2 className="struttura-title mb-4">{t("strutturePage.titolo")}</h2>
      <Row xs={1} sm={2} md={3} lg={4} className="g-4">
        {tipologieStruttura.map((tipo: TipologiaStruttura) => (
          <Col key={tipo.value}>
            <Card
              className="struttura-card h-100 border-0 shadow-sm"
              onClick={() => navigate(`/visitor/strutture/${tipo.label}`)}
            >
              <div className="struttura-img-wrapper">
                <Card.Img
                  variant="top"
                  src={tipo.url}
                  alt={tipo.label}
                  className="struttura-img"
                />
              </div>
              <Card.Body className="text-center p-3">
                <Card.Title className="struttura-label mb-1">
                  {tipo.label}
                </Card.Title>
                {tipo.descrizione && (
                  <Card.Text className="struttura-desc text-muted small">
                    {tipo.descrizione}
                  </Card.Text>
                )}
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default StrutturaPage;
