import { Injectable } from '@angular/core';
import { STORE_NAME } from '../common/en-constants';


@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {
private storage: Storage = sessionStorage;
  private storeKey: string = STORE_NAME;

  constructor() {}

  public getStorage(): any {
    return JSON.parse(this.storage.getItem(this.storeKey)!) ?? null;
  }

  public getStorageValue(name: string): any {
    return this.getStorage()?.[name];
  }

  public setStorage(data: object): void {
    this.storage.setItem(this.storeKey, JSON.stringify(data));
  }

  public setStorageValue(name: string, value: string): void {
    const session = JSON.parse(this.storage.getItem(this.storeKey) || '{}');
    session[name] = value;
    this.storage.setItem(this.storeKey, JSON.stringify(session));
  }

  public deleteStorage(): void {
    this.storage.clear();
  }

  public deleteStorageValue(key: string): void {
    const session = JSON.parse(this.storage.getItem(this.storeKey) ?? '');
    session[key] = undefined;
  }
}
