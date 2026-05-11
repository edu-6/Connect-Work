import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { SolicitudResponse } from '../../../modelos/solicitudes/solicitudResponse';
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';
import { CambioEnSolicitud } from '../../../modelos/solicitudes/cambioEnSolicitud';
import { SolicitudesService } from '../../../servicios/solicitudesService';
import { ErrorBackend } from '../../../modelos/ErrorBackend';

@Component({
  selector: 'app-solicitudes-card',
  imports: [],
  templateUrl: './solicitudes-card.html',
  styleUrl: './solicitudes-card.css',
})
export class SolicitudesCard {

  constructor(private authService: AutenticacionServicio,
    private solicitudesService: SolicitudesService
  ) {
  }


  hayError = signal<boolean>(false);
  mensajeError !: string;

  @Input({ required: true }) solicitud!: SolicitudResponse;

  @Output()
  recargarPaginaAction = new EventEmitter<void>();



  aceptar() {
    const cambio: CambioEnSolicitud = {
      tipo: "ACEPTAR",
      idSolicitud: this.solicitud.id
    }

    this.realizarCambio(cambio);

  }


  rechazar() {

    const cambio: CambioEnSolicitud = {
      tipo: "RECHAZAR",
      idSolicitud: this.solicitud.id
    }

    this.realizarCambio(cambio);

  }


  realizarCambio(cambio: CambioEnSolicitud) {
    if (this.solicitud.tipoSolicitud === "HABILIDAD") {
      this.cambiarEstadoSolicitudHabilidad(cambio);
    } else if (this.solicitud.tipoSolicitud === "CATEGORIA") {
      this.cambiarEstadoSolicitudCategoria(cambio);
    }
  }



  cambiarEstadoSolicitudHabilidad(cambio: CambioEnSolicitud) {
    this.solicitudesService.hacerCambioEnSolicitudHabilidad(cambio).subscribe({
      next: () => {
        this.recargarPaginaAction.emit();
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  cambiarEstadoSolicitudCategoria(cambio: CambioEnSolicitud) {
    this.solicitudesService.hacerCambioEnSolicitudCategoria(cambio).subscribe({
      next: () => {
        this.recargarPaginaAction.emit();
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }


  esAdmin() {
    return this.authService.esAdmin();
  }


  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData?.detalles || "Ocurrió un error inesperado";
  }
}
