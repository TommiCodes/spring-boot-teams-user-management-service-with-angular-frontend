import { TestBed } from '@angular/core/testing';

import { TeamResolver } from './team.resolver';

describe('TeamResolver', () => {
  let resolver: TeamResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(TeamResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
