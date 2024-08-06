import { Injectable } from "@angular/core";
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

const MINUTES_UNITL_AUTO_LOGOUT = 15 // in mins
const THRESHOLD_MINUTES_UNITL_AUTO_LOGOUT = 3 // in mins
const CHECK_INTERVAL = 10000 // in ms
const STORE_KEY = 'lastAction';
@Injectable(
    {
        providedIn: 'root'
    }
)
export class AutoLogoutService {
    val: any;
    public getLastAction() {
        return parseInt(localStorage.getItem(STORE_KEY) || '{}');
    }
    public setLastAction(lastAction: number) {
        localStorage.setItem(STORE_KEY, lastAction.toString());
    }

    constructor(private _router: Router,  private _toastrService:ToastrService) {
        this.check();
        this.initListener();
        this.initInterval();
        localStorage.setItem(STORE_KEY, Date.now().toString());
    }

    initListener() {
        document.body.addEventListener('click', () => this.reset());
        document.body.addEventListener('mouseover', () => this.reset());
        document.body.addEventListener('mouseout', () => this.reset());
        document.body.addEventListener('keydown', () => this.reset());
        document.body.addEventListener('keyup', () => this.reset());
        document.body.addEventListener('keypress', () => this.reset());
        window.addEventListener("storage", () => this.storageEvt());
    }

    reset() {

        //console.log('date got by using events', Date.now());
        this.setLastAction(Date.now());
        //console.log('store key', localStorage.getItem(STORE_KEY));

    }

    initInterval() {
        setInterval(() => {
            this.check();
        }, CHECK_INTERVAL);
    }

    check() {
        const url = window.location.href;
        const urlParms = url.split('/');
        //console.log(urlParms);
        
        if(urlParms[3] == 'auth' || urlParms[3] == 'system' ){
            return;
        }

        const now = Date.now();
        const timeleft = this.getLastAction() + MINUTES_UNITL_AUTO_LOGOUT * 60 * 1000;
        const threshold = THRESHOLD_MINUTES_UNITL_AUTO_LOGOUT * 60 * 1000;
        //console.log('threshold', threshold);

        const diff = timeleft - now;
        //console.log('difference', diff);
        const isThreshold = diff <= threshold;
        const isTimeout = diff < 0;

        if (isThreshold) {
            //this._router.navigate(['./login']);
            //console.log('');
            this._toastrService.info("You will soon be logged out of your account, press any key to cancel");
        }

        if (isTimeout) {
            localStorage.clear();
            sessionStorage.clear();
            //console.log('trigger toast');
            this._router.navigate(['/auth']);
        }
    }
    storageEvt() {
        //console.log("storage");
        this.val = localStorage.getItem(STORE_KEY);
    }
}

