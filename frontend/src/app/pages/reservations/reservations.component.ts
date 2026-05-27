// src/app/pages/reservations/reservations.component.ts
import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ReservationService } from '../../services/reservation.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reservations.component.html'
})
export class ReservationsComponent {
  private fb = inject(FormBuilder);
  private reservationService = inject(ReservationService);
  private auth = inject(AuthService);
  private router = inject(Router);

  submitted = false;
  errorMessage = signal<string | null>(null);
  successMessage = signal<string | null>(null);

  form = this.fb.group({
    servicio: ['mesa', Validators.required],
    fecha: ['', Validators.required],
    hora: ['', Validators.required]
  });

  get f() { return this.form.controls; }
  get minDate(): string { return new Date().toISOString().split('T')[0]; }

  onSubmit(): void {
    this.submitted = true;
    this.errorMessage.set(null);
    if (this.form.invalid) return;

    const { servicio, fecha, hora } = this.form.value;
    if (new Date(`${fecha}T${hora}`) < new Date()) {
      this.errorMessage.set('No puedes viajar al pasado. Selecciona una fecha y hora futura.');
      return;
    }

    const nombres: Record<string, string> = {
      mesa: 'Solo Mesa (Cafetería)',
      tarot: 'Lectura de Tarot',
      posos: 'Lectura de Posos de Té'
    };

    const userId = this.auth.currentUserId()!;
    this.reservationService.create(userId, {
      serviceName: nombres[servicio!],
      dateTime: `${fecha}T${hora}:00`
    }).subscribe({
      next: () => {
        this.successMessage.set('El pacto ha sido sellado. 🌙');
        setTimeout(() => this.router.navigate(['/profile']), 1500);
      },
      error: (err: Error) => this.errorMessage.set(err.message)
    });
  }
}
