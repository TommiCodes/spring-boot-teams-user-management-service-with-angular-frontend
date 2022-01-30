import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface RefreshToken {
  id: number;
  userId: number;
  token: string;
  refreshCount: number;
  expiryDate: Date;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: RefreshToken;
  tokenType: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  API_BASE_PATH = 'http://localhost:8080/auth';

  constructor(
    private http: HttpClient
  ) { }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.API_BASE_PATH}/login`, loginRequest);
  }
}
