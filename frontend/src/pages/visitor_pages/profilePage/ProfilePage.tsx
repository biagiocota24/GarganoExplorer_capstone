import { useAuthStore } from "../../../zustand/authStore";
import ProfileForm from "./ProfileForm";
import { useTranslation } from "react-i18next";

const ProfilePage = function () {
  const { user } = useAuthStore();
  const { t } = useTranslation();

  if (!user) {
    return (
      <div className="d-flex justify-content-center align-items-center mt-5">
        <p className="text-secondary">{t("profilo.nessun_utente")}</p>
      </div>
    );
  }

  const userName = `${user.name} ${user.surname}`;

  return (
    <div className="d-flex justify-content-center py-5 px-3">
      <div style={{ width: "100%", maxWidth: 620 }}>
        <ProfileForm userName={userName} />
      </div>
    </div>
  );
};

export default ProfilePage;
