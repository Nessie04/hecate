// src/app/pages/contact/contact.component.ts
import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './contact.component.html'
})
export class ContactComponent {
  private fb = inject(FormBuilder);

  form = this.fb.group({
    nombre: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    mensaje: ['', Validators.required]
  });

  onSubmit(): void {
    if (this.form.valid) alert('Mensaje enviado al viento. 🌙');
  }
}
