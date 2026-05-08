import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';
import { EntregaResponse } from '../../../modelos/entregas/entregaResponse';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { RechazoPropuestaForm } from "../../rechazoEntrega/rechazo-propuesta-form/rechazo-entrega-form";

@Component({
  selector: 'app-entrega-card',
  imports: [RechazoPropuestaForm],
  templateUrl: './entrega-card.html',
  styleUrl: './entrega-card.css',
})
export class EntregaCard {


  constructor(
    private servicioAutenticacion: AutenticacionServicio
  ) {}



  botonesActivos = signal(true);
  hayError = signal(false);
  mensajeError!: string;

  @Input({ required: true })
  entrega!: EntregaResponse;

  @Output()
  recargarPaginaAction = new EventEmitter<void>();

  esAdmin() {
    return this.servicioAutenticacion.esAdmin();
  }

  esCliente() {
    return this.servicioAutenticacion.esCliente();
  }

  esFreelancer() {
    return this.servicioAutenticacion.esFreelancer();
  }

  aceptarEntrega() {
    alert("Aprobar entrega id: " + this.entrega.id);
  }

  rechazarEntrega() {
    alert("Rechazar entrega id: " + this.entrega.id);
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


  public desactivarBotones(){
    this.botonesActivos.set(false);
  }
  public activarBotones(){
    this.botonesActivos.set(true);
  }


  public mandarARecargarPagina(){
    this.activarBotones();
    this.recargarPaginaAction.emit();
  }
}
