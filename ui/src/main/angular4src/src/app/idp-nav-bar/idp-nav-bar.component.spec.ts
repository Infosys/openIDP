/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { IdpNavBarComponent } from "./idp-nav-bar.component";

describe("IdpNavBarComponent", () => {
  let component: IdpNavBarComponent;
  let fixture: ComponentFixture<IdpNavBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ IdpNavBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdpNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit("should be created", () => {
    expect(component).toBeTruthy();
  });
});
