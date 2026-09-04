import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import {
  FaCompass,
  FaUmbrellaBeach,
  FaStore,
  FaCog,
  FaEnvelope,
  FaLock,
  FaEye,
  FaEyeSlash,
  FaArrowRight,
  FaExclamationTriangle,
  FaCheckCircle,
} from "react-icons/fa";
import "./LoginPage.css";
import { useAuthStore } from "../../zustand/authStore";

type Role = "visitor" | "business" | "admin";
type Toast = { type: "error" | "success"; message: string } | null;

const ROLES: { key: Role; icon: React.ReactNode }[] = [
  { key: "visitor", icon: <FaUmbrellaBeach /> },
  { key: "business", icon: <FaStore /> },
  { key: "admin", icon: <FaCog /> },
];

const isValidEmail = (value: string) =>
  /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);

const LoginPage = function () {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { login } = useAuthStore();

  const [role, setRole] = useState<Role>("visitor");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailTouched, setEmailTouched] = useState(false);
  const [passwordTouched, setPasswordTouched] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [remember, setRemember] = useState(true);
  const [loading, setLoading] = useState(false);
  const [toast, setToast] = useState<Toast>(null);

  const emailError =
    emailTouched && !email
      ? t("login.emailRequired")
      : emailTouched && !isValidEmail(email)
        ? t("login.emailInvalid")
        : null;

  const passwordError =
    passwordTouched && !password
      ? t("login.passwordRequired")
      : passwordTouched && password.length < 6
        ? t("login.passwordTooShort")
        : null;

  const showToast = (next: Toast) => {
    setToast(next);
    setTimeout(() => setToast(null), 3200);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setEmailTouched(true);
    setPasswordTouched(true);

    const hasEmailError = !email || !isValidEmail(email);
    const hasPasswordError = !password || password.length < 6;
    if (hasEmailError || hasPasswordError) {
      showToast({ type: "error", message: t("login.toastError") });
      return;
    }

    setLoading(true);
    setToast(null);

    const success = await login({ email, password });

    setLoading(false);
    if (success) {
      showToast({ type: "success", message: t("login.toastSuccess") });
      setTimeout(() => {
        if (role === "visitor") navigate("/visitor/home");
        if (role === "business") navigate("/business/dashboard");
        if (role === "admin") navigate("/admin/dashboard");
      }, 1400);
    } else {
      showToast({ type: "error", message: t("login.toastError") });
    }
  };

  return (
    <div className="login-page">
      <div className="login-blob login-blob-1" />
      <div className="login-blob login-blob-2" />

      <div className="login-hero">
        <div className="login-hero-pattern" />

        <div className="login-hero-brand">
          <div className="login-hero-brand-mark">
            <FaCompass />
          </div>
          <div className="login-hero-brand-name">{t("login.brand")}</div>
        </div>

        <div className="login-hero-headline">
          <h1>{t("login.heroTitle")}</h1>
          <p>{t("login.heroSubtitle")}</p>
        </div>

        <div className="login-hero-stats">
          <div className="login-hero-stat">
            <strong>180+</strong>
            <span>{t("login.stats.attractions")}</span>
          </div>
          <div className="login-hero-stat">
            <strong>40</strong>
            <span>{t("login.stats.municipalities")}</span>
          </div>
          <div className="login-hero-stat">
            <strong>4.8★</strong>
            <span>{t("login.stats.reviews")}</span>
          </div>
        </div>
      </div>

      <div className="login-form-panel">
        <div className="login-card">
          <div className="login-card-header">
            <div>
              <h2>{t("login.welcomeBack")}</h2>
              <p>{t("login.subtitle")}</p>
            </div>
          </div>

          <div className="login-role-tabs">
            {ROLES.map((r) => (
              <button
                key={r.key}
                type="button"
                className={`login-role-tab${role === r.key ? " active" : ""}`}
                onClick={() => setRole(r.key)}
              >
                <span>{r.icon}</span>
                <span>{t(`login.role.${r.key}`)}</span>
              </button>
            ))}
          </div>

          <form onSubmit={handleSubmit}>
            <div className="login-fields">
              <div className="login-field">
                <label htmlFor="login-email">{t("login.email")}</label>
                <div
                  className={`login-field-wrap${emailError ? " error" : ""}`}
                >
                  <input
                    id="login-email"
                    type="email"
                    placeholder={t("login.emailPlaceholder")}
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    onBlur={() => setEmailTouched(true)}
                  />
                  <span className="login-field-icon">
                    <FaEnvelope />
                  </span>
                </div>
                {emailError && (
                  <div className="login-field-error">{emailError}</div>
                )}
              </div>

              <div className="login-field">
                <div className="login-field-row">
                  <label htmlFor="login-password">{t("login.password")}</label>
                  <a href="#">{t("login.forgotPassword")}</a>
                </div>
                <div
                  className={`login-field-wrap${passwordError ? " error" : ""}`}
                >
                  <input
                    id="login-password"
                    type={showPassword ? "text" : "password"}
                    className="has-toggle"
                    placeholder={t("login.passwordPlaceholder")}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    onBlur={() => setPasswordTouched(true)}
                  />
                  <span className="login-field-icon">
                    <FaLock />
                  </span>
                  <button
                    type="button"
                    className="login-field-toggle"
                    onClick={() => setShowPassword((v) => !v)}
                  >
                    {showPassword ? <FaEyeSlash /> : <FaEye />}
                  </button>
                </div>
                {passwordError && (
                  <div className="login-field-error">{passwordError}</div>
                )}
              </div>

              <label className="login-remember">
                <input
                  type="checkbox"
                  checked={remember}
                  onChange={() => setRemember((v) => !v)}
                />
                {t("login.remember")}
              </label>

              <button className="login-submit" type="submit" disabled={loading}>
                {loading ? (
                  <>
                    <span className="login-spinner" />
                    <span>{t("login.submitting")}</span>
                  </>
                ) : (
                  <>
                    <span>
                      {t("login.submit", { role: t(`login.role.${role}`) })}
                    </span>
                    <FaArrowRight />
                  </>
                )}
              </button>
            </div>
          </form>

          <div className="login-divider">
            <div className="login-divider-line" />
            <span>{t("login.or")}</span>
            <div className="login-divider-line" />
          </div>

          <div className="login-signup">
            {t("login.noAccount")}{" "}
            <Link to="/register">{t("login.signUp")}</Link>
          </div>
        </div>
      </div>

      {toast && (
        <div className={`login-toast login-toast-${toast.type}`}>
          <span>
            {toast.type === "error" ? (
              <FaExclamationTriangle />
            ) : (
              <FaCheckCircle />
            )}
          </span>
          <span>{toast.message}</span>
        </div>
      )}
    </div>
  );
};

export default LoginPage;
