/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
// tslint:disable

import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { PreviousConfigComponent } from "./previous-config.component";

describe("PreviousConfigComponent", () => {
  let component: PreviousConfigComponent;
  let fixture: ComponentFixture<PreviousConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ PreviousConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviousConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit("should be created", () => {
    expect(component).toBeTruthy();
  });
});
