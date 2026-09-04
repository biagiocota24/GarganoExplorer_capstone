import { Form, Spinner } from "react-bootstrap";
import "./registerForm.css";
import { FiMail, FiLock, FiPhone, FiUser, FiCheckCircle } from "react-icons/fi";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Link, useNavigate } from "react-router-dom";
import { Modal, Button } from "react-bootstrap";
import UploadFile from "../../components/componentiGenerali/UploadFile";
import ToastNotification from "../../components/componentiGenerali/ToastNotification";
import { useEnumsStore } from "../../zustand/enumsStore";
import type { BusinessOwner, Visitor } from "../../interfaces/intefaces";
import Logo from "../../components/componentiGenerali/logo/logo";

const RegisterForm = function () {
  const { t } = useTranslation();

  const roles = [
    { id: "turista", label: t("register.roleTurista"), icon: "🧳" },
    { id: "imprenditore", label: t("register.roleImprenditore"), icon: "📍" },
  ];
  const navigate = useNavigate();
  const { fetchAllEnums, NazionalitaEnums } = useEnumsStore();

  const [selectedRole, setSelectedRole] = useState("turista");

  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [email, setEmail] = useState("");
  const [telephone, setTelephone] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfermata, setPasswordConfermata] = useState("");
  const [biografy, setBiografy] = useState("");
  const [avatar, setAvatar] = useState("");
  const [preview, setPreview] = useState("");
  const [nazionalita, setNazionalita] = useState("");

  const [errors, setErrors] = useState<Record<string, string>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [toast, setToast] = useState<{
    show: boolean;
    type: "success" | "error" | "info";
    title: string;
    message: string;
  }>({ show: false, type: "info", title: "", message: "" });

  const showToast = (
    type: "success" | "error" | "info",
    title: string,
    message: string,
  ) => {
    setToast({ show: true, type, title, message });
  };

  const resetForm = () => {
    setName("");
    setSurname("");
    setEmail("");
    setTelephone("");
    setPassword("");
    setPasswordConfermata("");
    setBiografy("");
    setAvatar("");
    setNazionalita("");
    setPreview("");
    setErrors({});
  };

  const handleUrlSelect = (url: string) => {
    setAvatar(url);
    setPreview(url);
    setErrors((prev) => {
      const next = { ...prev };
      delete next.avatar;
      return next;
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({});
    setIsLoading(true);

    if (password !== passwordConfermata) {
      setIsLoading(false);
      setErrors((prev) => ({
        ...prev,
        passwordConfermata: t("Le password non corrispondono"),
      }));
      showToast("error", t("Errore"), t("Le password non corrispondono"));
      return;
    }

    if (!avatar) {
      setIsLoading(false);
      setErrors((prev) => ({
        ...prev,
        avatar: t("Carica una foto di profilo"),
      }));
      showToast("info", t("Info"), t("Carica una foto di profilo"));
      return;
    }

    type RegistrationPayload = Visitor | BusinessOwner;

    let daRegistrare: RegistrationPayload;

    switch (selectedRole) {
      case "turista":
        daRegistrare = {
          name,
          surname,
          email,
          telephone,
          password,
          biografy,
          avatar,
          nazionalita,
        } as Visitor;
        break;

      case "imprenditore":
        daRegistrare = {
          name,
          surname,
          email,
          telephone,
          password,
          biografy,
          avatar,
          nazionalita,
        } as BusinessOwner;
        break;

      default:
        throw new Error(`Ruolo non riconosciuto: ${selectedRole}`);
    }

    try {
      const api_url = import.meta.env.VITE_API_URL;

      let roleEndpoint: string;

      switch (selectedRole) {
        case "turista":
          roleEndpoint = "visitor";
          break;

        case "imprenditore":
          roleEndpoint = "owner";
          break;

        default:
          throw new Error(`Ruolo non riconosciuto: ${selectedRole}`);
      }

      const response = await fetch(`${api_url}/auth/register/${roleEndpoint}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(daRegistrare),
      });

      const data = await response.json();

      if (!response.ok) {
        if (data.errors && Object.keys(data.errors).length > 0) {
          setErrors(data.errors);
          showToast("error", t("Errore"), t("Correggi gli errori nel modulo"));
        } else {
          showToast(
            "error",
            t("Errore"),
            data.message || t("Oops! Qualcosa è andato storto"),
          );
        }
        setIsLoading(false);
      } else {
        resetForm();
        setIsLoading(false);
        setShowSuccessModal(true);
      }
    } catch {
      showToast("error", t("Errore"), t("Oops! Qualcosa è andato storto"));
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchAllEnums();
  }, [fetchAllEnums]);

  return (
    <>
      <ToastNotification
        show={toast.show}
        type={toast.type}
        title={toast.title}
        message={toast.message}
        onClose={() => setToast((prev) => ({ ...prev, show: false }))}
      />

      <Modal show={showSuccessModal} centered>
        <Modal.Header
          style={{ backgroundColor: "var(--blu-mare)", color: "white" }}
        >
          <Modal.Title className="d-flex align-items-center gap-2">
            <FiCheckCircle size={22} />
            {t("Registrazione Completata!")}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body className="text-center py-4">
          <div style={{ fontSize: "3.5rem" }}>✅</div>
          <p className="mt-3 fs-5">
            {t("Benvenuto! Verrai reindirizzato al login...")}
          </p>
        </Modal.Body>
        <Modal.Footer className="justify-content-center">
          <Button
            onClick={() => {
              setShowSuccessModal(false);
              navigate("/");
            }}
            style={{
              backgroundColor: "var(--blu-mare)",
              border: "none",
              borderRadius: "0.5rem",
              padding: "0.5rem 2rem",
            }}
          >
            {t("Continua")}
          </Button>
        </Modal.Footer>
      </Modal>

      <div className="register-page row m-0">
        <div className="blob blob-1"></div>
        <div className="blob blob-2"></div>

        <div className="hero p-5 col-5 d-none d-md-flex">
          <Logo tema="light" />
          <div className="hero-header">
            <h1 className="fw-bold">{t("login.heroTitle")}</h1>
            <p>{t("login.heroSubtitle")}</p>
          </div>
          <div className="d-flex gap-4">
            <span>
              <div className="fw-bold fs-4">180+</div>
              <div className="small">{t("login.stats.attractions")}</div>
            </span>
            <span>
              <div className="fw-bold fs-4">40</div>
              <div>{t("login.stats.municipalities")}</div>
            </span>
            <span>
              <div className="fw-bold fs-4">4.0★</div>
              <div>{t("login.stats.reviews")}</div>
            </span>
          </div>
        </div>

        <div className="col-12 col-md-7 page-content">
          <div className="form-wrapper">
            <h2 className="fw-bold">{t("login.welcomeBack")}</h2>
            <p className="text-secondary small">{t("register.subtitle")}</p>

            <div className="role-wrapper">
              {roles.map((role) => (
                <button
                  key={role.id}
                  type="button"
                  onClick={() => setSelectedRole(role.id)}
                  className={
                    selectedRole === role.id
                      ? "role-button role-button-active"
                      : "role-button"
                  }
                >
                  {role.icon} {role.label}
                </button>
              ))}
            </div>

            <Form
              className="mt-4 d-flex flex-column gap-3"
              onSubmit={handleSubmit}
            >
              <div className="d-flex gap-2">
                <Form.Group className="flex-fill">
                  <div style={{ position: "relative" }}>
                    <FiUser
                      style={{
                        position: "absolute",
                        left: "12px",
                        top: "50%",
                        transform: "translateY(-50%)",
                        color: "#1a7a96",
                        pointerEvents: "none",
                      }}
                    />
                    <Form.Control
                      type="text"
                      placeholder={t("Inserisci il tuo nome")}
                      value={name}
                      onChange={(e) => setName(e.target.value)}
                      isInvalid={!!errors.name}
                      required
                      style={{ paddingLeft: "40px" }}
                    />
                    <Form.Control.Feedback type="invalid">
                      {errors.name}
                    </Form.Control.Feedback>
                  </div>
                </Form.Group>
                <Form.Group className="flex-fill">
                  <div style={{ position: "relative" }}>
                    <FiUser
                      style={{
                        position: "absolute",
                        left: "12px",
                        top: "50%",
                        transform: "translateY(-50%)",
                        color: "#1a7a96",
                        pointerEvents: "none",
                      }}
                    />
                    <Form.Control
                      type="text"
                      placeholder={t("Inserisci il tuo cognome")}
                      value={surname}
                      onChange={(e) => setSurname(e.target.value)}
                      isInvalid={!!errors.surname}
                      required
                      style={{ paddingLeft: "40px" }}
                    />
                    <Form.Control.Feedback type="invalid">
                      {errors.surname}
                    </Form.Control.Feedback>
                  </div>
                </Form.Group>
              </div>

              <Form.Group>
                <div style={{ position: "relative" }}>
                  <FiMail
                    style={{
                      position: "absolute",
                      left: "12px",
                      top: "50%",
                      transform: "translateY(-50%)",
                      color: "#1a7a96",
                      pointerEvents: "none",
                    }}
                  />
                  <Form.Control
                    type="email"
                    placeholder={t("login.emailPlaceholder")}
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    isInvalid={!!errors.email}
                    required
                    style={{ paddingLeft: "40px" }}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.email}
                  </Form.Control.Feedback>
                </div>
              </Form.Group>

              <Form.Group>
                <div style={{ position: "relative" }}>
                  <FiPhone
                    style={{
                      position: "absolute",
                      left: "12px",
                      top: "50%",
                      transform: "translateY(-50%)",
                      color: "#1a7a96",
                      pointerEvents: "none",
                    }}
                  />
                  <Form.Control
                    type="tel"
                    placeholder={t("Inserisci il numero di telefono")}
                    value={telephone}
                    onChange={(e) => setTelephone(e.target.value)}
                    isInvalid={!!errors.telephone}
                    pattern="[0-9\s\-\+\(\)]+"
                    required
                    style={{ paddingLeft: "40px" }}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.telephone}
                  </Form.Control.Feedback>
                </div>
              </Form.Group>

              <Form.Group>
                <div style={{ position: "relative" }}>
                  <FiLock
                    style={{
                      position: "absolute",
                      left: "12px",
                      top: "50%",
                      transform: "translateY(-50%)",
                      color: "#1a7a96",
                      pointerEvents: "none",
                    }}
                  />
                  <Form.Control
                    type="password"
                    placeholder={t("Inserisci una password")}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    isInvalid={!!errors.password}
                    required
                    style={{ paddingLeft: "40px" }}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.password}
                  </Form.Control.Feedback>
                </div>
              </Form.Group>

              <Form.Group>
                <div style={{ position: "relative" }}>
                  <FiLock
                    style={{
                      position: "absolute",
                      left: "12px",
                      top: "50%",
                      transform: "translateY(-50%)",
                      color: "#1a7a96",
                      pointerEvents: "none",
                    }}
                  />
                  <Form.Control
                    type="password"
                    placeholder={t("Ripeti password")}
                    value={passwordConfermata}
                    onChange={(e) => setPasswordConfermata(e.target.value)}
                    isInvalid={!!errors.passwordConfermata}
                    required
                    style={{ paddingLeft: "40px" }}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.passwordConfermata}
                  </Form.Control.Feedback>
                </div>
              </Form.Group>

              <Form.Group>
                <Form.Control
                  as="textarea"
                  placeholder={t("Inserisci una tua breve biografia")}
                  value={biografy}
                  onChange={(e) => setBiografy(e.target.value)}
                  isInvalid={!!errors.biografy}
                  rows={3}
                  required
                />
                <Form.Control.Feedback type="invalid">
                  {errors.biografy}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group>
                <Form.Control
                  type="text"
                  placeholder={t("Carica la tua foto di profilo")}
                  value={avatar}
                  readOnly
                />
                <UploadFile onUrlSelect={handleUrlSelect} preview={preview} />
                {errors.avatar && (
                  <Form.Control.Feedback
                    type="invalid"
                    style={{ display: "block" }}
                  >
                    {errors.avatar}
                  </Form.Control.Feedback>
                )}
              </Form.Group>

              <Form.Group>
                <Form.Select
                  value={nazionalita}
                  onChange={(e) => setNazionalita(e.target.value)}
                  isInvalid={!!errors.nazionalita}
                >
                  <option value="" disabled>
                    {t("register.nazionalita")}
                  </option>
                  {NazionalitaEnums.map((n) => (
                    <option value={n.value} key={n.label}>
                      {n.bandiera} {n.label}
                    </option>
                  ))}
                </Form.Select>
                {errors.nazionalita && (
                  <Form.Control.Feedback
                    type="invalid"
                    style={{ display: "block" }}
                  >
                    {errors.nazionalita}
                  </Form.Control.Feedback>
                )}
              </Form.Group>

              <button
                type="submit"
                disabled={isLoading}
                className="role-button role-button-active mt-2 w-100 py-2"
              >
                {isLoading ? (
                  <>
                    <Spinner
                      as="span"
                      size="sm"
                      animation="border"
                      className="me-2"
                      role="status"
                    />
                    {t("Registrazione in corso...")}
                  </>
                ) : (
                  t("Registrati")
                )}
              </button>
            </Form>
            <div className="login-divider">
              <div className="login-divider-line" />
              <span>{t("login.or")}</span>
              <div className="login-divider-line" />
            </div>

            <div className="login-signup pb-3">
              {t("Hai gia un account?")}{" "}
              <Link to="/">{t("Accedi")}</Link>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default RegisterForm;
