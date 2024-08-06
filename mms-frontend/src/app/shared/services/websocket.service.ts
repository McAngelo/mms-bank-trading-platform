import { Injectable } from '@angular/core';
//import io from 'socket.io-client';
import { Socket } from 'ngx-socket-io';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebsocketService {
  socket: any;
  readonly url: string = 'http://localhost:8082/queue/market-data';

  /* constructor() { 
    this.socket = io(this.url);
  }

  listen(eventName: string){
    return new Observable((subscriber)=> {
      this.socket.on(eventName, (data:any) => {
        subscriber.next(data);
      })
    })
  }

  emit(eventName: string, data: any){
    this.socket.emit(eventName, data);
  } */

  private webSocket: Socket;
  constructor() {
    this.webSocket = new Socket({
      url: 'http://localhost:8082/queue/market-data',
      options: {},
    });
  }

  // this method is used to start connection/handhshake of socket with server
  connectSocket(message: any) {
    this.webSocket.emit('connect', message);
  }

  // this method is used to get response from server
  receiveStatus() {
    return this.webSocket.fromEvent('/get-response');
  }

  // this method is used to end web socket connection
  disconnectSocket() {
    this.webSocket.disconnect();
  }
}
