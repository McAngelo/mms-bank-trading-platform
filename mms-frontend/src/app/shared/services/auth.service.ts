import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap, map } from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Logger } from './logger.service';
import { LoginRequest, AccountVerificationRequest, LoginResponse } from '../models/auth.model';
import { ApiResponse } from '../models/api-response';
import { filter } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';


const log = new Logger('HttpConfigInterceptor');

import { environment } from '../../../environments/environment';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl: string = environment._apiEndpoint;
  public headers: any = new HttpHeaders();
  public token: string = '';
  public loggedUser?:User;
  private readonly JWT_TOKEN: string = 'JWT_TOKEN';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);

  constructor(private _http: HttpClient, private _router: Router) {  }

  login(credentials: LoginRequest): Observable<any> {
    return this._http.post<ApiResponse<any>>(
        `${this.baseUrl}/v1/auth/login`,
        credentials
      )
      .pipe(
        map((response: ApiResponse<any>) => {
          let user:any = response.data;
          this.doLoginUser(user, user.token);
          return response.data;
        }));
  }

  verifyAccount(credentials: AccountVerificationRequest): Observable<any> {
    return this._http.post<ApiResponse<any>>(
        `${this.baseUrl}/v1/auth/account-validation`,
        credentials
      )
      .pipe(
        map((response: ApiResponse<any>) => {
          let user:any = response.data;
          this.doLoginUser(user, user.token);
          return response.data;
        }));
  }

  forgotternPassword(phoneNumber: string): Observable<any> {
    return this._http.get<ApiResponse<any>>(
        `${this.baseUrl}/v1/auth/forgotten-password/${phoneNumber}`
      )
      .pipe(
        map((response: ApiResponse<any>) => {
          let user:any = response.data;
          this.doLoginUser(user, user.token);
          return response.data;
        }));
  }

  private doLoginUser(user: User, token: string): void {
    this.loggedUser = user;
    this.storeJwtToken(token);
    if(user.enabled == true){
      const userObj:string = JSON.stringify(user);
      sessionStorage.setItem('userObj', userObj);
      this.isAuthenticatedSubject.next(true);
    }
  }

  private storeJwtToken(jwt: string): void {
    sessionStorage.setItem(this.JWT_TOKEN, jwt);
  }

  logout(): void {
    sessionStorage.removeItem(this.JWT_TOKEN);
    this.isAuthenticatedSubject.next(false);
    sessionStorage.clear();
    this._router.navigate(['/']);
  }

  // TODO: Implement this features later at the API side

  getCurrentAuhUser(): any {
    return this._http.get('https"//api.escuelajs.co/api/v1/auth/profile');
  }

  isLoggedIn(): any {
    return !!sessionStorage.getItem(this.JWT_TOKEN);
  }

  isTokenExpired(): any {
    const tokens = sessionStorage.getItem(this.JWT_TOKEN);
    if(!tokens) return true;
    const token = JSON.parse(tokens).access_token;

    const decoded = jwtDecode(token);

    if(!decoded.exp) return true;

    const expirationDate = decoded.exp * 1000;

    const now = new Date().getTime();

    return expirationDate < now;
  }

  refreshToken(): any {
    let tokens: any = sessionStorage.getItem(this.JWT_TOKEN);

    if (!tokens) return;

    tokens = JSON.parse(tokens);

    let refreshToken = tokens.refresh_token;

    return this._http.post<ApiResponse<any>>('https://api.escuelajs.co/api/v1/auth/refresh-token', { refreshToken }).pipe(
      tap((tokens: any) => this.storeJwtToken(JSON.stringify(tokens)))
    );
  }


}
