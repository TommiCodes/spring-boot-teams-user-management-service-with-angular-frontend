import { Page, Pageable } from './../../../model/interfaces';
import { Observable } from 'rxjs';
import { UserState } from 'src/app/root-states/user.state';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TeamsPagedResponse } from 'src/app/model/team.interfaces';

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

  getAllTeams(pageable: Pageable, teamNameSearchString?: string | null): Observable<TeamsPagedResponse> {
    let params = new HttpParams();

    params = params.set('page', pageable.number)
    params = params.set('sort', pageable.size);

    if (teamNameSearchString !== null && teamNameSearchString !== undefined) {
      params = params.set('name', teamNameSearchString);
    }

    return this.http.get<TeamsPagedResponse>(`/api/teams`, {params});
  }
}
