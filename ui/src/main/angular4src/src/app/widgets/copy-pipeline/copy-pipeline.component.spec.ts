import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CopyPipelineComponent } from './copy-pipeline.component';

describe('CopyPipelineComponent', () => {
  let component: CopyPipelineComponent;
  let fixture: ComponentFixture<CopyPipelineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CopyPipelineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CopyPipelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
