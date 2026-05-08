import { Component, Input } from '@angular/core';
import { ReporteIngresosAdmin } from '../../../modelos/reportes/reporteTotalIngresos';

@Component({
  selector: 'app-total-ingresos-component',
  imports: [],
  templateUrl: './total-ingresos-component.html',
  styleUrl: './total-ingresos-component.css',
})
export class TotalIngresosComponent {
@Input({ required: true }) datos: ReporteIngresosAdmin | null = null;

}
