/* export interface User {
    id: string;
    username: string;
    role: string;
    phoneNumber: string;
    status: string;
    userPixUrl: string;
    firstName: string;
    lastName: string;
    token: string;
    verified: string;
} */

export interface userRegistrationModel {
  fullName?: string;
  email?: string;
  password?: string;
}

export interface Authority {
  authority: string;
}

export interface Role {
  id: number;
  name: string;
  createdDate: string;
  lastModifiedDate: string | null;
}

export interface Portfolio {
  id: number;
  createdDate: string;
  lastModifiedDate: string | null;
  createdBy: number;
  lastModifiedBy: number | null;
  portfolioName: string;
  portfolioType: string;
  status: string;
}

export interface Wallet {
  id: number;
  createdDate: string;
  lastModifiedDate: string | null;
  createdBy: number;
  lastModifiedBy: number | null;
  balance: number;
  status: string;
}

export interface User {
  id: number;
  fullName: string;
  email: string;
  accountLocked: boolean;
  enabled: boolean;
  roles: Role[];
  portfolios: Portfolio[];
  wallet: Wallet[];
  createdDate: string;
  lastModifiedDate: string;
  name: string;
  username: string;
  authorities: Authority[];
  credentialsNonExpired: boolean;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
}
