import { Component, EventEmitter, Input, Output, output, signal } from '@angular/core';
import { PropuestaResponse } from '../../../modelos/propuestas/propuestasResponse';
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';
import { PropuestasService } from '../../../servicios/propuestasService';
import { producerUpdateValueVersion } from '@angular/core/primitives/signals';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { RechazoPropuestaForm } from "../../rechazoEntrega/rechazo-propuesta-form/rechazo-entrega-form";

@Component({
  selector: 'app-propuesta-card',
  imports: [],
  templateUrl: './propuesta-card.html',
  styleUrl: './propuesta-card.css',
})
export class PropuestaCard {
  constructor(private autenticacionService: AutenticacionServicio,
    private propuestasServiec: PropuestasService
  ){

  }

  
  hayError = signal(false);
  mostrarFormularioRechazo = signal(false);
  mensajeError !:string;
  @Input({ required: true }) propuesta!: PropuestaResponse;


  @Output()
  recargarPaginaAction = new EventEmitter<void>();


  public esFreelancer(){
    return this.autenticacionService.esFreelancer();
  }

  public esCliente(){
    return this.autenticacionService.esCliente();
  }


  public eliminarPropuesta(){
    this.propuestasServiec.eliminarPropuesta(this.propuesta.id).subscribe({
      next: () =>{
        this.recargarPaginaAction.emit();
      },
      error: (http: any)=>{
        this.registrarError(http);
      }
    });
  }

  private registrarError(httpError: any) {
      this.hayError.set(true);
      const errorData: ErrorBackend = httpError.error;
      this.mensajeError = errorData.detalles;
  }
  public recargarPropuestas(){
    this.recargarPaginaAction.emit();
  }


  public rechazarPropuesta(){
    this.propuestasServiec.rechazarPropuesta(this.propuesta.id).subscribe({
     next: () =>{
        this.recargarPaginaAction.emit();
      },
      error: (http: any)=>{
        this.registrarError(http);
      }
    });
  }
}
