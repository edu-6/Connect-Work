import { Component, Input } from '@angular/core';
import { ReportePropuestaEnviada } from '../../../modelos/reportes/reportePropuestaEnviada';

@Component({
  selector: 'app-propuestas-enviadas-component',
  imports: [],
  templateUrl: './propuestas-enviadas-component.html',
  styleUrl: './propuestas-enviadas-component.css',
})
export class PropuestasEnviadasComponent {
  
@Input({ required: true }) propuestas: ReportePropuestaEnviada[] = [];

}
