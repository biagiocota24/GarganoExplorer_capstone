import { Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import "./notFound.css";

export default function NotFound() {
  const navigate = useNavigate();

  return (
    <Container fluid className="notfound-container">
      <div className="notfound-content">
        {/* Emoji decorativi */}
        <div className="notfound-emoji">🗺️</div>

        {/* 404 grande */}
        <h1 className="notfound-title">404</h1>

        {/* Messaggio */}
        <p className="notfound-subtitle">
          Oops! Sembra che tu sia andato fuori dai sentieri...
        </p>

        <p className="notfound-description">
          La pagina che stai cercando non esiste o è stata spostata. 
          <br />
          Torna alla mappa e riprendi l'esplorazione del Gargano!
        </p>

        {/* CTA Buttons */}
        <div className="notfound-buttons">
          <button 
            className="btn-home"
            onClick={() => navigate("/")}
          >
            ← Torna alla Home
          </button>
          <button 
            className="btn-explore"
            onClick={() => navigate("/visitor/esplora")}
          >
            Esplora le Attrazioni →
          </button>
        </div>

        {/* Icone decorative */}
        <div className="notfound-icons">
          <span>🏖️</span>
          <span>🏔️</span>
          <span>🍝</span>
          <span>🏨</span>
        </div>
      </div>
    </Container>
  );
}
