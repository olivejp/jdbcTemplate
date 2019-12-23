export class MessageDto {

  from: string;
  text: string;
  time: string;

  constructor(text: string) {
    this.text = text;
  }
}
