import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserState } from 'src/app/root-states/user.state';
import { User } from 'src/app/model/interfaces';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private userState: UserState
  ) {}

  getOwnProfile(): Observable<User> {
    return this.http.get<User>(`/api/users/${this.userState.id}`);
  }
}
