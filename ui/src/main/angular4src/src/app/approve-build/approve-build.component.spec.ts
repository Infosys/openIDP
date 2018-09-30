/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { ApproveBuildComponent } from "./approve-build.component";

describe("ApproveBuildComponent", () => {
  let component: ApproveBuildComponent;
  let fixture: ComponentFixture<ApproveBuildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ ApproveBuildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApproveBuildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
