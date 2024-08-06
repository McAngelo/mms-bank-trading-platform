import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable()
export class AuthenticationService {

  public baseUrl: string = environment._apiEndpoint;
  public headers: any = new HttpHeaders();

  constructor(private _http: HttpClient, private router: Router) { }

  /* Authorize API endpoints */
  public authorize(): Observable<HttpEvent<any>> {
    let url = `${this.baseUrl}/oauth/token`;
    const urlencoded = new URLSearchParams();
    urlencoded.append("grant_type", "client_credentials");
    urlencoded.append("scope", "*");
    const req = new HttpRequest('POST', url, urlencoded, {
      reportProgress: true,
      headers: new HttpHeaders({
        Authorization: `Basic RGlnaXRhbE1vbml0b3I6MEQ0RTFGMTctNDc5Qy00Q0RCLUEyMUMtQjUzRTcxOUIwRDAy`,
        "Content-Type": `application/x-www-form-urlencoded`
      }),
    });
    return this._http.request(req);
  }

}
