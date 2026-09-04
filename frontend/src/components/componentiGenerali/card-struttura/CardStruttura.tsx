import { useNavigate } from "react-router-dom";
import { FiMapPin, FiStar } from "react-icons/fi";
import { useTranslation } from "react-i18next";
import "./cardStruttura.css";
import type { StrutturaResponse } from "../../../interfaces/struttureInterfaces";

interface StruttureLCardProps {
  struttura: StrutturaResponse;
}

const CardStruttura = ({ struttura }: StruttureLCardProps) => {
  const navigate = useNavigate();
  const { t } = useTranslation();

  // PLACEHOLDER PER RATING CHE AGGIUNGERO DOPO
  const rating = 4.8;

  return (
    <div
      className="struttura-card"
      onClick={() => {
        if (location.pathname.includes("visitor")) {
          navigate(`/visitor/esplora/${struttura.id}`);
        } else if (location.pathname.includes("business")) {
          navigate(`/business/propertyOffice/${struttura.id}`);
        }
      }}
    >
      <div className="struttura-card-image-container">
        {struttura.fotoUrls ? (
          <img
            src={struttura.fotoUrls[0]}
            alt={struttura.name}
            className="struttura-card-image"
          />
        ) : (
          <div className="struttura-card-image-placeholder">
            {t("card.nessuna_foto")}
          </div>
        )}

        <div className="struttura-card-rating">
          <FiStar size={16} fill="currentColor" />
          <span>{rating}</span>
        </div>
      </div>

      <div className="struttura-card-info">
        <h3 className="struttura-card-name">{struttura.name}</h3>

        <div className="struttura-card-location">
          <FiMapPin size={14} />
          <span>{struttura.cittaNome || t("card.luogo_non_specificato")}</span>
        </div>
      </div>
    </div>
  );
};

export default CardStruttura;
