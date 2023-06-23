import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchRamComponent } from './search-ram.component';

describe('SearchRamComponent', () => {
  let component: SearchRamComponent;
  let fixture: ComponentFixture<SearchRamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchRamComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchRamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
