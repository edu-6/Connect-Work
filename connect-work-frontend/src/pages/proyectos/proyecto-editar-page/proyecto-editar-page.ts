import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { ProyectoResponse } from '../../../modelos/proyectos/proyectoResponse';
import { ProyectosService } from '../../../servicios/proyectosService';
import { ActivatedRoute } from '@angular/router';
import { ProyectoRequest } from '../../../modelos/proyectos/proyectoRequest';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ProyectoForm } from "../../../componentes/proyectos/proyecto-form/proyecto-form";

@Component({
  selector: 'app-proyecto-editar-page',
  imports: [Header, ProyectoForm],
  templateUrl: './proyecto-editar-page.html',
  styleUrl: './proyecto-editar-page.css',
})
export class ProyectoEditarPage implements OnInit {
  idProyecto !: number;

  proyecto !: ProyectoRequest;
  hayError = signal<boolean>(false);
  mensajeError !: string;

  encontrado = signal<boolean>(false);

  constructor(private proyectosService: ProyectosService, private router: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.idProyecto = this.router.snapshot.params['id'];
    this.proyectosService.buscarPorId(this.idProyecto).subscribe({
      next: (resp: ProyectoRequest) => {
        this.proyecto = resp;
        this.encontrado.set(true);
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
