import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { rechazoEntregaService } from '../../../servicios/rechazoEntregaService';
import { EntregasService } from '../../../servicios/entregasService.';

@Component({
  selector: 'app-rechazo-propuesta-form',
  imports: [ReactiveFormsModule],
  templateUrl: './rechazo-entrega-form.html',
  styleUrl: './rechazo-entrega-form.css',
})
export class RechazoPropuestaForm {
  @Input({ required: true })
  idEntrega!: number;

  @Output()
  recargarPropuestas = new EventEmitter<void>();

  @Output()
  cancelarRechazoAction = new EventEmitter<void>();

  @Output()
  recargarPaginaAction = new EventEmitter<void>();

  formulario!: FormGroup;
  intentoEnviarlo = signal(false);
  hayError = signal(false);
  mensajeError = "";

  constructor(
    private fb: FormBuilder,
    private entregasService: EntregasService
  ) {

   }

  ngOnInit(): void {
    this.formulario = this.fb.group({
      idEntrega: [this.idEntrega, [Validators.required]],
      motivo: ["", [Validators.required, Validators.maxLength(300)]]
    });
  }

  public enviar() {
    this.intentoEnviarlo.set(true);
    this.hayError.set(false);
    if (this.formulario.invalid) return;

    this.entregasService.rechazarEntrega(this.formulario.value).subscribe({
      next: () => {
        this.recargarPaginaAction.emit();
      },
      error: (err) => {
        
        this.hayError.set(true);
        const errorData: ErrorBackend = err.error;
        this.mensajeError = errorData.detalles;
      }
    });
  }


  public cancelarRechazo(){
    this.cancelarRechazoAction.emit();
  }

}
