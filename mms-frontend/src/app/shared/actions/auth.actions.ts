import { createAction, props } from '@ngrx/store';
import { AuthState, LoginRequest } from '@/shared/models/auth.model';

export const USER_LOGIN = '[Login Page] Login';
export const RESET_AUTH = '[Login Page] Reset Auth';
export const USER_LOGOUT = '[Main Page] Logout';
export const FORCE_USER_LOGOUT = '[Login Page] Force user logout';
export const USER_LOGOUT_SUCCESS = '[Main Page] Logout Success';
export const USER_LOGIN_SUCESS = '[Login Page] Login Success';
export const USER_LOGIN_FAILURE = '[Login Page] Login Failure';

export const login = createAction(USER_LOGIN, props<LoginRequest>());

export const loginSuccess = createAction(USER_LOGIN_SUCESS, props<AuthState>());

export const loginFailure = createAction(USER_LOGIN_FAILURE);

export const logout = createAction(USER_LOGOUT);

export const forceLogout = createAction(FORCE_USER_LOGOUT);

export const resetAuthStore = createAction(RESET_AUTH);

export const logoutSuccess = createAction(USER_LOGOUT_SUCCESS);
