import { Component, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Comision } from '../../../modelos/comision';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ComisionesService } from '../../../servicios/comisionesService';

@Component({
  selector: 'app-comision-form',
  imports: [ReactiveFormsModule],
  templateUrl: './comision-form.html',
  styleUrl: './comision-form.css',
})
export class ComisionForm implements OnInit {
  formulario!: FormGroup;
  mensajeError!: string;

  intentoEnviarlo = signal<boolean>(false);
  hayError = signal<boolean>(false);
  enviando = signal<boolean>(false);

  @Input() comisionEnEdicion!: Comision;
  @Output() cambioExitoso = new EventEmitter<void>();
  @Output() cancelar = new EventEmitter<void>();

  constructor(
    private formBuilder: FormBuilder,
    private comisionesServicio: ComisionesService
  ) {}

  ngOnInit(): void {
    this.instanciarFormulario();
    if (this.comisionEnEdicion) {
      this.formulario.patchValue(this.comisionEnEdicion);
    }
  }

  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      porcentajeComision: [null, [Validators.required, Validators.min(1), Validators.max(100)]]
    });
  }

  public enviar() {
    this.reiniciarBooleanos();
    this.intentoEnviarlo.set(true);

    if (this.formulario.invalid) return;

    this.enviando.set(true);
    let datos = this.formulario.value as Comision;

    this.comisionesServicio.actualizar(datos).subscribe({
      next: () => {
        this.enviando.set(false);
        this.cambioExitoso.emit();
      },
      error: (errorHttp: any) => {
        this.enviando.set(false);
        this.registrarError(errorHttp);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }

  private reiniciarBooleanos() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(false);
  }

}