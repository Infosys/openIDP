/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
/* tslint:disable */
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { ReleaseHistoryComponent } from "./release-history.component";

describe("ReleaseHistoryComponent", () => {
  let component: ReleaseHistoryComponent;
  let fixture: ComponentFixture<ReleaseHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ ReleaseHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleaseHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit("should be created", () => {
    expect(component).toBeTruthy();
  });
});
