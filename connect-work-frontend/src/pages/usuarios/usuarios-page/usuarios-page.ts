import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { EnumsService } from '../../../servicios/enums-service';
import { Rol } from '../../../modelos/rol';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { PerfilSimpleResponse } from '../../../modelos/perfiles/perfiles-response/usuarioSimpleResponse';
import { PerfilesService } from '../../../servicios/perfiles-service';
import { UsuarioSimpleCard } from "../../../componentes/usuarios/perfiles/usuario-simple-card/usuario-simple-card";
import { immediateProvider } from 'rxjs/internal/scheduler/immediateProvider';

@Component({
  selector: 'app-usuarios-page',
  imports: [Header, UsuarioSimpleCard],
  templateUrl: './usuarios-page.html',
  styleUrl: './usuarios-page.css',
})
export class UsuariosPage implements OnInit {


  hayError = signal<boolean>(false);
  roles = signal<Rol[]>([]);
  mensajeError !: string;

  rolActal !: string;

  perfiles = signal<PerfilSimpleResponse [] | null>(null);

  constructor(private enumsService: EnumsService,
    private perfilesService: PerfilesService
  ) {}


  ngOnInit(): void {
    this.cargarRoles();
  }


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


  cargarUsuarios(idRol: string) {
    this.rolActal = idRol;
    this.perfilesService.buscarPerfilesPorRol(idRol).subscribe({
      
      next:(perfiles: PerfilSimpleResponse[])=>{
        this.perfiles.set(perfiles);
      },
      error:(error: any)=>{
        this.registrarError(error);
      }
    });
  }


  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }


  public cargarUsuariosRolActual(){
    this.cargarUsuarios(this.rolActal);
  }









}
