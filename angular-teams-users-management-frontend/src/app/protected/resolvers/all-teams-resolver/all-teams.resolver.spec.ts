import { TestBed } from '@angular/core/testing';

import { AllTeamsResolver } from './all-teams.resolver';

describe('AllTeamsResolver', () => {
  let resolver: AllTeamsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(AllTeamsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
