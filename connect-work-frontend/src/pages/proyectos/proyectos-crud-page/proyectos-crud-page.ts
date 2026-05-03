import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { Habilidad } from '../../../modelos/habilidades/habilidad';
import { HabilidadesService } from '../../../servicios/habilidades-service';
import { HabilidadCard } from "../../../componentes/habilidades/habilidad-card/habilidad-card";
import { RouterLink } from '@angular/router';
import { ProyectoResponse } from '../../../modelos/proyectos/proyectoResponse';
import { ProyectosService } from '../../../servicios/proyectosService';
import { BusquedaProyecto } from '../../../modelos/proyectos/busquedaProyecto';
import { ProyectoCard } from "../../../componentes/proyectos/proyecto-card/proyecto-card";

@Component({
  selector: 'app-proyectos-crud-page',
  imports: [Header, ReactiveFormsModule, RouterLink, ProyectoCard],
  templateUrl: './proyectos-crud-page.html',
  styleUrl: './proyectos-crud-page.css',
})
export class ProyectosCrudPage implements OnInit {


  public barraBusqueda !: FormGroup;

  mensajeError !: string;

  hayError = signal<boolean>(false);
  buscandoTodos = signal<boolean>(false);
  buscandoUno = signal<boolean>(false);

  public proyectoEncontrado = signal<ProyectoResponse | null>(null);

  constructor(private formBuilder: FormBuilder, private proyectosService: ProyectosService) {

  }

  proyectos = signal<ProyectoResponse[]>([]);

  ngOnInit(): void {
    this.buscarTodos();

    this.barraBusqueda = this.formBuilder.group(
      {
        busqueda: ["", [Validators.required]]
      }
    );
  }

  public buscarTodos() {
    let cuiCliente = null;
     cuiCliente = localStorage.getItem('cui');
    const miBusqueda: BusquedaProyecto = {
      cuiCliente : cuiCliente || undefined,
      idBusqueda: 6
    };

    this.proyectosService.buscar(miBusqueda).subscribe({
      next: (todos: ProyectoResponse[]) => {
        this.proyectos.set(todos);
        this.buscandoUno.set(false);
        this.buscandoTodos.set(true);
      },
      error: (errorHtttp: any) => {
        this.registrarError(errorHtttp);
      }
    });
  }



  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


  public buscarProyectos() {

  }
}
