import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { ContratoResponse } from '../../../modelos/contratos/contratoResponse';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ContratosService } from '../../../servicios/contratosService';

@Component({
  selector: 'app-contrato-card',
  imports: [],
  templateUrl: './contrato-card.html',
  styleUrl: './contrato-card.css',
})
export class ContratoCard {

  @Input({ required: true }) contrato!: ContratoResponse;
  @Output() contratoCanceladoAction = new EventEmitter<void>();

  hayError = signal(false);
  mensajeError = "";

  constructor(private contratosService: ContratosService) { }

  public cancelarContrato() {
    this.hayError.set(false);
    this.contratosService.cancelarContrato(this.contrato.id!).subscribe({
      next: () => {
        this.contratoCanceladoAction.emit();
      },
      error: (err) => {
        this.hayError.set(true);
        const errorData: ErrorBackend = err.error;
        this.mensajeError = errorData.detalles;
      }
    });
  }
}
