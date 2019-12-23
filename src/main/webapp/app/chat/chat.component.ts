import {Component, OnInit, OnDestroy} from '@angular/core';
import {JhiMessageService} from "app/core/tracker/message.service";
import {MessageDto} from "app/entities/message.dto";
import {FormBuilder, Validators} from '@angular/forms';

@Component({
  selector: 'jhi-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, OnDestroy {
  get messages(): MessageDto[] {
    return this._messages;
  }

  set messages(value: MessageDto[]) {
    this._messages = value;
  }

  private _messages: MessageDto[] = [];

  messageForm = this.fb.group({
    newMessage: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*$')]],
  });

  constructor(private messageService: JhiMessageService,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.messageService.subscribe();
    this.messageService.receive().subscribe(message => {
      this._messages.push(message);
    })
  }

  sendMessage() {
    const newMessage = this.messageForm.get(['newMessage']).value;
    this.messageService.sendMessage(new MessageDto(newMessage));
  }

  ngOnDestroy() {
    this.messageService.unsubscribe();
  }
}
