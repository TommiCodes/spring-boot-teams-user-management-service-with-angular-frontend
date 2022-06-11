import { TeamService } from '../../services/team-service/team.service';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot} from '@angular/router';
import { Observable } from 'rxjs';
import { UserTeamRelationPagedResponse } from 'src/app/models/user-team-relation.interfaces';

@Injectable({
  providedIn: 'root'
})
export class TeamMembersResolver implements Resolve<UserTeamRelationPagedResponse> {

  constructor(private teamsService: TeamService) { }

  resolve(route: ActivatedRouteSnapshot): Observable<UserTeamRelationPagedResponse> {
    const pageSize: number = route.queryParamMap.get('pageSize') === null ? 20 : Number(route.queryParamMap.get('pageSize'));
    const pageNumber: number = route.queryParamMap.get('pageNumber') === null ? 0 : Number(route.queryParamMap.get('pageNumber'));
    const teamId = route.paramMap.get('id');

    return this.teamsService.getTeamMembersByTeamId(Number(teamId), {
      number: pageNumber,
      size: pageSize
    });
  }
}
