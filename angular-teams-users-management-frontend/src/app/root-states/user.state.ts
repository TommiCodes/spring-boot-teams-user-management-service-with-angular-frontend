import { Injectable } from '@angular/core';
import { StateRepository } from '@angular-ru/ngxs/decorators';
import { NgxsDataRepository } from '@angular-ru/ngxs/repositories';
import { User } from '../model/interfaces';
import { State } from '@ngxs/store';

export interface UserAuth extends User {
  sub: string;
  auth: string[];
  exp: number;
  iat: number;
}

@StateRepository()
@State({
  name: 'user',
  defaults: {},
})
@Injectable()
export class UserState extends NgxsDataRepository<UserAuth> {}
