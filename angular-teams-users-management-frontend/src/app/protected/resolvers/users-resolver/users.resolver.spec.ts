import { TestBed } from '@angular/core/testing';

import { UsersResolver } from './users.resolver';

describe('UsersResolverResolver', () => {
  let resolver: UsersResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(UsersResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
