import { User } from '../models/user.model';

export interface AuthState {
  user?: User;
  isLoggingIn: boolean;
  isAuthenticated: boolean;
}

export interface LoginRequest {
  email?: string;
  password?: string;
}

export interface AccountVerificationRequest {
  email: string;
  oldPassword: string;
  newPassword: string;
}


export interface LoginResponse {
  user: User;
  token: string;
}
