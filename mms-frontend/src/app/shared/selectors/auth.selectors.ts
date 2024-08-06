import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AuthState } from '@/shared/models/auth.model';

import * as fromStore from '@/shared/reducers/auth.reducers';

const authSelector = createFeatureSelector<AuthState>(fromStore.authFeatureKey);

export const selectIsLoggingIn = createSelector(
  authSelector,
  (state: AuthState) => state.isLoggingIn
);

export const selectIsAuthenticated = createSelector(
  authSelector,
  (state: AuthState) => state.isAuthenticated
);

export const userInfo = createSelector(
  authSelector,
  (state: AuthState) => state.user
);
