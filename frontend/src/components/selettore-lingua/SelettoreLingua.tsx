import { useTranslation } from "react-i18next";
import { useState, useRef, useEffect } from "react";
import "./SelettoreLingua.css";

const lingue = [
  { code: "it", bandiera: "🇮🇹", label: "ITALIANO" },
  { code: "en", bandiera: "🇬🇧", label: "ENGLISH" },
  { code: "de", bandiera: "🇩🇪", label: "DEUTSCH" },
];

const SelettoreLingua = function () {
  const { i18n } = useTranslation();
  const [aperto, setAperto] = useState(false);
  const ref = useRef<HTMLDivElement>(null);

  const linguaAttuale =
    lingue.find((l) => i18n.language.startsWith(l.code)) ?? lingue[0];

  useEffect(() => {
    const chiudiSeFuori = (e: MouseEvent) => {
      if (ref.current && !ref.current.contains(e.target as Node)) {
        setAperto(false);
      }
    };
    document.addEventListener("mousedown", chiudiSeFuori);
    return () => document.removeEventListener("mousedown", chiudiSeFuori);
  }, []);

  return (
    <div className="sel-lingua" ref={ref}>
      <button
        type="button"
        className="sel-lingua-trigger"
        onClick={() => setAperto((v) => !v)}
        aria-haspopup="listbox"
        aria-expanded={aperto}
      >
        <span>{linguaAttuale.bandiera}</span>
        <span>{linguaAttuale.label}</span>
        <i className={`sel-lingua-chevron${aperto ? " aperto" : ""}`}>▾</i>
      </button>

      {aperto && (
        <div className="sel-lingua-dropdown" role="listbox">
          {lingue.map((l) => (
            <button
              key={l.code}
              type="button"
              role="option"
              aria-selected={l.code === linguaAttuale.code}
              className={`sel-lingua-opzione${l.code === linguaAttuale.code ? " attiva" : ""}`}
              onClick={() => {
                i18n.changeLanguage(l.code);
                setAperto(false);
              }}
            >
              <span>{l.bandiera}</span>
              <span>{l.label}</span>
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default SelettoreLingua;
