import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApproveReleaseComponent } from './approve-release.component';

describe('ApproveReleaseComponent', () => {
  let component: ApproveReleaseComponent;
  let fixture: ComponentFixture<ApproveReleaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApproveReleaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApproveReleaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
