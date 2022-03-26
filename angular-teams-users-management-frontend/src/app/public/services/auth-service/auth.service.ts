import { UserState } from './../../../root-states/user.state';
import { LOCALSTORAGE_TOKEN_KEY } from './../../../app.module';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material/snack-bar';
import {
  AuthenticationResponse,
  LoginRequest,
  RegisterRequest,
} from '../../model/interfaces';
import { snackBarConf } from 'src/app/model/consts';
import { User } from 'src/app/model/user.interfaces';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private jwtService: JwtHelperService,
    private userState: UserState
  ) { }

  login(loginRequest: LoginRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>('/api/auth/login', loginRequest).pipe(
      tap((res: AuthenticationResponse) => localStorage.setItem(LOCALSTORAGE_TOKEN_KEY, res.accessToken)),
      tap((res: AuthenticationResponse) => this.userState.setState(this.jwtService.decodeToken(res.accessToken))),
      tap(() => this.snackbar.open('Login Successfull', 'Close', snackBarConf))
    );
  }

  register(registerRequest: RegisterRequest): Observable<User> {
    return this.http.post<User>('/api/users', registerRequest).pipe(
      tap(() => this.snackbar.open(`User created successfully`, 'Close', snackBarConf))
    );
  }

  getLoggedInUser() {
    const decodedToken = this.jwtService.decodeToken();
    return decodedToken.user;
  }
}
