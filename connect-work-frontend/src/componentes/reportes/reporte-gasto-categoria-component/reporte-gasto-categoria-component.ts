import { Component, Input } from '@angular/core';
import { ReporteGastoCategoria } from '../../../modelos/reportes/reporteGastoCategoria';

@Component({
  selector: 'app-reporte-gasto-categoria-component',
  imports: [],
  templateUrl: './reporte-gasto-categoria-component.html',
  styleUrl: './reporte-gasto-categoria-component.css',
})
export class ReporteGastoCategoriaComponent {

  @Input({ required: true }) datos: ReporteGastoCategoria[] = [];

  calcularTotal(): string {
    return this.datos.reduce((acc, current) => acc + current.totalGastado, 0).toFixed(2);
  }
}
