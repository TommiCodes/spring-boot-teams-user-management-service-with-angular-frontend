import { User } from 'src/app/models/user.interfaces';
import { Team } from '../../../models/team.interfaces';
import { Pageable, Role } from '../../../models/interfaces';
import {catchError, Observable, of} from 'rxjs';
import { UserState } from 'src/app/root-states/user.state';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TeamsPagedResponse } from 'src/app/models/team.interfaces';
import { UserTeamRelation, UserTeamRelationPagedResponse } from 'src/app/models/user-team-relation.interfaces';
import {snackBarConf} from "../../../models/consts";

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
    return this.http.post<string>(`/api/teams/${team.id}/users/${user.id}/leave`, {}).pipe(
      catchError((error) => {
        if (error.status === 403) {
          this.snackbar.open(`User is last Admin in Team. Can't be removed. Please contact an Admin (This feature is not implemented atm)`, 'Close', snackBarConf);
        }
        return of(error);
      })
    );
  }

  updateRoleOfUser(role: Role, team: Team, user: User): Observable<UserTeamRelation> {
    return this.http.put<UserTeamRelation>(`/api/teams/${team.id}/users/${user.id}/update-role`, {role: role}).pipe(
      catchError((error) => {
        if (error.status === 403) {
          this.snackbar.open(`User is last Admin in Team. Can't change Role. Please contact an Admin (This feature is not implemented atm)`, 'Close', snackBarConf);
        }
        return of(error);
      })
    );
  }

  getTeamMembersByTeamId(id: number, pageable: Pageable): Observable<UserTeamRelationPagedResponse> {
    let params = new HttpParams();
    params = params.set('page', pageable.number);
    params = params.set('sort', pageable.size);

    params = params.set('projection', 'users');

    return this.http.get<UserTeamRelationPagedResponse>(`/api/teams/${id}/users`, {params});
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
