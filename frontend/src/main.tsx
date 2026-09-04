import { createRoot } from "react-dom/client";
import "./configurazione/i18n";
import App from "./App.tsx";

createRoot(document.getElementById("root")!).render(<App />);
