import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TriggerPipelineComponent } from './trigger-pipeline.component';

describe('TriggerPipelineComponent', () => {
  let component: TriggerPipelineComponent;
  let fixture: ComponentFixture<TriggerPipelineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TriggerPipelineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TriggerPipelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
