import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-comision-card',
  imports: [],
  templateUrl: './comision-card.html',
  styleUrl: './comision-card.css',
})
export class ComisionCard {
  @Input({ required: true }) porcentaje: number = 0;
  @Output() editar = new EventEmitter<void>();
}
