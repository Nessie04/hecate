import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservationRequest, ReservationResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class ReservationService {
  private readonly apiUrl = '/api/reservations';

  constructor(private http: HttpClient) {}

  create(userId: number, request: ReservationRequest): Observable<ReservationResponse> {
    return this.http.post<ReservationResponse>(`${this.apiUrl}/${userId}`, request);
  }

  getByUserId(userId: number): Observable<ReservationResponse[]> {
    return this.http.get<ReservationResponse[]>(`${this.apiUrl}/${userId}`);
  }

  delete(reservationId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${reservationId}`);
  }
}
