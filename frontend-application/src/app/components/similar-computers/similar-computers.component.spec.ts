import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimilarComputersComponent } from './similar-computers.component';

describe('SimilarComputersComponent', () => {
  let component: SimilarComputersComponent;
  let fixture: ComponentFixture<SimilarComputersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SimilarComputersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimilarComputersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
