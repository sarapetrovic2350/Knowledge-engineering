import { TestBed } from '@angular/core/testing';

import { CauseService } from './cause.service';

describe('CauseServiceService', () => {
  let service: CauseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CauseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
