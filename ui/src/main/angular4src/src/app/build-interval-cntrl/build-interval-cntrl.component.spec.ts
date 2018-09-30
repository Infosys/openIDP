import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { BuildIntervalCntrlComponent } from "./build-interval-cntrl.component";

describe("BuildIntervalCntrlComponent", () => {
  let component: BuildIntervalCntrlComponent;
  let fixture: ComponentFixture<BuildIntervalCntrlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ BuildIntervalCntrlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuildIntervalCntrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
