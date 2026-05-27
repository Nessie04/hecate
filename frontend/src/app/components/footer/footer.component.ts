// src/app/components/footer/footer.component.ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  standalone: true,
  template: `
    <footer class="forest-footer">
      <div class="container">
        <p>&copy; 2026 La Guarida de Hécate • Hecho con raíces y estrellas</p>
        <div class="social-icons">𓇗 ☾ 𓋹</div>
      </div>
    </footer>
  `
})
export class FooterComponent {}
