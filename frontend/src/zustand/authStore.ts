import { create } from "zustand";
import { persist } from "zustand/middleware";
import type {
  Admin,
  BusinessOwner,
  CredenzialiLogin,
  LoginResponse,
  Visitor,
} from "../interfaces/intefaces";

type AnyUser = Visitor | Admin | BusinessOwner;

interface AuthStore {
  user: AnyUser | null;
  role: string | null;
  isAuthenticated: boolean;

  login: (credenziali: CredenzialiLogin) => Promise<boolean>;
  logout: () => void;
  updateCurrentUser: (data: Record<string, unknown>) => Promise<boolean>;
}

export const useAuthStore = create<AuthStore>()(
  persist(
    (set) => ({
      user: null,
      role: null,
      isAuthenticated: false,

      login: async (credenziali: CredenzialiLogin) => {
        try {
          const api_url = import.meta.env.VITE_API_URL;
          const response = await fetch(`${api_url}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(credenziali),
          });

          const accessData: LoginResponse = await response.json();

          if (response.ok) {
            set({
              user: accessData.user,
              role: accessData.role,
              isAuthenticated: true,
            });
            return true;
          } else {
            set({ user: null, role: null, isAuthenticated: false });
            return false;
          }
        } catch (error) {
          console.error("Errore login:", error);
          set({ user: null, role: null, isAuthenticated: false });
          return false;
        }
      },

      logout: () => {
        set({ user: null, role: null, isAuthenticated: false });
        const api_url = import.meta.env.VITE_API_URL;
        fetch(`${api_url}/auth/logout`, {
          method: "POST",
          credentials: "include",
        }).catch(() => {});
      },

      updateCurrentUser: async (data: Record<string, unknown>) => {
        try {
          const api_url = import.meta.env.VITE_API_URL;
          const response = await fetch(`${api_url}/users/me`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(data),
          });

          if (response.ok) {
            const updated: AnyUser = await response.json();
            set((state) => ({
              user: { ...state.user, ...updated } as AnyUser,
            }));
            return true;
          }
          return false;
        } catch (error) {
          console.error("Errore updateUser:", error);
          return false;
        }
      },
    }),
    { name: "auth-storage" },
  ),
);
