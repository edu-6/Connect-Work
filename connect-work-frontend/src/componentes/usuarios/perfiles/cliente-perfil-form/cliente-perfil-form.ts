import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ErrorBackend } from '../../../../modelos/ErrorBackend';
import { ClientePefil } from '../../../../modelos/perfiles/usuarioPerfil';
import { PerfilesService } from '../../../../servicios/perfiles-service';

@Component({
  selector: 'app-cliente-perfil-form',
  imports: [ReactiveFormsModule],
  templateUrl: './cliente-perfil-form.html',
  styleUrl: './cliente-perfil-form.css',
})
export class ClientePerfilForm implements OnInit {
  formulario!: FormGroup;

  intentoEnviarlo = signal<boolean>(false);
  hayError = signal<boolean>(false);
  mensajeError!: string;

  constructor(
    private formBuilder: FormBuilder,
    private perfilesService: PerfilesService,
    private router: Router
  )
   {
    
   }

  ngOnInit(): void {
    this.instanciarFormulario();
  }

  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      cuiUsuario: [localStorage.getItem('cui')],
      descripcion: ["", [Validators.required, Validators.maxLength(500)]],
      sitioWeb: ["", [ Validators.minLength(1),Validators.maxLength(100)]],
      industria: ["", [Validators.required, Validators.maxLength(100)]],
    });
  }

  enviar() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(true);
    if (!this.formulario.valid) return;

    let nuevo = this.formulario.value as ClientePefil;
    this.guardarNuevo(nuevo);
  }

  private guardarNuevo(nuevo: ClientePefil) {
    this.perfilesService.crearPefilcliente(nuevo).subscribe({
      next: () => {
        this.redirigirAHome();
      },
      error: (error: any) => {
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
    this.mensajeError = errorData.detalles;
  }
}
