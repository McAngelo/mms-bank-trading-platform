import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface ApplicationObject {
  type ?: typeObject;
  details?: DetailsObject;
  business?: BusinessObject;
  location?: LocationObject;
  statement?: StatementObject;
  videoUpload?: VideoUploadObject;
}

export interface typeObject {
  applicationUniqueCode?: string;
  applicationCategory?: string;
  applicationPhoneNumber?: string;
  applicationType?: string;
  applicationMode?: string;
}

export interface DetailsObject {
  applicationUniqueCode?: string;
  applicationCategory?: string;
  applicationPhoneNumber?: string;
  lastName?:string;
  firstName?:string;
  middleName?:string;
  age?: number;
  gender?:string;
  disabled?:boolean;
  specifyDisability?:string;
  education?:string;
  employment?:string;
  email?:string;
}

export interface BusinessObject {
  applicationUniqueCode?: string;
  organizationName?: string;
  leadAgeRange?: string;
  staffAverageAgeRange?: string;
  age?: number;
  gender?:string;
  organizationalEmail?: string;
  organizationalPhoneNumber?: string;
  isRegistered?: boolean;
  disabled?:boolean;
  specifyDisability?:string;
}

export interface LocationObject {
  applicationUniqueCode?: string;
  region?: string;
  district?: string;
  locality?: string;
  locationType?: string;
}
export interface StatementObject {
  applicationUniqueCode?: string;
  challenge ?: string;
  underlyingCause ?: string;
  peopleExperience ?: string;
  personalExperience?: string;
  solution?: string;
  sector ?: string[];
  specifyOtherSector ?: string;
  solutionLevel ?: string;
  expectation ?: string;
  timeAvailable ?: string;
  participatedBefore ?: boolean;
  applicationMode ?: string;
  informationSource ?: string;
  specifyOtherInformationSource ?: string;
}


export interface VideoUploadObject {
  applicationUniqueCode?: string;
  videoUrl?: string;
  videoName?: string;
  status?: string;
  createdDate?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ApplicationStoreService {

  private _applicationDetails = new BehaviorSubject<ApplicationObject>({});
  private _dataStore: { applicationDetails: ApplicationObject } = { applicationDetails: {} };
  readonly applicationDetails = this._applicationDetails.asObservable();

  constructor() { }

  public readAll(){
    this._applicationDetails.next(Object.assign({}, this._dataStore).applicationDetails);
  }

  public create(applicationDetails: ApplicationObject) {
    this._dataStore.applicationDetails = {};
    this._dataStore.applicationDetails = applicationDetails;
    sessionStorage.setItem('applicationDetails', JSON.stringify(applicationDetails));
    this._applicationDetails.next(Object.assign({}, this._dataStore).applicationDetails);
  }


  public update(key: string, dataObj: any){
    let applicationDetails: ApplicationObject = JSON.parse(sessionStorage.getItem('applicationDetails') || '{}');

    switch (key) {
      case 'type':
        applicationDetails.type = dataObj;
        break;
      case 'details':
        applicationDetails.details = dataObj;
        break;
      case 'business':
        applicationDetails.business = dataObj;
        break;
      case 'location':
        applicationDetails.location = dataObj;
        break;
      case 'statement':
        applicationDetails.statement = dataObj;
        break;
      case 'videoUpload':
        applicationDetails.videoUpload = dataObj;
        break;
      default:
        break;
    }
    this._dataStore.applicationDetails = applicationDetails;
    sessionStorage.setItem('applicationDetails', JSON.stringify(applicationDetails));
    this._applicationDetails.next(Object.assign({}, this._dataStore).applicationDetails);
  }

  public remove(){
    this._dataStore.applicationDetails = {};
    sessionStorage.removeItem('applicationDetails')
    //this._applicationDetails.next(Object.assign({}, this._dataStore).applicationDetails);
  }
}
