import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthResponse, LoginRequest, UserRequest, UserResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly apiUrl = '/api/users';

  isLoggedIn = signal<boolean>(!!localStorage.getItem('jwt_token'));
  currentUserId = signal<number | null>(
    localStorage.getItem('userId') ? Number(localStorage.getItem('userId')) : null
  );

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request).pipe(
      tap(res => {
        localStorage.setItem('jwt_token', res.token);
        localStorage.setItem('userId', res.userId.toString());
        this.isLoggedIn.set(true);
        this.currentUserId.set(res.userId);
      })
    );
  }

  register(request: UserRequest): Observable<UserResponse> {
    return this.http.post<UserResponse>(this.apiUrl, request);
  }

  getUserById(userId: number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.apiUrl}/${userId}`);
  }

  logout(): void {
    localStorage.clear();
    this.isLoggedIn.set(false);
    this.currentUserId.set(null);
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }
}
