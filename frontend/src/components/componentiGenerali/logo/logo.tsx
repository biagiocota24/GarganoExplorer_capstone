import { FaCompass } from "react-icons/fa";
import "./logo.css";
import { useNavigate } from "react-router-dom";
interface LogoProps {
  tema: "dark" | "light";
}

const Logo = function ({ tema }: LogoProps) {
  const navigate = useNavigate();

  const logoClickNavigate = () => {
    if (location.pathname.includes("visitor")) {
      navigate("/visitor/home");
    }else if(location.pathname.includes("business")){
      navigate("/business/dashboard")
    }
  };
  return (
    <div
      className="d-flex align-items-center"
      onClick={logoClickNavigate}
      style={{ cursor: "pointer" }}
    >
      <div
        className={
          tema === "dark" ? "compass-icon-scura" : "compass-icon-chiara"
        }
      >
        <FaCompass />
      </div>
      <div
        className={
          tema === "dark" ? "scritta-logo-scura" : "scritta-logo-chiara"
        }
      >
        Gargano Explorer
      </div>
    </div>
  );
};

export default Logo;
