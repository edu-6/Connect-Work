import { Component, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { SolicitudResponse } from '../../../modelos/solicitudes/solicitudResponse';
import { SolicitudesService } from '../../../servicios/solicitudesService';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { SolicitudesCard } from "../../../componentes/solicitudes/solicitudes-card/solicitudes-card";

@Component({
  selector: 'app-solicitudes-page',
  imports: [Header, SolicitudesCard],
  templateUrl: './solicitudes-page.html',
  styleUrl: './solicitudes-page.css',
})
export class SolicitudesPage {


  hayError = signal<boolean>(false);
  mensajeError !: string;
  solicitudes = signal<SolicitudResponse[] | null>(null);

  ultimaBusqueda :number = 0;

  constructor(private solicitudesService: SolicitudesService) {

  }




  cargarSolicitudesCategorias() {
    this.ultimaBusqueda = 1;
    this.solicitudesService.buscarSolicitudesDeCategorias().subscribe({
      next: (datos: SolicitudResponse[]) => {
        this.solicitudes.set(datos);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });

  }


  cargarSolicitudesHabilidades() {
    this.ultimaBusqueda = 2;
        this.solicitudesService.buscarSolicitudesDeHabilidades().subscribe({
      next: (datos: SolicitudResponse[]) => {
        this.solicitudes.set(datos);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });

  }


  recargarPagina(){
    if(this.ultimaBusqueda == 1){
      this.cargarSolicitudesCategorias();
    }else{
      this.cargarSolicitudesHabilidades();
    }
  }


  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }





}
