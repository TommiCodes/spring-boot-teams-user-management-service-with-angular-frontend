import { MatSnackBar } from '@angular/material/snack-bar';
import { Pageable } from 'src/app/models/interfaces';
import { HttpClient, HttpParams } from '@angular/common/http';
import { NewJoinRequest, JoinRequestPageResponse, UpdateJoinTeamRequest, JoinRequest } from '../../../models/join-request.interfaces';
import {catchError, Observable, of, tap} from 'rxjs';
import { UserState } from 'src/app/root-states/user.state';
import { Injectable } from '@angular/core';
import { snackBarConf } from 'src/app/models/consts';

@Injectable({
  providedIn: 'root'
})
export class JoinRequestService {

  constructor(private userState: UserState, private http: HttpClient, private snack: MatSnackBar) { }

  sendJoinRequest(teamToJoin: number): Observable<NewJoinRequest> {
    const joinRequest: NewJoinRequest = {
      userId: this.userState.id
    };

    return this.http.post<NewJoinRequest>(`/api/teams/${teamToJoin}/send-join-request`, joinRequest).pipe(
      tap(() => this.snack.open('Join Request sent', 'Close', snackBarConf)),
      catchError((error) => {
        if (error.status === 409) {
          this.snack.open('Open Request to this team already exists', 'Close', snackBarConf);
        }
        return of(error);
      })
    );
  }

  /** The Admin can either accept or decline a join-request for a team **/
  handleJoinRequest(joinRequest: JoinRequest, updateRequest: UpdateJoinTeamRequest): Observable<string> {
    return this.http.put<string>(`/api/join-requests/${joinRequest.id}`, updateRequest);
  }

  checkIfUserHasOpenJoinRequest(teamId: number): Observable<boolean> {
    return this.http.get<boolean>(`/api/teams/${teamId}/join-requests/check-request-open`);
  }

  getJoinRequestsByTeamId(teamId: number, pageable: Pageable): Observable<JoinRequestPageResponse> {
    let params = new HttpParams();
    params = params.set('page', pageable.number);
    params = params.set('sort', pageable.size);
    // Add projection param, so that Spring will return the projection, so that we can use the user and team object for the reuqest
    params = params.set('projection', 'details');

    return this.http.get<JoinRequestPageResponse>(`/api/teams/${teamId}/join-requests`, { params });
  }

}
