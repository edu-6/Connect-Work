import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { SolicituRequest } from '../../../modelos/solicitudes/solicitudRequest';
import { SolicitudesService } from '../../../servicios/solicitudesService';
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';

@Component({
  selector: 'app-solicitudes-form',
  imports: [ReactiveFormsModule],
  templateUrl: './solicitudes-form.html',
  styleUrl: './solicitudes-form.css',
})
export class SolicitudesForm implements OnInit {


  formulario!: FormGroup;
  intentoEnviarlo = signal<boolean>(false);
  hayError = signal<boolean>(false);
  mensajeError: string = "";

  constructor(
    private formBuilder: FormBuilder,
    private solicitudesService: SolicitudesService,
    private router: Router,
    private autentiacionService: AutenticacionServicio
  ) { }

  ngOnInit(): void {
    this.instanciarFormulario();
  }

  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      nombre: ["", [Validators.required, Validators.maxLength(30)]],
      descripcion: ["", [Validators.required, Validators.maxLength(255)]],
      cuiUsuario: [localStorage.getItem('cui'), [Validators.required]]
    });
  }

  public enviar() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(true);

    if (this.formulario.invalid) return;

    const datos: SolicituRequest = this.formulario.value;


    if (this.autentiacionService.esCliente()) {
      this.enviarSolicitudCategoria(datos);
    } else {
      this.enviarSolicitudHabilidad(datos);
    }
  }



  private enviarSolicitudCategoria(solicitud: SolicituRequest) {
    this.solicitudesService.mandarSolicitudCategoria(solicitud).subscribe({
      next: () => {
        this.router.navigate(['/solicitudes/propias']);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }
    });
  }

  private enviarSolicitudHabilidad(solicitud: SolicituRequest) {
    this.solicitudesService.mandarSolicitudHabilidad(solicitud).subscribe({
      next: () => {
        this.router.navigate(['/solicitudes/propias']);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData?.detalles || "Ocurrió un error inesperado";
  }
}
