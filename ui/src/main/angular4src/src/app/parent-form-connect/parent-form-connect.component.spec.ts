/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { ParentFormConnectComponent } from "./parent-form-connect.component";

describe("ParentFormConnectComponent", () => {
  let component: ParentFormConnectComponent;
  let fixture: ComponentFixture<ParentFormConnectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ ParentFormConnectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParentFormConnectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit("should create", () => {
    expect(component).toBeTruthy();
  });
});
