import { UserState } from '../../../root-states/user.state';
import { UserService } from '../../services/user-service/user.service';
import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user.interfaces';

@Injectable({
  providedIn: 'root',
})
export class OwnProfileResolver implements Resolve<User> {
  constructor(private userState: UserState, private userService: UserService) {}
  resolve(): Observable<User> {
    return this.userService.getProfileByUserId(this.userState.id);
  }
}
