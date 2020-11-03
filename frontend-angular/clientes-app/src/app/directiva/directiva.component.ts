import { Component } from '@angular/core';

@Component({
  selector: 'app-directiva',
  templateUrl: './directiva.component.html'
})
export class DirectivaComponent {
  listaCurso: string[] = ['typescript', 'javascript', 'java'];
  habilitar: boolean = true;

  setHabilitar(): void {
    this.habilitar = !this.habilitar;
  }
}
