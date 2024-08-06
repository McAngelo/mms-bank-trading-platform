import { Injectable } from '@angular/core';
import {Observable  ,  Subject } from 'rxjs';

@Injectable()
export class LoginService {

	logger = new Subject<boolean>();
	loggedIn: boolean = false;

  	constructor() { }

  	isLoggedIn(): Observable<boolean>{
  		return this.logger.asObservable();
  	}

  	verifyLogin(){
  		let authState = sessionStorage.getItem('authenticated');
  		this.logIn(authState||"");
  	}

  	logIn(authState: string){
  		sessionStorage.setItem('authenticated', authState);
  		this.loggedIn = authState === 'yes';

  		this.logger.next(this.loggedIn);
  	}

  	logOut(){
  		sessionStorage.removeItem('authenticated');
  		this.loggedIn = false;
  		this.logger.next(this.loggedIn);
  	}

}
