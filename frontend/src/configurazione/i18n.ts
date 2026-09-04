import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";

import it from "../languages/it/translation.json";
import de from "../languages/de/translation.json";
import en from "../languages/en/translation.json";

i18n
  .use(LanguageDetector)
  .use(initReactI18next)
  .init({
    resources: {
      it: { translation: it },
      en: { translation: en },
      de: { translation: de },
    },
    fallbackLng: "it",
    interpolation: {
      escapeValue: false,
    },
  });

export default i18n;
