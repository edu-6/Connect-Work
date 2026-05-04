import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { BusquedaProyecto } from '../../../modelos/proyectos/busquedaProyecto';
import { ProyectoResponse } from '../../../modelos/proyectos/proyectoResponse';
import { ProyectosService } from '../../../servicios/proyectosService';
import { ProyectoTipoBusqueda } from '../../../modelos/tiposBusquedaParaFreelancer';
import { Categoria } from '../../../modelos/categorias/categoria';
import { Habilidad } from '../../../modelos/habilidades/habilidad';
import { CategoriasService } from '../../../servicios/categorias-service';
import { HabilidadesService } from '../../../servicios/habilidades-service';
import { ProyectoCard } from "../../../componentes/proyectos/proyecto-card/proyecto-card";

@Component({
  selector: 'app-proyectos-buscar-page',
  imports: [Header, ReactiveFormsModule, ProyectoCard],
  templateUrl: './proyectos-buscar-page.html',
  styleUrl: './proyectos-buscar-page.css',
})
export class ProyectosBuscarPage implements OnInit {
  public barraBusqueda !: FormGroup;

  mensajeError !: string;

  hayError = signal<boolean>(false);
  buscandoTodos = signal<boolean>(false);
  buscandoUno = signal<boolean>(false);

  mostrarPresupuesto = signal<boolean>(false);
  mostrarCategoria = signal<boolean>(false);
  mostrarHabilidad = signal<boolean>(false);

  tiposBusqueda = ProyectoTipoBusqueda;
  
  categorias = signal<Categoria[]>([]);
  habilidades = signal<Habilidad[]>([]);

  public proyectoEncontrado = signal<ProyectoResponse | null>(null);

  constructor(
    private formBuilder: FormBuilder, 
    private proyectosService: ProyectosService,
    private categoriasService: CategoriasService,
    private habilidadesService: HabilidadesService
  ) {}

  proyectos = signal<ProyectoResponse[]>([]);


  public cambiarTipoBusqueda() {
    const tipoSeleccionado = Number(this.barraBusqueda.get('idBusqueda')?.value);
    this.mostrarPresupuesto.set(false);
    this.mostrarCategoria.set(false);
    this.mostrarHabilidad.set(false);

    if (tipoSeleccionado === ProyectoTipoBusqueda.POR_PRESUPUESTO) {
      this.mostrarPresupuesto.set(true);
    } else if (tipoSeleccionado === ProyectoTipoBusqueda.POR_CATEGORIA) {
      this.mostrarCategoria.set(true);
    } else if (tipoSeleccionado === ProyectoTipoBusqueda.POR_HABILIDAD) {
      this.mostrarHabilidad.set(true);
    }
  }


  

  

  ngOnInit(): void {
    this.cargarDatosIniciales();

    this.barraBusqueda = this.formBuilder.group({
      idCategoria: [null],
      idHabilidad: [null],
      fechaCreacion: [null],
      cuiFreelancer: [null],
      cuiCliente: [null],
      minPresupuesto: [null],
      maxiPresupuesto: [null],
      idBusqueda: [null],
      fechaInicio: [null],
      fechaFin: [null]
    });
  }



  public realizarBusqueda() {
    this.hayError.set(false);

    const busqueda = this.barraBusqueda.value as BusquedaProyecto;

    this.buscarConBusqueda(busqueda);
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



  private cargarDatosIniciales() {
    this.categoriasService.buscarTodas().subscribe({
      next: (array: Categoria[]) => {
        this.categorias.set(array);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });

    this.habilidadesService.buscarTodas().subscribe({
      next: (array: Habilidad[]) => {
        this.habilidades.set(array);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }
  
}
