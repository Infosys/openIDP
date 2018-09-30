/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { BasicInfoComponent } from "./basic-info.component";

describe("BasicInfoComponent", () => {
  let component: BasicInfoComponent;
  let fixture: ComponentFixture<BasicInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ BasicInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BasicInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should be created", () => {
    expect(component).toBeTruthy();
  });

  it("clearBuildInterval()", () => {
    const a = component.clearBuildInterval();
    expect(component.basicInfo.buildInterval.buildInterval).toBe("");
    expect(component.basicInfo.buildInterval.buildIntervalValue).toBe("");
    expect(component.basicInfo.buildInterval.pollSCM).toBe("off");
    expect(a).toBe("off");
  });
});

