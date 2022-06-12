import { Pageable } from 'src/app/models/interfaces';
import { snackBarConf } from 'src/app/models/consts';
import { UserState } from './../../../root-states/user.state';
import { Observable, tap } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User, UserPagedResponse } from 'src/app/models/user.interfaces';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  constructor(
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private userState: UserState
  ) { }

  getProfileByUserId(id: number): Observable<User> {
    return this.http.get<User>(`/api/users/${id}`);
  }

  updateOwnProfile(updateUser: User): Observable<User> {
    return this.http.put<User>(`/api/users/${this.userState.id}`, updateUser).pipe(
      tap(() => this.snackbar.open('Update Profile Successfull', 'Close', snackBarConf))
    );
  }

  findAllUsers(pageable: Pageable, usernameSearchString?: string | null): Observable<UserPagedResponse> {
    let params = new HttpParams();

    params = params.set('page', pageable.number);
    params = params.set('size', pageable.size);

    if (usernameSearchString !== null && usernameSearchString !== undefined) {
      params = params.set('username', usernameSearchString);
    }

    return this.http.get<UserPagedResponse>(`/api/users`, {params});
  }

}
