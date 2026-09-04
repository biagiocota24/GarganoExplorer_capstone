import { useState, useRef, useEffect } from "react";
import { FiUser, FiSettings, FiLogOut } from "react-icons/fi";
import { useLocation, useNavigate } from "react-router-dom";
import { useAuthStore } from "../../../zustand/authStore";
import { useTranslation } from "react-i18next";
import { Button, Modal } from "react-bootstrap";
import "./visitorDropdown.css";
import { FaHome } from "react-icons/fa";

interface DropdownProps {
  userName: string;
}

const UserDropdown = ({ userName }: DropdownProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const [showLogoutModal, setShowLogoutModal] = useState(false);
  const dropdownRef = useRef(null);
  const navigate = useNavigate();
  const location = useLocation();
  const { logout, role } = useAuthStore();
  const { t } = useTranslation();

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const initials = userName
    .split(" ")
    .map((word) => word[0])
    .join("")
    .toUpperCase()
    .slice(0, 2);

  const handleLogoutClick = () => {
    setIsOpen(false);
    setShowLogoutModal(true);
  };

  const confirmLogout = () => {
    setShowLogoutModal(false);
    logout();
    navigate("/");
  };

  return (
    <>
      <div className="user-dropdown" ref={dropdownRef}>
        {/* BUTTON AVATAR */}
        <button
          className="avatar-button"
          onClick={() => setIsOpen(!isOpen)}
          title={userName}
        >
          <span className="avatar-initials">{initials}</span>
          <span className="avatar-name">{userName}</span>
        </button>

        {/* MENU DROPDOWN */}
        {isOpen && (
          <div className="dropdown-menu-custom">
            <div className="dropdown-item-section">
              <button
                className={
                  location.pathname === "/visitor/profile"
                    ? "dropdown-item dropdown-item-active"
                    : "dropdown-item"
                }
                onClick={() =>
                  navigate(
                    role === "BUSINESS_OWNER"
                      ? "/business/profile"
                      : "/visitor/profile",
                  )
                }
              >
                <FiUser size={18} />
                <span>{t("dropdown.profilo")}</span>
              </button>

              <button className="dropdown-item">
                <FiSettings size={18} />
                <span>{t("dropdown.impostazioni")}</span>
              </button>
              {location.pathname.includes("business") && (
                <button
                  className="dropdown-item"
                  onClick={() => navigate("/business/new-property")}
                >
                  <FaHome size={18} />
                  <span>{t("dropdown.nuovaAttivita")}</span>
                </button>
              )}
            </div>

            <div className="dropdown-divider"></div>

            <button className="dropdown-item logout-item" onClick={handleLogoutClick}>
              <FiLogOut size={18} />
              <span>{t("dropdown.esci")}</span>
            </button>
          </div>
        )}
      </div>

      <Modal show={showLogoutModal} onHide={() => setShowLogoutModal(false)} centered>
        <Modal.Header closeButton>
          <Modal.Title>{t("dropdown.confermaLogout")}</Modal.Title>
        </Modal.Header>
        <Modal.Body>{t("dropdown.logoutMessaggio")}</Modal.Body>
        <Modal.Footer>
          <Button variant="outline-secondary" onClick={() => setShowLogoutModal(false)}>
            {t("dropdown.annulla")}
          </Button>
          <Button variant="danger" onClick={confirmLogout}>
            {t("dropdown.esci")}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default UserDropdown;
