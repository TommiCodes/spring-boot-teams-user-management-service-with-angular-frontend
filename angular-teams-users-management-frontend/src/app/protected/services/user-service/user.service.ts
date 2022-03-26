import { snackBarConf } from 'src/app/model/consts';
import { UserState } from './../../../root-states/user.state';
import { Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from 'src/app/model/user.interfaces';

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

}
