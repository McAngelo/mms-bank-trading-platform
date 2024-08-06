import { Action, createReducer, on } from '@ngrx/store';
import * as fromAuthActions from '@/shared/actions/auth.actions';
import { AuthState } from '@/shared/models/auth.model';
import * as storage from '@/shared/stores/state-storage';

export const initialAuthState: AuthState = {
  user: storage.getItem('auth').user,
  isAuthenticated: storage.getItem('auth').isAuthenticated ?? false,
  isLoggingIn: false,
};

export const authFeatureKey: string = 'auth';

const authReducer = createReducer(
  initialAuthState,
  on(fromAuthActions.loginFailure, () => ({
    ...initialAuthState,
  })),
  on(fromAuthActions.loginSuccess, (state: AuthState, data: AuthState) => ({
    ...data,
    isLoggingIn: false,
  })),
  on(fromAuthActions.login, (state: AuthState) => ({
    ...state,
    isLoggingIn: true,
  })),
  on(fromAuthActions.resetAuthStore, (state: any) => ({
    ...state,
    user: {},
    isAuthenticated: false,
  })),
  on(fromAuthActions.logoutSuccess, (state: any) => ({
    ...state,
    isLoggingIn: false,
    isAuthenticated: false,
    user: {},
  }))
);

export function reducer(state: AuthState | undefined, action: Action): any {
  return authReducer(state, action);
}
