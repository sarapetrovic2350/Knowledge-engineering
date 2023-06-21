import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SymptomsCausesComponent } from './symptoms-causes.component';

describe('SymptomsCausesComponent', () => {
  let component: SymptomsCausesComponent;
  let fixture: ComponentFixture<SymptomsCausesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SymptomsCausesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SymptomsCausesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
