import { Component, Input } from '@angular/core';
import { ProyectoResponse } from '../../../modelos/proyectos/proyectoResponse';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-proyecto-card',
  imports: [RouterLink],
  templateUrl: './proyecto-card.html',
  styleUrl: './proyecto-card.css',
})
export class ProyectoCard {

  constructor() { }

  @Input({ required: true })
  proyecto !: ProyectoResponse;

  @Input({ required: true })
  mostrarBtnEditar !: boolean;
}
