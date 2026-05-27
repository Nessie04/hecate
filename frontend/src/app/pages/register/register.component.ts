// src/app/pages/register/register.component.ts
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

function passwordsMatch(control: AbstractControl) {
  const p = control.get('password')?.value;
  const c = control.get('passwordConfirmed')?.value;
  return p === c ? null : { mismatch: true };
}

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);

  submitted = false;
  errorMessage: string | null = null;

  form = this.fb.group({
    fullName: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    phone: [''],
    password: ['', [Validators.required, Validators.minLength(6)]],
    passwordConfirmed: ['', Validators.required],
    profilePicture: ['']
  }, { validators: passwordsMatch });

  get f() { return this.form.controls; }

  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = null;
    if (this.form.invalid) return;
    const { passwordConfirmed, ...userData } = this.form.value as any;
    this.auth.register(userData).subscribe({
      next: () => this.router.navigate(['/login']),
      error: (err: Error) => this.errorMessage = err.message
    });
  }
}
