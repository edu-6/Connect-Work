import { Component, Input } from '@angular/core';
import { Habilidad } from '../../../modelos/habilidades/habilidad';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-habilidad-card',
  imports: [RouterLink],
  templateUrl: './habilidad-card.html',
  styleUrl: './habilidad-card.css',
})
export class HabilidadCard {

 @Input({required: true})
  habilidad !: Habilidad;

}
