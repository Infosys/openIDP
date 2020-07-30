import {
  Directive,
  ElementRef,
  Output,
  HostListener,
  EventEmitter,
} from "@angular/core";

@Directive({
  selector: "[appCapsLock]",
})
export class CapsLockDirective {
  constructor(private el: ElementRef) {}
  @Output() change: EventEmitter<Object> = new EventEmitter();

  @HostListener("window:keydown", ["$event"])
  onKeyDown(event: KeyboardEvent): void {
    const capsOn = event.getModifierState && event.getModifierState("CapsLock");
    if (capsOn) {
      this.change.emit(true);
    } else {
      this.change.emit(false);
    }
  }
}
