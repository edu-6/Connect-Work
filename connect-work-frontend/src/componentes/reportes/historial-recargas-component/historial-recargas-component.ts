import { Component, Input } from '@angular/core';
import { ReporteRecarga } from '../../../modelos/reportes/reporteRecargas';

@Component({
  selector: 'app-historial-recargas-component',
  imports: [],
  templateUrl: './historial-recargas-component.html',
  styleUrl: './historial-recargas-component.css',
})
export class HistorialRecargasComponent {
  
  @Input({ required: true }) recargas: ReporteRecarga[] = [];
}
