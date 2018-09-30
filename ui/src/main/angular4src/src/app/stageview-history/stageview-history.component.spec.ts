/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { StageviewHistoryComponent } from "./stageview-history.component";

describe("StageviewHistoryComponent", () => {
  let component: StageviewHistoryComponent;
  let fixture: ComponentFixture<StageviewHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ StageviewHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StageviewHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit("should create", () => {
    expect(component).toBeTruthy();
  });
});
