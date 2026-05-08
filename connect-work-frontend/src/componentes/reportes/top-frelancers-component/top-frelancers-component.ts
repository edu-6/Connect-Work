import { Component, Input } from '@angular/core';
import { ReporteTopFreelancer } from '../../../modelos/reportes/reporteTopFreelancer';

@Component({
  selector: 'app-top-frelancers-component',
  imports: [],
  templateUrl: './top-frelancers-component.html',
  styleUrl: './top-frelancers-component.css',
})
export class TopFrelancersComponent {
  @Input({ required: true }) datos: ReporteTopFreelancer[] = [];
}
