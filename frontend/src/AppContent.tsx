import "./genericStyle.css";
import { Route, Routes } from "react-router-dom";
import RegisterForm from "./pages/registerPage/RegisterForm";
import LoginPage from "./pages/LoginPage/LoginPage";
import UserLayout from "./pages/visitor_pages/userLayout/UserLayout";
import { ProtectedRoute } from "./configurazione/ProtectedRoute";
import UserHome from "./pages/visitor_pages/userHome/UserHome";
import ProfilePage from "./pages/visitor_pages/profilePage/ProfilePage";
import EsploraPage from "./pages/visitor_pages/attrazioniPage/EsploraPage";
import StrutturaDetails from "./pages/visitor_pages/strutturaDetailsPage/StrutturaDetails";
import StrutturaPage from "./pages/visitor_pages/strutturePage/StrutturaPage";
import ShowStruttureTipologia from "./pages/visitor_pages/showStruttureTipologia/ShowStruttureTipologia";
import BusinessLayout from "./pages/business_pages/businessLayout/BusinessLayout";
import NewPropertyForm from "./pages/business_pages/newPropertyForm/NewPropertyForm";
import MyProperties from "./pages/business_pages/myProperties/MyProperties";
import PropertyOffice from "./pages/business_pages/propertyOffice/PropertyOffice";
import BusinessDashboard from "./pages/business_pages/businessDashboard/BusinessDashboard";
import NotFound from "./pages/notFoundPage/NotFoundPage";

const AppContent = function () {
  return (
    <main className="flex-grow-1 main">
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/register" element={<RegisterForm />} />
        <Route
          element={
            <ProtectedRoute requiredRole="VISITOR">
              <UserLayout />
            </ProtectedRoute>
          }
        >
          <Route path="/visitor/home" element={<UserHome />} />
          <Route path="/visitor/profile" element={<ProfilePage />} />
          <Route path="/visitor/esplora" element={<EsploraPage />} />
          <Route
            path="/visitor/esplora/:strutturaId"
            element={<StrutturaDetails />}
          />
          <Route path="/visitor/strutture" element={<StrutturaPage />} />
          <Route
            path="/visitor/strutture/:strutturaTipologia"
            element={<ShowStruttureTipologia />}
          />
          <Route path="*" element={<NotFound />} />
        </Route>
        <Route
          element={
            <ProtectedRoute requiredRole="BUSINESS_OWNER">
              <BusinessLayout />
            </ProtectedRoute>
          }
        >
          <Route path="/business/dashboard" element={<BusinessDashboard />} />
          <Route path="/business/profile" element={<ProfilePage />} />
          <Route path="/business/new-property" element={<NewPropertyForm />} />
          <Route path="/business/strutture" element={<MyProperties />} />
          <Route
            path="/business/propertyOffice/:strutturaId"
            element={<PropertyOffice />}
          />
          <Route path="*" element={<NotFound />} />
        </Route>
        <Route
          element={
            <ProtectedRoute requiredRole="ADMIN">
              <UserLayout />
            </ProtectedRoute>
          }
        >
          <Route path="/businessowner/home" element={<UserHome />} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </main>
  );
};

export default AppContent;
