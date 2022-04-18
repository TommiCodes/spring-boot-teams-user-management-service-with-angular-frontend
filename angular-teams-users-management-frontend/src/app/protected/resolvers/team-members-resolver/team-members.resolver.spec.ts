import { TestBed } from '@angular/core/testing';

import { TeamMembersResolver } from './team-members.resolver';

describe('TeamMembersResolver', () => {
  let resolver: TeamMembersResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(TeamMembersResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
