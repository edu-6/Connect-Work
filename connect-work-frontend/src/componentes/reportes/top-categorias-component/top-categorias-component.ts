import { Component, Input } from '@angular/core';
import { ReporteTopCategoria } from '../../../modelos/reportes/reporetTopCategoria';

@Component({
  selector: 'app-top-categorias-component',
  imports: [],
  templateUrl: './top-categorias-component.html',
  styleUrl: './top-categorias-component.css',
})
export class TopCategoriasComponent {
  @Input({ required: true }) datos: ReporteTopCategoria[] = [];
}
