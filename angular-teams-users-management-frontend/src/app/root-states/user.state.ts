import { Injectable } from '@angular/core';
import { Computed, StateRepository } from '@angular-ru/ngxs/decorators';
import { NgxsDataRepository } from '@angular-ru/ngxs/repositories';
import { State } from '@ngxs/store';
import { UserAuth } from '../model/interfaces';

@StateRepository()
@State({
  name: 'user',
  defaults: {},
})
@Injectable()
export class UserState extends NgxsDataRepository<UserAuth> {

  // Sub (Subject) of the jwt is the id of the user
  @Computed()
  public get id(): number {
    return Number(this.snapshot.sub);
  }

  @Computed()
  public get teamIds(): number[] {
    return this.snapshot.auth.map(auth => auth.teamId);
  }

}
