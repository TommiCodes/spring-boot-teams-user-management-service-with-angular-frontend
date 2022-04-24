import { TestBed } from '@angular/core/testing';

import { UserIsTeamAdminGuard } from './user-is-team-admin.guard';

describe('UserIsTeamAdminGuard', () => {
  let guard: UserIsTeamAdminGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(UserIsTeamAdminGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
