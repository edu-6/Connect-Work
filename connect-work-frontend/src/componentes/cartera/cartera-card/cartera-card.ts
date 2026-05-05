import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';

@Component({
  selector: 'app-cartera-card',
  imports: [],
  templateUrl: './cartera-card.html',
  styleUrl: './cartera-card.css',
})
export class CarteraCard {
  constructor(private autenticacionService: AutenticacionServicio){

  }
  @Input({ required: true }) saldo: number = 0;
  @Output() editar = new EventEmitter<void>();


  public esCliente(){
    return this.autenticacionService.esCliente();
  }
}
