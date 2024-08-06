import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpEventType, HttpEvent } from "@angular/common/http";
import { Subscription, Observable } from 'rxjs';
import { Router } from '@angular/router';
//import { AuthService } from '../../services/auth.service';
import { userRegistrationModel, AuthService, LoginRequest, ApiClientService, NotificationService } from 'src/app/shared';
import { ConfirmPasswordValidator } from './confirm-password.validator';
import { UserModel } from '../../models/user.model';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss'],
})
export class RegistrationComponent implements OnInit, OnDestroy {
  registrationForm: FormGroup;
  isLoading$: Observable<boolean>;
  public processing: boolean = false;
  public hasError: boolean = false;
  public errorMsg: string = "";

  // private fields
  private unsubscribe: Subscription[] = []; // Read more: => https://brianflove.com/2016/12/11/anguar-2-unsubscribe-observables/

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private _apiClientService: ApiClientService,
    private _notificationService: NotificationService
  ) {
    /* this.isLoading$ = this.authService.isLoading$;
    // redirect to home if already logged in
    if (this.authService.currentUserValue) {
      this.router.navigate(['/']);
    } */
  }

  ngOnInit(): void {
    this.initForm();
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registrationForm.controls;
  }

  initForm() {
    this.registrationForm = this.fb.group(
      {
        fullname: [
          '',
          Validators.compose([
            Validators.required,
            Validators.minLength(3),
            Validators.maxLength(100),
          ]),
        ],
        email: [
          '',
          Validators.compose([
            Validators.required,
            Validators.email,
            Validators.minLength(3),
            Validators.maxLength(320), // https://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address
          ]),
        ],
        password: [
          '',
          Validators.compose([
            Validators.required,
            Validators.minLength(3),
            Validators.maxLength(100),
          ]),
        ],
        cPassword: [
          '',
          Validators.compose([
            Validators.required,
            Validators.minLength(3),
            Validators.maxLength(100),
          ]),
        ],
        agree: [false, Validators.compose([Validators.required])],
      },
      {
        validator: ConfirmPasswordValidator.MatchPassword,
      }
    );
  }

 /*  submit() {
    this.hasError = false;
    const result: {
      [key: string]: string;
    } = {};
    Object.keys(this.f).forEach((key) => {
      result[key] = this.f[key].value;
    });
    const newUser = new UserModel();
    newUser.setUser(result);
    const registrationSubscr = this.authService
      .registration(newUser)
      .pipe(first())
      .subscribe((user: UserModel) => {
        if (user) {
          this.router.navigate(['/']);
        } else {
          this.hasError = true;
        }
      });
    this.unsubscribe.push(registrationSubscr);
  } */

  onSubmit(){

    this.hasError = false;
    const result: {
      [key: string]: string;
    } = {};
    Object.keys(this.f).forEach((key) => {
      result[key] = this.f[key].value;
    });

    const newUser = new UserModel();
    newUser.setUser(result);

    console.log(result);
    const checkPass:boolean = (result.password === result.cPassword)? true:false;

    const userData:userRegistrationModel = {};

    if(checkPass && result.agree){
      userData.fullName = result.fullname;
      userData.email = result.email;
      userData.password = result.password;
      this.createUser(userData);
    }

  }

  public async createUser( userData:userRegistrationModel){
    this.processing = true;
    const loginReq:LoginRequest = {
      email: userData.email,
      password: userData.password
    };
    (await this._apiClientService.addApiService(`auth/register`, userData)).subscribe(
      (event: HttpEvent<any>): any => {
        switch (event.type) {
          case HttpEventType.Sent:
            this.processing = true;
            break;
          case HttpEventType.Response:
            this.processing = false;
            let {status, data, message, error} = event.body;
            console.log({status, data, message, error});
            if(status == 200){
              //this.triggerModal('close');
              this.authService.login(loginReq);
              this._notificationService.showSuccess('Successful registration', 'Success');
              //this.getAllCompanies();
            }else{
              this.hasError = true;
              this.errorMsg = 'something went wrong please contact Technical Support';
            }
        }
      },
      (errorObj:any) => {
        this.processing = false;
        const errorMsg = errorObj?.error?.Message;
        this.hasError = true;
        this.errorMsg = errorMsg || 'something went wrong please contact Technical Support';
      }
    );

  }

 /*  public async updateCompany(): Promise<void> {
    this.model.status = parseInt(this.model.status);
    this.processing = true;
    (await this._apiClientService.updateApiService(`v1/companies`, this.model.companyID, this.model)).subscribe(
      (event: HttpEvent<any>): any => {
        switch (event.type) {
          case HttpEventType.Sent:
            this.processing = true;
            break;
          case HttpEventType.Response:
            this.processing = false;
            let {status, data, message, error} = event.body;
            //console.log({status, data, message, error});
            if(status == 200){
              this.triggerModal('close');
              this._notificationService.showSuccess('Company processed successfully', 'Success');
              //this.getAllCompanies();
            }else{
              this.hasError = true;
              this.errorMsg = 'something went wrong please contact Technical Support';
            }
        }
      },
      (errorObj:any) => {
        this.processing = false;
        const errorMsg = errorObj?.error?.Message;
        this.hasError = true;
        this.errorMsg = errorMsg || 'something went wrong please contact Technical Support';
      }
    );
  } */

  ngOnDestroy() {
    this.unsubscribe.forEach((sb) => sb.unsubscribe());
  }
}
