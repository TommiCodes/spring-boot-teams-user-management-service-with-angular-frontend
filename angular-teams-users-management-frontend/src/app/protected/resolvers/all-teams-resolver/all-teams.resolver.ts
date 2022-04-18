import { TeamService } from '../../services/team-service/team.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { TeamsPagedResponse } from 'src/app/model/team.interfaces';

@Injectable({
  providedIn: 'root'
})
export class AllTeamsResolver implements Resolve<TeamsPagedResponse> {

  constructor(
    private teamsService: TeamService
  ) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TeamsPagedResponse> {

    const pageSize: number = route.queryParamMap.get('pageSize') === null ? 20 : Number(route.queryParamMap.get('pageSize'));
    const pageNumber: number = route.queryParamMap.get('pageNumber') === null ? 0 : Number(route.queryParamMap.get('pageNumber'));
    const teamName: string | null = route.queryParamMap.get('name');

    return this.teamsService.getAllTeams({
      number: pageNumber,
      size: pageSize
    }, teamName);
  }
}
