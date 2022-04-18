import { TestBed } from '@angular/core/testing';

import { OwnTeamsResolver } from './own-teams.resolver';

describe('OwnTeamsResolver', () => {
  let resolver: OwnTeamsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(OwnTeamsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
