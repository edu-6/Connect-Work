import { Component, Input } from '@angular/core';
import { ReporteTopCategoriaAdmin } from '../../../modelos/reportes/topCategoriasActividad';

@Component({
  selector: 'app-top-categorias-actividad-component',
  imports: [],
  templateUrl: './top-categorias-actividad-component.html',
  styleUrl: './top-categorias-actividad-component.css',
})
export class TopCategoriasActividadComponent {
  @Input({ required: true }) datos: ReporteTopCategoriaAdmin[] = [];
}
