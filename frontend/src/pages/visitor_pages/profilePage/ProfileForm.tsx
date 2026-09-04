import { Form, Spinner } from "react-bootstrap";
import "../../../pages/registerPage/registerForm.css";
import {
  FiMail,
  FiLock,
  FiPhone,
  FiUser,
  FiEdit2,
  FiCheck,
  FiX,
} from "react-icons/fi";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import UploadFile from "../../../components/componentiGenerali/UploadFile";
import ToastNotification from "../../../components/componentiGenerali/ToastNotification";
import { useEnumsStore } from "../../../zustand/enumsStore";
import { useAuthStore } from "../../../zustand/authStore";
import type { Visitor } from "../../../interfaces/intefaces";

type Mode = "view" | "edit";

interface ProfileFormProps {
  userName?: string;
}

const ProfileForm = function ({ userName }: ProfileFormProps) {
  const { t } = useTranslation();
  const { user, updateCurrentUser } = useAuthStore();
  const { fetchAllEnums, NazionalitaEnums } = useEnumsStore();

  const [mode, setMode] = useState<Mode>("view");

  const visitor = user as Visitor | null;

  const [name, setName] = useState(visitor?.name ?? "");
  const [surname, setSurname] = useState(visitor?.surname ?? "");
  const [email, setEmail] = useState(visitor?.email ?? "");
  const [telephone, setTelephone] = useState(visitor?.telephone ?? "");
  const [password, setPassword] = useState("");
  const [biografy, setBiografy] = useState(visitor?.biografy ?? "");
  const [avatar, setAvatar] = useState(visitor?.avatar ?? "");
  const [preview, setPreview] = useState(visitor?.avatar ?? "");
  const [nazionalita, setNazionalita] = useState(visitor?.nazionalita ?? "");

  const [errors, setErrors] = useState<Record<string, string>>({});
  const [isLoading, setIsLoading] = useState(false);
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
  ) => setToast({ show: true, type, title, message });

  const resetToUserData = () => {
    setName(visitor?.name ?? "");
    setSurname(visitor?.surname ?? "");
    setEmail(visitor?.email ?? "");
    setTelephone(visitor?.telephone ?? "");
    setPassword("");
    setBiografy(visitor?.biografy ?? "");
    setAvatar(visitor?.avatar ?? "");
    setPreview(visitor?.avatar ?? "");
    setNazionalita(visitor?.nazionalita ?? "");
    setErrors({});
  };

  const handleUrlSelect = (url: string) => {
    setAvatar(url);
    setPreview(url);
    setErrors((prev) => {
      const n = { ...prev };
      delete n.avatar;
      return n;
    });
  };

  const handleModeChange = (newMode: Mode) => {
    if (newMode === "view") resetToUserData();
    setMode(newMode);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({});
    setIsLoading(true);

    const patch: Record<string, unknown> = {};
    if (name !== visitor?.name) patch.name = name;
    if (surname !== visitor?.surname) patch.surname = surname;
    if (email !== visitor?.email) patch.email = email;
    if (telephone !== visitor?.telephone) patch.telephone = telephone;
    if (biografy !== visitor?.biografy) patch.biografy = biografy;
    if (avatar !== visitor?.avatar) patch.avatar = avatar;
    if (nazionalita !== visitor?.nazionalita) patch.nazionalita = nazionalita;
    if (password) patch.password = password;

    const userId = visitor?.id;
    if (!userId) {
      showToast("error", t("Errore"), t("profilo.id_non_trovato"));
      setIsLoading(false);
      return;
    }

    const success = await updateCurrentUser(patch);

    setIsLoading(false);
    if (success) {
      showToast("success", t("Successo"), t("profilo.aggiornato"));
      setPassword("");
      setMode("view");
    } else {
      showToast("error", t("Errore"), t("Oops! Qualcosa è andato storto"));
    }
  };

  useEffect(() => {
    fetchAllEnums();
  }, [fetchAllEnums]);

  const isView = mode === "view";

  const iconStyle = (extra?: React.CSSProperties): React.CSSProperties => ({
    position: "absolute",
    left: "12px",
    top: "50%",
    transform: "translateY(-50%)",
    color: "#1a7a96",
    pointerEvents: "none",
    ...extra,
  });

  const inputWrap = { position: "relative" as const };
  const paddedLeft = { paddingLeft: "40px" };
  const controlBg: React.CSSProperties = isView
    ? { paddingLeft: "40px", backgroundColor: "#f5f5f5" }
    : { paddingLeft: "40px", backgroundColor: "white" };

  return (
    <>
      <ToastNotification
        show={toast.show}
        type={toast.type}
        title={toast.title}
        message={toast.message}
        onClose={() => setToast((prev) => ({ ...prev, show: false }))}
      />

      <div className="form-wrapper">
        <div className="d-flex align-items-center justify-content-between mb-1">
          <div>
            <h2 className="fw-bold mb-1 text-dark">
              {userName ?? `${visitor?.name ?? ""} ${visitor?.surname ?? ""}`}
            </h2>
            <p className="text-secondary small mb-0">
              {isView ? t("profilo.visualizzazione") : t("profilo.modifica")}
            </p>
          </div>
          {isView && (
            <div
              style={{
                width: 60,
                height: 60,
                borderRadius: "50%",
                overflow: "hidden",
                border: "2px solid #1a7a96",
                flexShrink: 0,
              }}
            >
              {preview ? (
                <img
                  src={preview}
                  alt="avatar"
                  style={{ width: "100%", height: "100%", objectFit: "cover" }}
                />
              ) : (
                <div
                  style={{
                    width: "100%",
                    height: "100%",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    backgroundColor: "#e8f4f8",
                  }}
                >
                  <FiUser size={26} color="#1a7a96" />
                </div>
              )}
            </div>
          )}
        </div>

        <hr />

        <Form className="d-flex flex-column gap-3 mt-3" onSubmit={handleSubmit}>
          <div className="d-flex gap-2">
            <Form.Group className="flex-fill">
              <div style={inputWrap}>
                <FiUser style={iconStyle()} />
                <Form.Control
                  type="text"
                  placeholder={t("Inserisci il tuo nome")}
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  isInvalid={!!errors.name}
                  disabled={isView}
                  style={isView ? controlBg : paddedLeft}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.name}
                </Form.Control.Feedback>
              </div>
            </Form.Group>
            <Form.Group className="flex-fill">
              <div style={inputWrap}>
                <FiUser style={iconStyle()} />
                <Form.Control
                  type="text"
                  placeholder={t("Inserisci il tuo cognome")}
                  value={surname}
                  onChange={(e) => setSurname(e.target.value)}
                  isInvalid={!!errors.surname}
                  disabled={isView}
                  style={isView ? controlBg : paddedLeft}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.surname}
                </Form.Control.Feedback>
              </div>
            </Form.Group>
          </div>

          <Form.Group>
            <div style={inputWrap}>
              <FiMail style={iconStyle()} />
              <Form.Control
                type="email"
                placeholder={t("Inserisci la tua email")}
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                isInvalid={!!errors.email}
                disabled={isView}
                style={isView ? controlBg : paddedLeft}
              />
              <Form.Control.Feedback type="invalid">
                {errors.email}
              </Form.Control.Feedback>
            </div>
          </Form.Group>

          <Form.Group>
            <div style={inputWrap}>
              <FiPhone style={iconStyle()} />
              <Form.Control
                type="tel"
                placeholder={t("Inserisci il numero di telefono")}
                value={telephone}
                onChange={(e) => setTelephone(e.target.value)}
                isInvalid={!!errors.telephone}
                pattern="[0-9\s\-\+\(\)]+"
                disabled={isView}
                style={isView ? controlBg : paddedLeft}
              />
              <Form.Control.Feedback type="invalid">
                {errors.telephone}
              </Form.Control.Feedback>
            </div>
          </Form.Group>

          {!isView && (
            <Form.Group>
              <div style={inputWrap}>
                <FiLock style={iconStyle()} />
                <Form.Control
                  type="password"
                  placeholder={t("profilo.nuova_password")}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  isInvalid={!!errors.password}
                  style={paddedLeft}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.password}
                </Form.Control.Feedback>
              </div>
            </Form.Group>
          )}

          <Form.Group>
            <Form.Control
              as="textarea"
              placeholder={t("Inserisci una tua breve biografia")}
              value={biografy}
              onChange={(e) => setBiografy(e.target.value)}
              isInvalid={!!errors.biografy}
              rows={3}
              disabled={isView}
              style={isView ? { backgroundColor: "#f5f5f5" } : {}}
            />
            <Form.Control.Feedback type="invalid">
              {errors.biografy}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group>
            <Form.Control
              type="text"
              placeholder={t("profilo.url_foto")}
              value={avatar}
              readOnly
              style={{ backgroundColor: "#f5f5f5" }}
            />
            {!isView && (
              <UploadFile onUrlSelect={handleUrlSelect} preview={preview} />
            )}
            {isView && preview && (
              <div className="mt-2 text-center">
                <img
                  src={preview}
                  alt="Preview"
                  style={{
                    width: 100,
                    height: 100,
                    borderRadius: "50%",
                    objectFit: "cover",
                    border: "2px solid #ccc",
                  }}
                />
              </div>
            )}
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
              disabled={isView}
              style={isView ? { backgroundColor: "#f5f5f5" } : {}}
            >
              <option value="" disabled>
                {t("profilo.seleziona_nazionalita")}
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

          {isView ? (
            <button
              type="button"
              className="role-button role-button-active mt-2 w-100 py-2"
              onClick={() => handleModeChange("edit")}
            >
              <FiEdit2 className="me-2" />
              {t("profilo.modifica_dati")}
            </button>
          ) : (
            <div className="d-flex gap-2 mt-2">
              <button
                type="submit"
                disabled={isLoading}
                className="role-button role-button-active flex-fill py-2"
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
                    {t("profilo.salvataggio")}
                  </>
                ) : (
                  <>
                    <FiCheck className="me-2" />
                    {t("profilo.salva")}
                  </>
                )}
              </button>
              <button
                type="button"
                className="role-button flex-fill py-2"
                onClick={() => handleModeChange("view")}
                disabled={isLoading}
              >
                <FiX className="me-2" />
                {t("profilo.annulla")}
              </button>
            </div>
          )}
        </Form>
      </div>
    </>
  );
};

export default ProfileForm;
