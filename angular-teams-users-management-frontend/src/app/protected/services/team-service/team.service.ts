import { TeamsPagedResponse } from './../../../model/interfaces';

import { Observable } from 'rxjs';
import { UserState } from 'src/app/root-states/user.state';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor(
    private http: HttpClient,
    private snackbar: MatSnackBar
  ) { }

  getOwnTeams(id: number): Observable<TeamsPagedResponse> {
    return this.http.get<TeamsPagedResponse>(`/api/users/${id}/teams`);
  }
}
