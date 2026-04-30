import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { HabilidadesService } from '../../../servicios/habilidades-service';
import { Habilidad } from '../../../modelos/habilidades/habilidad';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-habilidad-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './habilidad-form.html',
  styleUrl: './habilidad-form.css',
})
export class HabilidadForm implements OnInit {
  enEdicion = signal<boolean>(false);
  formulario !: FormGroup;




  mensajeEdicion: String = "Editar habilidad";
  mensajeCreacion: String = "Crear habilidad";

  mensajeError!: string;

  creadoConExito = signal<boolean>(false);
  editadoConExito = signal<boolean>(false)
  intentoEnviarlo = signal<boolean>(false)
  hayError = signal<boolean>(false);

  constructor(private formBuilder: FormBuilder,
    private habilidadesServicio: HabilidadesService,
    private router: Router) {
  }

  @Input()
  habilidadEnEdicion !: Habilidad;

  ngOnInit(): void {
    this.enEdicion.set(this.habilidadEnEdicion != null);
    this.instanciarFormulario();

    if (this.enEdicion()) {
      this.formulario.patchValue(this.habilidadEnEdicion);
    }

  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group(
      {
        id: [null],
        nombre: ["", [Validators.required, Validators.minLength(1), Validators.maxLength(30)]],
        activa: [true, Validators.required]
      }
    );
  }


  public enviar() {
    this.reiniciarBooleanos();
    this.intentoEnviarlo.set(true);

    if (!this.formulario.valid) return;

    let nuevo = this.formulario.value as Habilidad;


    if (this.enEdicion()) {
      this.editar(nuevo);
    } else {
      this.crear(nuevo);
    }
  }


  private crear(nuevo: Habilidad) {
    this.habilidadesServicio.crear(nuevo).subscribe({
      next: () => {
        this.router.navigate([`/habilidades`]);
      },
      error: (errorHttp: any) => {
        this.registrarError(errorHttp);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


  private editar(habilidad: Habilidad) {
    this.habilidadesServicio.editar(habilidad).subscribe({
      next: () => {
        this.editadoConExito.set(true);
        this.router.navigate([`/habilidades`]);
      },
      error: (errorHttp: any) => {
        this.editadoConExito.set(false);
        this.registrarError(errorHttp);
      }
    });
  }


  private reiniciarBooleanos() {
    this.editadoConExito.set(false);
    this.hayError.set(false);
    this.intentoEnviarlo.set(false);
  }


}
