import { TestBed } from '@angular/core/testing';

import { JoinRequestService } from './join-request.service';

describe('JoinRequestService', () => {
  let service: JoinRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JoinRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
