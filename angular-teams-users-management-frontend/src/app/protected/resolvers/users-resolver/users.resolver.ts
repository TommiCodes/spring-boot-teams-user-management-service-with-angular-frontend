import { UserService } from './../../services/user-service/user.service';
import { UserPagedResponse } from 'src/app/model/user.interfaces';
import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersResolver implements Resolve<UserPagedResponse> {

  constructor(private userService: UserService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserPagedResponse> {
    const pageSize: number = route.queryParamMap.get('pageSize') === null ? 20 : Number(route.queryParamMap.get('pageSize'));
    const pageNumber: number = route.queryParamMap.get('pageNumber') === null ? 0 : Number(route.queryParamMap.get('pageNumber'));
    const username: string | null = route.queryParamMap.get('username');

    return this.userService.findAllUsers({
      number: pageNumber,
      size: pageSize
    }, username);
  }

}
