import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { TranslationService } from './modules/i18n';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
// language list
import { locale as enLang } from './modules/i18n/vocabs/en';
import { locale as chLang } from './modules/i18n/vocabs/ch';
import { locale as esLang } from './modules/i18n/vocabs/es';
import { locale as jpLang } from './modules/i18n/vocabs/jp';
import { locale as deLang } from './modules/i18n/vocabs/de';
import { locale as frLang } from './modules/i18n/vocabs/fr';
import { ThemeModeService } from './_metronic/partials/layout/theme-mode-switcher/theme-mode.service';

@Component({
  // tslint:disable-next-line:component-selector
  // eslint-disable-next-line @angular-eslint/component-selector
  selector: 'body[root]',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppComponent implements OnInit {

  private socket$: WebSocketSubject<any>;
  private webSocket: WebSocket;
  myStock: any = {};

  constructor(
    private translationService: TranslationService,
    private modeService: ThemeModeService
  ) {
    /* this.webSocket = new WebSocket('ws://127.0.0.1:8082/ws');
    this.webSocket.onmessage = (event) => {
      this.myStock = JSON.parse(event.data);
      console.log(this.myStock);
    }; */
    this.socket$ = webSocket('ws://localhost:8082/ws');
    // register translations
    this.translationService.loadTranslations(
      enLang,
      chLang,
      esLang,
      jpLang,
      deLang,
      frLang
    );
  }

  public sendMessage(msg: any): void {
    this.socket$.next(msg);
  }

  public close(): void {
    this.socket$.complete();
  }

  public getMessages() {
    return this.socket$.asObservable();
  }

  ngOnInit() {
    this.modeService.init();
  }
}
