import { Observable } from 'rxjs';
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
    private snackbar: MatSnackBar
  ) { }

  getProfileByUserId(id: number): Observable<User> {
    return this.http.get<User>(`/api/users/${id}`);
  }

  updateProfile(user: User): Observable<User> {
    return this.http.put<User>(`/api/users/${user.id}`, user);
  }

}
