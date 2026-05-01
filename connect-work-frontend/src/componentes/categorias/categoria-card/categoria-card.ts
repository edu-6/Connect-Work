import { Component, Input } from '@angular/core';
import { Categoria } from '../../../modelos/categorias/categoria';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-categoria-card',
  imports: [RouterLink],
  templateUrl: './categoria-card.html',
  styleUrl: './categoria-card.css',
})
export class CategoriaCard {

  @Input({required: true})
  categoria!: Categoria;
}
