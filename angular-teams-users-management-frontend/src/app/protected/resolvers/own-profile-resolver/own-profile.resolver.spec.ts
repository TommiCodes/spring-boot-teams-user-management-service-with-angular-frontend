import { TestBed } from '@angular/core/testing';

import { OwnProfileResolver } from './own-profile.resolver';

describe('OwnProfileResolver', () => {
  let resolver: OwnProfileResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(OwnProfileResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
