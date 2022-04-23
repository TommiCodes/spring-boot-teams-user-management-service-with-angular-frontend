import { TestBed } from '@angular/core/testing';

import { TeamJoinRequestsResolver } from './team-join-requests.resolver';

describe('TeamJoinRequestsResolver', () => {
  let resolver: TeamJoinRequestsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(TeamJoinRequestsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
