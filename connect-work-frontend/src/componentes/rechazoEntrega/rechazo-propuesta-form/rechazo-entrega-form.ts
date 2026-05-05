import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { rechazoEntregaService } from '../../../servicios/rechazoEntregaService';

@Component({
  selector: 'app-rechazo-propuesta-form',
  imports: [ReactiveFormsModule],
  templateUrl: './rechazo-entrega-form.html',
  styleUrl: './rechazo-entrega-form.css',
})
export class RechazoPropuestaForm {
  @Input({ required: true })
  idPropuesta!: number;

  @Output()
  recargarPropuestas = new EventEmitter<void>();

  formulario!: FormGroup;
  intentoEnviarlo = signal(false);
  hayError = signal(false);
  mensajeError = "";

  constructor(
    private fb: FormBuilder,
    private rechazoService: rechazoEntregaService
  ) {

   }

  ngOnInit(): void {
    this.formulario = this.fb.group({
      idSolicitud: [this.idPropuesta, [Validators.required]],
      motivo: ["", [Validators.required, Validators.maxLength(300)]]
    });
  }

  public enviar() {
    this.intentoEnviarlo.set(true);
    this.hayError.set(false);

    if (this.formulario.invalid) return;

    this.rechazoService.crear(this.formulario.value).subscribe({
      next: () => {
        this.recargarPropuestas.emit();
      },
      error: (err) => {
        this.hayError.set(true);
        const errorData: ErrorBackend = err.error;
        this.mensajeError = errorData.detalles;
      }
    });
  }

}
