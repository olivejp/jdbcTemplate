import {Injectable} from '@angular/core';
import {Observable, Observer, Subscription} from 'rxjs';
import {Location} from '@angular/common';
import {AuthServerProvider} from '../auth/auth-jwt.service';

import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';
import {MessageDto} from "app/entities/message.dto";

@Injectable({providedIn: 'root'})
export class JhiMessageService {
  stompClient = null;
  stompSubscriber = null;
  connection: Promise<any>;
  connectedPromise: any;
  listener: Observable<any>;
  listenerObserver: Observer<any>;
  alreadyConnectedOnce = false;
  private subscription: Subscription;

  constructor(
    private authServerProvider: AuthServerProvider,
    private location: Location
  ) {
    this.connection = this.createConnection();
    this.listener = this.createListener();
  }

  connect() {
    if (this.connectedPromise === null) {
      this.connection = this.createConnection();
    }
    // building absolute path so that websocket doesn't fail when deploying with a context path
    let url = '/websocket/chat';
    url = this.location.prepareExternalUrl(url);
    const authToken = this.authServerProvider.getToken();
    if (authToken) {
      url += '?access_token=' + authToken;
    }
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket);
    const headers = {};
    this.stompClient.connect(headers, () => {
      this.connectedPromise('success');
      this.connectedPromise = null;
      this.sendMessage(new MessageDto('Bonjour, je viens de rejoindre le chat.'));
      if (!this.alreadyConnectedOnce) {
        this.alreadyConnectedOnce = true;
      }
    });
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
      this.stompClient = null;
    }
    if (this.subscription) {
      this.subscription.unsubscribe();
      this.subscription = null;
    }
    this.alreadyConnectedOnce = false;
  }

  receive() {
    return this.listener;
  }

  sendMessage(message: MessageDto) {
    if (this.stompClient !== null && this.stompClient.connected) {
      this.stompClient.send(
        '/topic/chat', // destination
        JSON.stringify(message), // body
        {} // header
      );
    }
  }

  subscribe() {
    this.connection.then(() => {
      this.stompSubscriber = this.stompClient.subscribe('/topic/messages', data => {
        this.listenerObserver.next(JSON.parse(data.body));
      });
    });
  }

  unsubscribe() {
    if (this.stompSubscriber !== null) {
      this.stompSubscriber.unsubscribe();
    }
    this.listener = this.createListener();
  }

  private createListener(): Observable<any> {
    return new Observable(observer => {
      this.listenerObserver = observer;
    });
  }

  private createConnection(): Promise<any> {
    return new Promise(resolve => (this.connectedPromise = resolve));
  }
}
