import { Component, Input } from '@angular/core';
import { ReporteHistorialComision } from '../../../modelos/reportes/reporteHostorialComision';

@Component({
  selector: 'app-historial-comisiones-component',
  imports: [],
  templateUrl: './historial-comisiones-component.html',
  styleUrl: './historial-comisiones-component.css',
})
export class HistorialComisionesComponent {

  @Input({ required: true }) datos: ReporteHistorialComision[] = [];


  public listaProcesada: ReporteHistorialComision[] = [];

  ngOnChanges() {
    this.procesarFechas();
  }

  private procesarFechas() {
    this.listaProcesada = this.datos.map((item, index) => {
      const siguiente = this.datos[index + 1];
      return {
        ...item,
        fechaFin: siguiente ? siguiente.fechaCambio : 'Actualidad'
      };
    });
  }
}
