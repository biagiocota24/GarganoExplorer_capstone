// USERS & REGISTER & LOGIN
export interface Visitor {
  id?: number;
  name: string;
  surname: string;
  email: string;
  telephone: string;
  password: string;
  biografy: string;
  avatar: string;
  nazionalita: string;
}

export interface BusinessOwner {
  id?: number;
  name: string;
  surname: string;
  email: string;
  telephone: string;
  password: string;
  biografy: string;
  avatar: string;
  nazionalita?: string;
}

export interface Admin {
  id?: number;
  name: string;
  surname: string;
  email: string;
  telephone: string;
  password: string;
  biografy: string;
  avatar: string;
  dataAssunzione: Date;
  nazionalita?: string;
}

export interface CredenzialiLogin {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  type: string;
  role: string;
  user: Admin | BusinessOwner | Visitor;
}

export interface FileUploadProps {
  label: string;
  onFileSelect: (file: File | null) => void;
  preview?: string;
}
