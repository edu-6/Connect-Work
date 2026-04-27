import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Rol } from '../../../modelos/rol';
import { EnumsService } from '../../../servicios/enums-service';
import { usuariosService } from '../../../servicios/usuariosService';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { UsuarioPlataformaRequest } from '../../../modelos/usuarios/usuarioRequest';

@Component({
  selector: 'app-usuario-form',
  imports: [ReactiveFormsModule],
  templateUrl: './usuario-form.html',
  styleUrl: './usuario-form.css',
})
export class UsuarioForm implements OnInit {

  formulario !: FormGroup;



  intentoEnviarlo = signal<boolean>(false);
  enEdicion = signal<boolean>(false);
  hayError = signal<boolean>(false);

  roles = signal<Rol[]>([]);
  mensajeError !: string;




  constructor(
    private formBuilder: FormBuilder,
    private enumsService: EnumsService,
    private usuariosService: usuariosService,
    private router: Router,
    private routerParams: ActivatedRoute,
    /*private location: Location*/) {
  }


  ngOnInit(): void {

    /*
    if (this.edicion) {
      this.enEdicion.set(true);
    }*/


    //this.origen = this.routerParams.snapshot.queryParamMap.get('origin') || history.state?.origin || 'default';


    this.cargarRoles();
    this.instanciarFormulario();


   /* // recibir el parametro
    this.identificacionRecibida = this.routerParams.snapshot.params['identificacion'];
    if (this.identificacionRecibida) {
      this.vieneDeReservacionForm = true;
      this.formulario.patchValue({
        identificacion: this.identificacionRecibida
      });
    }


    if (this.edicion) {
      this.formulario.reset(this.edicion);
    }*/
  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group(
      {
        nickname: ["", [Validators.required, Validators.maxLength(30)]],
        contraseña: ["", [Validators.required, Validators.maxLength(700)]],
        idRol: [null, [Validators.required]],
        activo: [true, [Validators.required]],
        cui: ["", [Validators.required, Validators.maxLength(50)]],
        nombre: ["", [Validators.required, Validators.maxLength(80)]],
        correo: ["", [Validators.required, Validators.email, Validators.maxLength(100)]],
        telefono: ["", [Validators.required, Validators.maxLength(20)]],
        direccion: ["", [Validators.required, Validators.maxLength(300)]],
        fechaNacimiento: [new Date().toISOString().substring(0, 10), [Validators.required]],
        perfilCompletado: [false]
      }
    );
  }


  enviar() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(true);
    if (!this.formulario.valid) return;

    let nuevo = this.formulario.value as UsuarioPlataformaRequest;
    if (this.enEdicion()) {
      //this.editar(nuevo);
    } else {
      this.guardarNuevo(nuevo);
    }

  }
/*

  private editar(nuevo: ClienteRequest) {
    this.clientesService.editarCliente(nuevo).subscribe({
      next: () => {
        this.redirigirAPagina();
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }*/

  private guardarNuevo(nuevo: UsuarioPlataformaRequest) {
    this.usuariosService.crear(nuevo).subscribe({
      next: () => {
        this.redirigirAHome();
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  public redirigirAHome(){
    this.router.navigate(['']);
  }

/*
  cancelar() {
    this.redirigirAPagina();
  }*/

  /*
  redirigirAPagina() {
    if (this.origen === 'reservaciones-form') {

      this.router.navigate(['/reservaciones/form-page', this.formulario.value.identificacion]);

    } else if (this.origen === 'clientes-page') {

      this.router.navigate(['/clientes']);
    } else if (this.origen === 'perfil-usuario') {
      this.router.navigate(['clientes/detalles-page', this.edicion.identificacion]);
    }
    else {
      this.router.navigate(['/reservaciones']);
    }
  }*/



  cargarRoles() {
    this.enumsService.getRoles().subscribe({
      next: (array: Rol[]) => {
        this.roles.set(array);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }
    });
  }


  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }




}
