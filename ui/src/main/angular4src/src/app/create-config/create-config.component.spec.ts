/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
// tslint:disable
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { CreateConfigComponent } from "./create-config.component";

describe("CreateConfigComponent", () => {
  let component: CreateConfigComponent;
  let fixture: ComponentFixture<CreateConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ CreateConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit("should be created", () => {
    expect(component).toBeTruthy();
  });
});
