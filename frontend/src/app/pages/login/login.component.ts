// src/app/pages/login/login.component.ts
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);

  submitted = false;
  errorMessage: string | null = null;

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  get f() { return this.form.controls; }

  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = null;
    if (this.form.invalid) return;
    this.auth.login(this.form.value as any).subscribe({
      next: () => this.router.navigate(['/profile']),
      error: (err: Error) => this.errorMessage = err.message
    });
  }
}
