import { TeamService } from '../../services/team-service/team.service';
import { Injectable } from '@angular/core';
import {
  Resolve,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable } from 'rxjs';
import { Team } from 'src/app/models/team.interfaces';

@Injectable({
  providedIn: 'root'
})
export class TeamResolver implements Resolve<Team> {

  constructor(
    private teamsService: TeamService
  ) { }

  resolve(route: ActivatedRouteSnapshot): Observable<Team> {
    const teamId = route.paramMap.get('id');

    return this.teamsService.getTeamById(Number(teamId));
  }
}
