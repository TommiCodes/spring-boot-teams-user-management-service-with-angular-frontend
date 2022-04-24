import { TeamService } from './../../services/team-service/team.service';
import { User, UserPagedResponse } from './../../../model/user.interfaces';
import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { UserTeamPagedResponse } from 'src/app/model/user-team.interfaces';

@Injectable({
  providedIn: 'root'
})
export class TeamMembersResolver implements Resolve<UserTeamPagedResponse> {

  constructor(private teamsService: TeamService) { }

  resolve(route: ActivatedRouteSnapshot): Observable<UserTeamPagedResponse> {
    const pageSize: number = route.queryParamMap.get('pageSize') === null ? 20 : Number(route.queryParamMap.get('pageSize'));
    const pageNumber: number = route.queryParamMap.get('pageNumber') === null ? 0 : Number(route.queryParamMap.get('pageNumber'));
    const teamId = route.paramMap.get('id');

    return this.teamsService.getTeamMembersByTeamId(Number(teamId), {
      number: pageNumber,
      size: pageSize
    });
  }
}
