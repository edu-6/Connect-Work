import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { SolicitudResponse } from '../../../modelos/solicitudes/solicitudResponse';
import { SolicitudesCard } from "../../../componentes/solicitudes/solicitudes-card/solicitudes-card";
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';
import { SolicitudesService } from '../../../servicios/solicitudesService';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-solicitudes-propias-page',
  imports: [Header, SolicitudesCard, RouterLink],
  templateUrl: './solicitudes-propias-page.html',
  styleUrl: './solicitudes-propias-page.css',
})
export class SolicitudesPropiasPage implements OnInit {

  hayError = signal<boolean>(false);
  mensajeError !: string;

  solicitudes = signal<SolicitudResponse[] | null>(null);

  constructor(private authService: AutenticacionServicio,
    private solictudesService: SolicitudesService
  ) {

  }

  ngOnInit(): void {
    this.cargarSolicitudes();
  }


  cargarSolicitudes() {

    if (this.authService.esCliente()) {
      this.cargarSolicitudesCategorias()
    } else if (this.authService.esFreelancer()) {
      this.cargarSolicitudesHabilidades();
    }

  }
  
  cargarSolicitudesHabilidades() {
    const cui = localStorage.getItem('cui');
    if (cui === null) return;
    this.solictudesService.buscarSolicitudesDeHabilidadPropias(cui).subscribe({
      next: (datos: SolicitudResponse[]) => {
        this.solicitudes.set(datos);
      },
      error: (error: any) => {
        this.registrarError(error);
      }

    });
  }

  cargarSolicitudesCategorias() {
    const cui = localStorage.getItem('cui');
    if (cui === null) return;
    this.solictudesService.buscarSolicitudesDeCategoriaPropias(cui).subscribe({
      next: (datos: SolicitudResponse[]) => {
        this.solicitudes.set(datos);
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
