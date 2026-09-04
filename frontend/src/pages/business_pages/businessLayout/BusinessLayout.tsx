import { useState } from "react";
import { Link, Outlet, useLocation, useNavigate } from "react-router-dom";
import { FiMenu, FiX } from "react-icons/fi";
import Logo from "../../../components/componentiGenerali/logo/logo";
import SelettoreLingua from "../../../components/selettore-lingua/SelettoreLingua";
import UserDropdown from "../../../components/componentiGenerali/visitordropdown/VisitorDropdown";
import "./businessLayout.css";
import { Col, Container, Row } from "react-bootstrap";
import {
  MdDirectionsCar,
  MdEventNote,
  MdLocationOn,
  MdWaves,
} from "react-icons/md";
import { useAuthStore } from "../../../zustand/authStore";
import { useTranslation } from "react-i18next";

const BusinessLayout = function () {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const { t } = useTranslation();

  const linksNavbar = [
    { value: "dashboard", label: t("Dashboard") },
    { value: "strutture", label: t("Le tue strutture") },
  ];

  const exploreLinks = [
    {
      label: t("layout.nav.attrazioni"),
      href: "/businessOwner/attrazioni",
      icon: MdLocationOn,
    },
    {
      label: t("layout.nav.strutture"),
      href: "/visitor/Strutture",
      icon: MdDirectionsCar,
    },
    { label: t("layout.nav.spiagge"), href: "/visitor/spiagge", icon: MdWaves },
    {
      label: t("layout.nav.servizi"),
      href: "/visitor/servizi",
      icon: MdEventNote,
    },
  ];

  const businessLinks = [
    { label: t("layout.footer.registraAttivita"), href: "/business/register" },
    {
      label: t("layout.footer.dashboardBusiness"),
      href: "/business/dashboard",
    },
    { label: t("layout.footer.prezzi"), href: "/business/pricing" },
  ];

  const supportLinks = [
    { label: t("layout.footer.contattaci"), href: "/support/contact" },
    { label: t("layout.footer.faq"), href: "/support/faq" },
    { label: t("layout.footer.chiSiamo"), href: "/about" },
  ];

  const location = useLocation();
  const navigate = useNavigate();
  const { user } = useAuthStore();

  const handleNavigation = (value) => {
    navigate(`/business/${value}`);
    setMobileMenuOpen(false);
  };

  return (
    <div className="sfondo-user text-white">
      <div className="visitor-navbar">
        {/* LOGO */}
        <div className="navbar-logo">
          <Logo tema="dark" />
        </div>

        {/* DESKTOP: Link navbar */}
        <div className="d-none d-md-flex justify-content-center gap-2">
          {linksNavbar.map((link) => {
            const isActive = location.pathname === `/business/${link.value}`;
            return (
              <button
                className={isActive ? "link-navbar link-attivo" : "link-navbar"}
                key={link.label}
                onClick={() => handleNavigation(link.value)}
              >
                {link.label}
              </button>
            );
          })}
        </div>

        {/* DESKTOP: Selector lingua + Dropdown */}
        <div className="d-none d-md-flex align-items-center gap-3">
          <SelettoreLingua />
          <UserDropdown userName={user.name} />
        </div>

        {/* MOBILE: Menu hamburger */}
        <div className="d-md-none d-flex align-items-center gap-2">
          <button
            className="hamburger-btn"
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          >
            {mobileMenuOpen ? <FiX size={24} /> : <FiMenu size={24} />}
          </button>
        </div>
      </div>

      {/* MOBILE: Menu dropdown */}
      {mobileMenuOpen && (
        <div className="mobile-menu">
          {linksNavbar.map((link) => {
            const isActive = location.pathname === `/visitor/${link.value}`;
            return (
              <button
                className={isActive ? "mobile-link active" : "mobile-link"}
                key={link.label}
                onClick={() => handleNavigation(link.value)}
              >
                {link.label}
              </button>
            );
          })}

          <div className="mobile-menu-divider"></div>

          <div className="mobile-menu-bottom">
            <SelettoreLingua />
            <UserDropdown userName={user.name} />
          </div>
        </div>
      )}
      <main className="flex-grow-1">
        <Outlet />
      </main>
      <footer className="p-2 p-lg-5 ">
        <Container fluid>
          <Row xs={1} lg={4} className="g-5 border-top-chiaro">
            <Col>
              <Logo tema="light" />
              <p className="w-75">{t("layout.footer.desc")}</p>
              <div className="footer-icons">
                {exploreLinks.map((link) => {
                  const IconComponent = link.icon;
                  return (
                    <button
                      key={link.label}
                      className="footer-icon-btn"
                      onClick={() => navigate(link.href)}
                      title={link.label}
                    >
                      <IconComponent size={28} />
                    </button>
                  );
                })}
              </div>
            </Col>
            <Col className="d-flex flex-column gap-2">
              <h5>{t("layout.footer.esplora")}</h5>
              {exploreLinks.map((link) => {
                return (
                  <Link to={link.href} className="footer-link">
                    {link.label}
                  </Link>
                );
              })}
            </Col>
            <Col className="d-flex flex-column gap-2">
              <h5>{t("layout.footer.perAttivita")}</h5>
              {businessLinks.map((link) => {
                return (
                  <Link to={link.href} className="footer-link">
                    {link.label}
                  </Link>
                );
              })}
            </Col>
            <Col className="d-flex flex-column gap-2">
              <h5>{t("layout.footer.supporto")}</h5>
              {supportLinks.map((link) => {
                return (
                  <Link to={link.href} className="footer-link">
                    {link.label}
                  </Link>
                );
              })}
            </Col>
          </Row>
          <Row className="border-top-chiaro py-3 mt-4">
            <p className="mt-2">
              @ {new Date().getFullYear()} GarganoExplorer.{" "}
              {t("layout.footer.copyright")}
            </p>
            <div className="d-flex gap-3">
              <span className="footer-link">{t("layout.footer.privacy")}</span>
              <span className="footer-link">{t("layout.footer.termini")}</span>
              <span className="footer-link">{t("layout.footer.cookie")}</span>
            </div>
          </Row>
        </Container>
      </footer>
    </div>
  );
};

export default BusinessLayout;
