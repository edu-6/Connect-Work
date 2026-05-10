import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CalificaconesService } from '../../../servicios/calificacionesService';
import { ErrorBackend } from '../../../modelos/ErrorBackend';

@Component({
  selector: 'app-calificacion-form',
  imports: [ReactiveFormsModule],
  templateUrl: './calificacion-form.html',
  styleUrl: './calificacion-form.css',
})
export class CalificacionForm {

  @Input({ required: true })
  idEntrega!: number;
  


  @Output()
  recargarPaginaAction = new EventEmitter<void>();

  formulario!: FormGroup;
  intentoEnviarlo = signal(false);
  hayError = signal(false);
  mensajeError = "";

  constructor(
    private fb: FormBuilder,
    private calificacionesService : CalificaconesService
  ) {

  }
  ngOnInit(): void {
    this.formulario = this.fb.group({
      idEntrega: [this.idEntrega, [Validators.required]],
      cantidadEstrellas: [0, [Validators.required, Validators.min(0), Validators.max(5)]],
      comentario : ["", [Validators.required, Validators.maxLength(50)]]
    });
  }

  public enviar() {
    this.intentoEnviarlo.set(true);
    this.hayError.set(false);
    if (this.formulario.invalid) return;

    this.calificacionesService.crear(this.formulario.value).subscribe({
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
}
