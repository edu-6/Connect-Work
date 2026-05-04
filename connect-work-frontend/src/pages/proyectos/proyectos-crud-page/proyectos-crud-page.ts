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
        fechaInicio: [null, [Validators.required]],
        fechaFin: [null, [Validators.required]],
      }
    );
  }



  public buscarPorRango() {
    this.hayError.set(false);

    const busqueda: BusquedaProyecto = {
      cuiCliente: localStorage.getItem('cui') || undefined,
      fechaInicio: this.barraBusqueda.get('fechaInicio')?.value,
      fechaFin: this.barraBusqueda.get('fechaFin')?.value,
      idBusqueda: 1
    }

    this.buscarConBusqueda(busqueda);
  }

  public buscarTodos() {
    this.hayError.set(false);
    let cuiCliente = null;
    cuiCliente = localStorage.getItem('cui');
    const miBusqueda: BusquedaProyecto = {
      cuiCliente: cuiCliente || undefined,
      idBusqueda: 6
    };
    this.buscarConBusqueda(miBusqueda);
  }


  private buscarConBusqueda(busqueda: BusquedaProyecto) {
    this.proyectosService.buscar(busqueda).subscribe({
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

}
