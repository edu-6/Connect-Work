import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { ProyectoResponse } from '../../../modelos/proyectos/proyectoResponse';
import { ActivatedRoute } from '@angular/router';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ProyectosService } from '../../../servicios/proyectosService';
import { ProyectoCard } from "../../../componentes/proyectos/proyecto-card/proyecto-card";
import { PropuestaForm } from "../../../componentes/propuestas/propuesta-form/propuesta-form";
import { PropuestaCard } from "../../../componentes/propuestas/propuesta-card/propuesta-card";
import { PropuestasService } from '../../../servicios/propuestasService';
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';
import { PropuestaResponse } from '../../../modelos/propuestas/propuestasResponse';
import { TipoBusquedaPropuesta } from '../../../modelos/enumTipoBusquedaPropuesta';
import { CommonModule } from '@angular/common';
import { BusquedaPropuesta } from '../../../modelos/propuestas/busquedaPropuesta';
import { ContratosService } from '../../../servicios/contratosService';
import { ContratoResponse } from '../../../modelos/contratos/contratoResponse';
import { ContratoCard } from "../../../componentes/contratos/contrato-card/contrato-card";

@Component({
  selector: 'app-proyecto-detalle-page',
  imports: [Header, ProyectoCard, PropuestaForm, PropuestaCard, CommonModule, ContratoCard],
  templateUrl: './proyecto-detalle-page.html',
  styleUrl: './proyecto-detalle-page.css',
})
export class ProyectoDetallePage implements OnInit {

  idProyecto !: number;

  proyecto !: ProyectoResponse;
  hayError = signal<boolean>(false);
  mensajeError !: string;
  encontrado = signal<boolean>(false);



  puedeEliminar = signal<boolean>(false);

  mostrarFormulario = signal<boolean>(false);

  // Propuestas
  propuestaFreelancer = signal<PropuestaResponse | null>(null);
  propuestasProyecto = signal<PropuestaResponse[] | null>(null);


  // estados del poryecto
  abierto = signal<boolean>(false);
  enProgreso = signal<boolean>(false);
  entregaPendiente = signal<boolean>(false);
  compeltado = signal<boolean>(false);
  cancelado = signal<boolean>(false);


  contrato = signal<ContratoResponse | null>(null);

  constructor(
    private proyectosService: ProyectosService,
    private propuestasService: PropuestasService,
    private authServicio: AutenticacionServicio,
    private router: ActivatedRoute,
    private contratosService: ContratosService
  ) { }

  ngOnInit(): void {
    this.idProyecto = this.router.snapshot.params['id'];
    this.buscarProyecto();

    this.puedeEliminar.set(this.authServicio.esCliente());
  }

  public buscarProyecto() {
    this.proyectosService.buscarResponsePorId(this.idProyecto).subscribe({
      next: (resp: ProyectoResponse) => {
        this.proyecto = resp;
        this.encontrado.set(true);
        this.definirEstadoProyecto(resp.estado);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  public definirEstadoProyecto(estado: string) {

    this.abierto.set(false);
    this.enProgreso.set(false);
    this.entregaPendiente.set(false);
    this.compeltado.set(false);
    this.cancelado.set(false);
    
    switch (estado) {
      case "ABIERTO":
        this.cargarPropuestas();
        this.abierto.set(true);
        break;
      case "EN PROGRESO":
        this.cargarContrato();
        this.enProgreso.set(true);
        break;
      case "ENTREGA PENDIENTE":
        this.entregaPendiente.set(true);
        break;
      case "COMPLETADO":
        this.compeltado.set(true);
        break;
      case "CANCELADO":
        this.cancelado.set(true);
        break;
    }
  }



  public cargarPropuestas() {

  if (this.authServicio.esFreelancer()) {
    const busqueda: BusquedaPropuesta = {
      idProyecto: this.idProyecto,
      cuiFreelancer: localStorage.getItem('cui') || undefined,
      idTipoBusqueda: TipoBusquedaPropuesta.DE_FREELANCER_EN_PROYECTO
    }
    this.buscarPropuestas(busqueda);
  } else if (this.authServicio.esCliente()) {
    const busqueda: BusquedaPropuesta = {
      idProyecto: this.idProyecto,
      idTipoBusqueda: TipoBusquedaPropuesta.EN_PROYECTO
    }
    this.buscarPropuestas(busqueda);
  }
}

  private buscarPropuestas(busqueda: BusquedaPropuesta) {
  this.propuestasService.buscarPropuestas(busqueda).subscribe({
    next: (resp) => {
      if (Array.isArray(resp)) {
        this.propuestasProyecto.set(resp);
      } else {
        this.propuestaFreelancer.set(resp);
      }
    },
    error: (httpErrro: any) => {
      this.registrarError(httpErrro);
    }
  });
}





  public cargarContrato() {
  this.contratosService.buscarContratoDeProyecto(this.proyecto.id).subscribe({
    next: (resp: ContratoResponse) => {
      this.contrato.set(resp);
      this.abierto.set(false);

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

  public activarFormulario() {
  this.mostrarFormulario.set(true);
}

  public cerrarFormularioActcion() {
  this.mostrarFormulario.set(false);
  if (this.authServicio.esFreelancer()) {
    this.cargarPropuestas();
  }
}

  public esCliente() {
  return this.authServicio.esCliente();
}


}
