import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent, HttpEventType } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Logger } from './logger.service';
//import { AuthenticationService } from "@/shared";
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiClientService {
  private baseUrl: string = environment._apiEndpoint;
  public headers: any = new HttpHeaders();
  public token: string = '';

  //private _authenticationService: AuthenticationService
  constructor(private _http: HttpClient, private _router: Router, ) {

  }

  /* public async getToken() {
    this._authenticationService.authorize()
      .subscribe( (event: HttpEvent<any>) => {
        switch (event.type) {
          case HttpEventType.Response:
            const response = event.body;
            sessionStorage.setItem('authKeys', JSON.stringify(response));
            this.token = response.access_token;
            break;
        }
      });
  } */

  public async readAllApiService(endpoint: string): Promise<Observable<HttpEvent<any>>> {
    let authKeys = JSON.parse(sessionStorage.getItem('authKeys') || '{}');
    let token = (Object.keys(authKeys).length > 0)? authKeys.access_token : '';
    const url = `${this.baseUrl}/${endpoint}`;
    const req = new HttpRequest('GET', url, {
      reportProgress: true,
      /*headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      }),*/
    });

    return this._http.request(req);
  }

  public async readOneApiService(endpoint: string, id: string): Promise<Observable<HttpEvent<any>>> {
    let authKeys = JSON.parse(sessionStorage.getItem('authKeys') || '{}');
    let token = (Object.keys(authKeys).length > 0)? authKeys.access_token : '';
    const url = `${this.baseUrl}/${endpoint}/${id}`;
    const req = new HttpRequest('GET', url, {
      reportProgress: true,
      /*headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      }),*/
    });

    return this._http.request(req);
  }

  public async addApiService(endpointUrl: string, data: any): Promise<Observable<HttpEvent<any>>> {
    let authKeys = JSON.parse(sessionStorage.getItem('authKeys') || '{}');
    let token = (Object.keys(authKeys).length > 0)? authKeys.access_token : '';
    const url = `${this.baseUrl}/${endpointUrl}`;
    const req = new HttpRequest('POST', url, data, {
      reportProgress: true,
      /*headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      }),*/
    });
    return this._http.request(req);
  }

  public async addApiFormDataService(endpointUrl: string, data: FormData): Promise<Observable<HttpEvent<any>>> {

    let authKeys = JSON.parse(sessionStorage.getItem('authKeys') || '{}');
    let token = (Object.keys(authKeys).length > 0)? authKeys.access_token : '';
    const url = `${this.baseUrl}/${endpointUrl}`;
    const req = new HttpRequest('POST', url, data, {
      reportProgress: true,
      /* headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      }), */
    });
    return this._http.request(req);
  }

  public async updateApiService(endpoint: string, id: string, data: any): Promise<Observable<HttpEvent<any>>> {
    let authKeys = JSON.parse(sessionStorage.getItem('authKeys') || '{}');
    let token = (Object.keys(authKeys).length > 0)? authKeys.access_token : '';
    const url = `${this.baseUrl}/${endpoint}/${id}`;
    const req = new HttpRequest('PUT', url, data, {
      reportProgress: true,
      /*headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      }),*/
    });
    return this._http.request(req);
  }

   public async updateApiFormDataService(endpointUrl: string, id: string, data: FormData): Promise<Observable<HttpEvent<any>>> {

    let authKeys = JSON.parse(sessionStorage.getItem('authKeys') || '{}');
    let token = (Object.keys(authKeys).length > 0)? authKeys.access_token : '';
    const url = `${this.baseUrl}/${endpointUrl}/${id}`;
    const req = new HttpRequest('PUT', url, data, {
      reportProgress: true,
      /* headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      }), */
    });
    return this._http.request(req);
  }

  public async deleteApiService(endpoint: string, id: string): Promise<Observable<HttpEvent<any>>> {
    let authKeys = JSON.parse(sessionStorage.getItem('authKeys') || '{}');
    let token = (Object.keys(authKeys).length > 0)? authKeys.access_token : '';
    const url = `${this.baseUrl}/${endpoint}/${id}`;
    let req;
    req = new HttpRequest('DELETE', url, {
      reportProgress: true,
      /*headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      }),*/
    });
    return this._http.request(req);
  }

}
