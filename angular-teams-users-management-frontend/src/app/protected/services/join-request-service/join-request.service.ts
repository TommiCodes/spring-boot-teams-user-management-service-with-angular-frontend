import { Pageable } from 'src/app/model/interfaces';
import { HttpClient, HttpParams } from '@angular/common/http';
import { NewJoinRequest, JoinRequestPageResponse } from './../../../model/join-request.interfaces';
import { Observable } from 'rxjs';
import { UserState } from 'src/app/root-states/user.state';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JoinRequestService {

  constructor(private userState: UserState, private http: HttpClient) {}

  sendJoinRequest(teamToJoin: number): Observable<NewJoinRequest> {
    const joinRequest: NewJoinRequest = {
      userId: this.userState.id
    };

    return this.http.post<NewJoinRequest>(`/api/teams/${teamToJoin}/send-join-request`, joinRequest);
  }

  getJoinRequestsByTeamId(teamId: number, pageable: Pageable): Observable<JoinRequestPageResponse> {
    let params = new HttpParams();
    params = params.set('page', pageable.number);
    params = params.set('sort', pageable.size);
    // Add projection param, so that Spring will return the projection, so that we can use the user and team object for the reuqest
    params = params.set('projection', 'details');

    return this.http.get<JoinRequestPageResponse>(`/api/teams/${teamId}/join-requests`, {params});
  }

}
