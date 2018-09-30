/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";
import { BuildInfoComponent } from "./build-info.component";

describe("BuildInfoComponent", () => {
  let component: BuildInfoComponent;
  let fixture: ComponentFixture<BuildInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ BuildInfoComponent, ParentFormConnectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuildInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit("should be created", () => {
    expect(component).toBeTruthy();
  });

  it("buildSubscriptionNotSubmitCheck()", () => {
    const a = component.buildSubscriptionNotSubmitCheck();
    expect(component.IdpdataService.buildSubscriptionSubmit).toBeTruthy();
    expect(a).toBeFalsy();
  });

  it("buildSubscriptionNotSubmitCheck()", () => {
    const a = component.buildSubscriptionNotSubmitCheck();
    expect(component.IdpdataService.buildSubscriptionSubmit).toBeFalsy();
    expect(a).toBeTruthy();
  });

  it("buildSubscriptionSubmitCheck()", () => {
    const a = component.buildSubscriptionSubmitCheck();
    expect(component.IdpdataService.buildSubscriptionSubmit).toBeTruthy();
    expect(a).toBeTruthy();
  });

  it("buildSubscriptionSubmitCheck()", () => {
    const a = component.buildSubscriptionSubmitCheck();
    expect(component.IdpdataService.buildSubscriptionSubmit).toBeFalsy();
    expect(a).toBeFalsy();
  });


});
