import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { ProyectoResponse } from '../../../modelos/proyectos/proyectoResponse';
import { BusquedaProyecto } from '../../../modelos/proyectos/busquedaProyecto';
import { ReturnStatement } from '@angular/compiler';
import { ProyectosService } from '../../../servicios/proyectosService';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ProyectoCard } from "../../../componentes/proyectos/proyecto-card/proyecto-card";

@Component({
  selector: 'app-proyectos-del-freelancer-page',
  imports: [Header, ProyectoCard],
  templateUrl: './proyectos-del-freelancer-page.html',
  styleUrl: './proyectos-del-freelancer-page.css',
})
export class ProyectosDelFreelancerPage implements OnInit {


  proyectos = signal<ProyectoResponse[] | null>(null);
  mensajeError !: string;
  hayError = signal<boolean>(false);

  constructor(private proyectosService: ProyectosService) {

  }
  ngOnInit(): void {
    const cuiFreelncer = localStorage.getItem('cui');
    if (cuiFreelncer === null) return;

    const busqueda: BusquedaProyecto = {
      cuiFreelancer: cuiFreelncer,
      idBusqueda: 5
    }
    this.buscarProyectosConContrato(busqueda);
  }



  private buscarProyectosConContrato(busqueda: BusquedaProyecto) {
    this.proyectosService.buscar(busqueda).subscribe({

      next: (proyectos: ProyectoResponse[]) => {
        this.proyectos.set(proyectos);
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

