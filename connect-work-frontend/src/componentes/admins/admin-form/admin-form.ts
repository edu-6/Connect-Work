import { Component, OnInit, signal } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminRequest } from '../../../modelos/adminRequest';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { usuariosService } from '../../../servicios/usuariosService';

@Component({
  selector: 'app-admin-form',
  imports: [ReactiveFormsModule],
  templateUrl: './admin-form.html',
  styleUrl: './admin-form.css',
})
export class AdminForm implements OnInit {
formulario!: FormGroup;
  intentoEnviarlo = signal<boolean>(false);
  hayError = signal<boolean>(false);
  mensajeError: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private usuariosService: usuariosService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.instanciarFormulario();
  }

  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      nickname: ["", [Validators.required, Validators.maxLength(30)]],
      nombre: ["", [Validators.required, Validators.maxLength(80)]],
      contraseña: ["", [Validators.required, Validators.maxLength(700)]],
      idRol: [1]
    });
  }

  enviar() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(true);

    if (this.formulario.invalid) return;
    const nuevoAdmin: AdminRequest = this.formulario.value;
    
    this.usuariosService.crearAdmin(nuevoAdmin).subscribe({
      next: () => {
        this.redirigirAHome();
      },
      error: (error) => {
        this.registrarError(error);
      }
    });
  }

  public redirigirAHome() {
    this.router.navigate(['']);
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData?.detalles || 'Ocurrió un error inesperado';
  }

  
}
