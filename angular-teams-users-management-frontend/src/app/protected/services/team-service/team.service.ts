import { User } from 'src/app/model/user.interfaces';
import { Team } from './../../../model/team.interfaces';
import { Pageable } from './../../../model/interfaces';
import { Observable } from 'rxjs';
import { UserState } from 'src/app/root-states/user.state';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TeamsPagedResponse } from 'src/app/model/team.interfaces';
import { UserTeamPagedResponse } from 'src/app/model/user-team.interfaces';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor(
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private userState: UserState
  ) { }

  getOwnTeams(): Observable<TeamsPagedResponse> {
    return this.http.get<TeamsPagedResponse>(`/api/users/${this.userState.id}/teams`);
  }

  getTeamsForUserId(id: number): Observable<TeamsPagedResponse> {
    return this.http.get<TeamsPagedResponse>(`/api/users/${id}/teams`);
  }

  getTeamById(id: number): Observable<Team>{
    return this.http.get<Team>(`/api/teams/${id}`);
  }

  removeUserFromTeam(team: Team, user: User): Observable<string> {
    return this.http.post<string>(`/api/teams/${team.id}/users/${user.id}/leave`, {});
  }

  getTeamMembersByTeamId(id: number, pageable: Pageable): Observable<UserTeamPagedResponse> {
    let params = new HttpParams();
    params = params.set('page', pageable.number);
    params = params.set('sort', pageable.size);
    
    params = params.set('projection', 'users');

    return this.http.get<UserTeamPagedResponse>(`/api/teams/${id}/users`, {params});
  }

  getAllTeams(pageable: Pageable, teamNameSearchString?: string | null): Observable<TeamsPagedResponse> {
    let params = new HttpParams();

    params = params.set('page', pageable.number);
    params = params.set('sort', pageable.size);

    if (teamNameSearchString !== null && teamNameSearchString !== undefined) {
      params = params.set('name', teamNameSearchString);
    }

    return this.http.get<TeamsPagedResponse>(`/api/teams`, {params});
  }
}
