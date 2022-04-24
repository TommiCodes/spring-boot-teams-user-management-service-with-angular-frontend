import { TestBed } from '@angular/core/testing';

import { UserIsTeamMemberGuard } from './user-is-team-member.guard';

describe('UserIsTeamMemberGuard', () => {
  let guard: UserIsTeamMemberGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(UserIsTeamMemberGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
