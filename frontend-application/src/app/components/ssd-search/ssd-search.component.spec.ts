import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SsdSearchComponent } from './ssd-search.component';

describe('SsdSearchComponent', () => {
  let component: SsdSearchComponent;
  let fixture: ComponentFixture<SsdSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SsdSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SsdSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
