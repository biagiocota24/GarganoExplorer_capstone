import { Navigate } from "react-router-dom";
import { useAuthStore } from "../zustand/authStore";

interface ProtectedRouteProps {
  children: React.ReactNode;
  requiredRole?: "VISITOR" | "ADMIN" | "BUSINESS_OWNER";
}

export const ProtectedRoute = ({
  children,
  requiredRole,
}: ProtectedRouteProps) => {
  const { isAuthenticated, role } = useAuthStore();

  if (!isAuthenticated) {
    return <Navigate to="/" replace />;
  }

  if (requiredRole && role !== requiredRole) {
    return <Navigate to="/unauthorized" replace />;
  }

  return <>{children}</>;
};
