// src/app/pages/profile/profile.component.ts
import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ReservationService } from '../../services/reservation.service';
import { UserResponse, ReservationResponse } from '../../models/models';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  private auth = inject(AuthService);
  private reservationService = inject(ReservationService);
  private router = inject(Router);

  activeTab = signal<'profile' | 'reservations'>('profile');
  user = signal<UserResponse | null>(null);
  reservations = signal<ReservationResponse[]>([]);
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void { this.loadProfile(); }

  loadProfile(): void {
    const userId = this.auth.currentUserId();
    if (!userId) return;
    this.loading.set(true);
    this.auth.getUserById(userId).subscribe({
      next: (data) => { this.user.set(data); this.loading.set(false); },
      error: (err: Error) => { this.errorMessage.set(err.message); this.loading.set(false); }
    });
  }

  showReservations(): void {
    this.activeTab.set('reservations');
    const userId = this.auth.currentUserId();
    if (!userId) return;
    this.loading.set(true);
    this.reservationService.getByUserId(userId).subscribe({
      next: (data) => { this.reservations.set(data); this.loading.set(false); },
      error: () => { this.reservations.set([]); this.loading.set(false); }
    });
  }

  cancelReservation(id: number): void {
    if (!confirm('¿Seguro que deseas cancelar esta cita?')) return;
    this.reservationService.delete(id).subscribe({
      next: () => this.showReservations(),
      error: (err: Error) => this.errorMessage.set(err.message)
    });
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  formatDate(dt: string): { dia: number; mes: string; hora: string } {
    const d = new Date(dt);
    return {
      dia: d.getDate(),
      mes: d.toLocaleString('es-ES', { month: 'short' }).toUpperCase().replace('.', ''),
      hora: d.toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' })
    };
  }
}
