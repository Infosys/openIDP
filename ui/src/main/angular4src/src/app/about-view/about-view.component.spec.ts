/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutViewComponent } from './about-view.component';

describe('AboutViewComponent', () => {
  let component: AboutViewComponent;
  let fixture: ComponentFixture<AboutViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AboutViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AboutViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
