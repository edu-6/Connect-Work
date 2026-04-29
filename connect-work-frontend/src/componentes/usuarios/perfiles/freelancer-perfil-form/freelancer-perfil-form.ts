import { Component, OnInit, signal } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ErrorBackend } from '../../../../modelos/ErrorBackend';
import { Rol } from '../../../../modelos/rol';
import { UsuarioPlataformaRequest } from '../../../../modelos/usuarios/usuarioRequest';
import { EnumsService } from '../../../../servicios/enums-service';
import { PerfilesService } from '../../../../servicios/perfiles-service';
import { NivelExperiencia } from '../../../../modelos/nivelExperiencia';
import { PefilFreelancer } from '../../../../modelos/perfiles/freelancerPerfil';
import { AutenticacionServicio } from '../../../../servicios/autenficacion-service';

@Component({
  selector: 'app-freelancer-perfil-form',
  imports: [ReactiveFormsModule],
  templateUrl: './freelancer-perfil-form.html',
  styleUrl: './freelancer-perfil-form.css',
})
export class FreelancerPerfilForm implements OnInit {

  formulario !: FormGroup;

  intentoEnviarlo = signal<boolean>(false);
  enEdicion = signal<boolean>(false);
  hayError = signal<boolean>(false);

  nivelesExperiencia = signal<NivelExperiencia[]>([]);
  mensajeError !: string;

  constructor(private formBuilder: FormBuilder,
    private enumsService: EnumsService,
    private perfilesService: PerfilesService,
    private router: Router,
    private routerParams: ActivatedRoute,
    private autenticacionService: AutenticacionServicio
  ) {

  }


  ngOnInit(): void {
    this.cargarNivelesExperiencia();
    this.instanciarFormulario();
  }


  private instanciarFormulario() {
    this.formulario = this.formBuilder.group(
      {
        cuiFreelancer: [localStorage.getItem('cui'), [Validators.required, Validators.maxLength(50)]],
        biografia: ["", [Validators.required, Validators.maxLength(300)]],
        tarifaHora: [0, [Validators.required, Validators.min(1)]],
        idNivelExperiencia: [null, [Validators.required]]
      }
    );
  }


  


  enviar() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(true);
    if (!this.formulario.valid) return;

    let nuevo = this.formulario.value as PefilFreelancer;
    if (this.enEdicion()) {
      //this.editar(nuevo);
    } else {
      this.guardarNuevo(nuevo);
    }
  }


  private guardarNuevo(nuevo: PefilFreelancer) {
    this.perfilesService.crearPerfilFreelancer(nuevo).subscribe({
      next: () => {
        this.autenticacionService.marcarPerfilCompletado();
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




  cargarNivelesExperiencia() {
    this.enumsService.getNivelesExperiencia().subscribe({
      next: (array: NivelExperiencia[]) => {
        this.nivelesExperiencia.set(array);
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
