import {Directive, Input, Output, EventEmitter} from "@angular/core";
import { IDPEncryption } from "./idpencryption.service";
@Directive({
selector: "[encrypt]",
host: {
"(blur)": "onblurEvent($event)",
"(focus)": "onFocusEvent($event)"
    }
})
export class EncryptionDirective {
  constructor(
    private idpencryption: IDPEncryption
  ) {}
@Output() ngModelChange: EventEmitter<any> = new EventEmitter();
value: any;

onblurEvent($event) {
  if ($event.target.value !== "") {
  $event.target.value = this.idpencryption.encryptAES($event.target.value);
  this.value = $event.target.value;
  this.ngModelChange.emit(this.value); }
    }

onFocusEvent($event) {
        $event.target.value = "";
        this.value = $event.target.value;
        this.ngModelChange.emit(this.value);
    }
}
