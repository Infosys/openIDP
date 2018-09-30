/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { MailSuccessComponent } from "./mail-success.component";

describe("MailSuccessComponent", () => {
  let component: MailSuccessComponent;
  let fixture: ComponentFixture<MailSuccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ MailSuccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MailSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit("should create", () => {
    expect(component).toBeTruthy();
  });
});
