import { Component, Input } from '@angular/core';
import { ReporteHistorialProyecto } from '../../../modelos/reportes/reporteHistorialProyectos';

@Component({
  selector: 'app-historial-proyectos-component',
  imports: [],
  templateUrl: './historial-proyectos-component.html',
  styleUrl: './historial-proyectos-component.css',
})
export class HistorialProyectosComponent {

  @Input({ required: true })
  reportes: ReporteHistorialProyecto[] = [];
}
