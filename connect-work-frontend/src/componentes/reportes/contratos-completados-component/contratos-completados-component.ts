import { Component, Input } from '@angular/core';
import { ReporteContratoCompletado } from '../../../modelos/reportes/reporteContratoCompletado';

@Component({
  selector: 'app-contratos-completados-component',
  imports: [],
  templateUrl: './contratos-completados-component.html',
  styleUrl: './contratos-completados-component.css',
})
export class ContratosCompletadosComponent {

  @Input({ required: true }) contratos: ReporteContratoCompletado[] = [];
}
