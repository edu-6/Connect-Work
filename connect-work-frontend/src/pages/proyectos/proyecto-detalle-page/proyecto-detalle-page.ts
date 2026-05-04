import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { ProyectoResponse } from '../../../modelos/proyectos/proyectoResponse';
import { ActivatedRoute } from '@angular/router';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ProyectosService } from '../../../servicios/proyectosService';
import { ProyectoCard } from "../../../componentes/proyectos/proyecto-card/proyecto-card";

@Component({
  selector: 'app-proyecto-detalle-page',
  imports: [Header, ProyectoCard],
  templateUrl: './proyecto-detalle-page.html',
  styleUrl: './proyecto-detalle-page.css',
})
export class ProyectoDetallePage implements OnInit {

  idProyecto !: number;
  
  proyecto !: ProyectoResponse;
  hayError = signal<boolean>(false);
  mensajeError !: string;
  encontrado = signal<boolean>(false);

  constructor(
    private proyectosService: ProyectosService, 
    private router: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.idProyecto = this.router.snapshot.params['id'];
    this.buscarProyecto();
  }

  private buscarProyecto() {
    this.proyectosService.buscarResponsePorId(this.idProyecto).subscribe({
      next: (resp: ProyectoResponse) => {
        this.proyecto = resp;
        if(resp != null){
          this.encontrado.set(true);
        }
        
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }
}
