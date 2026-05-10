import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLinkActive } from '@angular/router';
import { PerfilesService } from '../../../servicios/perfiles-service';
import { PerfilPlataformaResponse } from '../../../modelos/perfiles/perfiles-response/perfilPlataformaResponse';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { Header } from "../../../shared/header/header";
import { UsuarioPlataformaCard } from "../../../componentes/usuarios/perfiles/usuario-plataforma-card/usuario-plataforma-card";

@Component({
  selector: 'app-perfil-detalle-page',
  imports: [Header, UsuarioPlataformaCard],
  templateUrl: './perfil-detalle-page.html',
  styleUrl: './perfil-detalle-page.css',
})
export class PerfilDetallePage implements OnInit{

  constructor(private router: ActivatedRoute,
    private perfilesService: PerfilesService
  ){

  }

  hayError = signal<boolean>(false);
  mensajeError !: string;
  perfil = signal<PerfilPlataformaResponse |null>(null);

  private nickname !: string;

  ngOnInit(): void {
    this.nickname = this.router.snapshot.params['nickname'];

    if(this.nickname){
      this.cargarPerfil();
    }
  }



  private cargarPerfil(){
    this.perfilesService.buscarPerflCompleto(this.nickname).subscribe({

      next:(perfi: PerfilPlataformaResponse)=>{
        this.perfil.set(perfi);
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


}
