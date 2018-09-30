import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ActiveReleaseComponent } from "./active-release.component";

describe("ActiveReleaseComponent", () => {
  let component: ActiveReleaseComponent;
  let fixture: ComponentFixture<ActiveReleaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ ActiveReleaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiveReleaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should be created", () => {
    expect(component).toBeTruthy();
  });
});
