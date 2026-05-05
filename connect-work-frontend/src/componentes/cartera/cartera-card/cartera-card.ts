import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-cartera-card',
  imports: [],
  templateUrl: './cartera-card.html',
  styleUrl: './cartera-card.css',
})
export class CarteraCard {
  @Input({ required: true }) saldo: number = 0;
  @Output() editar = new EventEmitter<void>();
}
