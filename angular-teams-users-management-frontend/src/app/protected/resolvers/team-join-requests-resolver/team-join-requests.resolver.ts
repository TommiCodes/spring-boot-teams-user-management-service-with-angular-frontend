import { Pageable } from 'src/app/model/interfaces';
import { JoinRequestService } from './../../services/join-request-service/join-request.service';
import { JoinRequestPageResponse } from './../../../model/join-request.interfaces';
import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TeamJoinRequestsResolver implements Resolve<JoinRequestPageResponse> {

  constructor(private joinRequestService: JoinRequestService) { }

  resolve(route: ActivatedRouteSnapshot): Observable<JoinRequestPageResponse> {
    const teamId = route.paramMap.get('id');
    const pageSize: number = route.queryParamMap.get('pageSize') === null ? 20 : Number(route.queryParamMap.get('pageSize'));
    const pageNumber: number = route.queryParamMap.get('pageNumber') === null ? 0 : Number(route.queryParamMap.get('pageNumber'));

    const pageable: Pageable = {
      number: pageNumber,
      size: pageSize
    };

    return this.joinRequestService.getJoinRequestsByTeamId(Number(teamId), pageable).pipe(tap(a => console.log(a)));
  }
}
