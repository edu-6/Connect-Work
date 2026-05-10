import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { PerfilSimpleResponse } from '../../../../modelos/perfiles/perfiles-response/usuarioSimpleResponse';
import { Router } from '@angular/router';
import { usuariosService } from '../../../../servicios/usuariosService';
import { ErrorBackend } from '../../../../modelos/ErrorBackend';

@Component({
  selector: 'app-usuario-simple-card',
  imports: [],
  templateUrl: './usuario-simple-card.html',
  styleUrl: './usuario-simple-card.css',
})
export class UsuarioSimpleCard {

  hayError = signal<boolean>(false);
  mensajeError !: string;



  constructor(private router: Router, private usuariosService: usuariosService) {

  }

  @Input({ required: true })
  perfil !: PerfilSimpleResponse;


  @Output()
  cambioEstado = new EventEmitter<void>();




  verPerfil(nickane: string) {
    this.router.navigate(['/perfil', nickane]);
  }


  cambiarEstadoPerfil(nickname: string) {
    this.usuariosService.cambiarEstadoActivo(nickname).subscribe({
      next: () => {
        this.cambioEstado.emit();
      },
      error: (error: any) => {
        this.hayError.set(true);
        const errorData: ErrorBackend = error.error;
        this.mensajeError = errorData.detalles;
      }
    });
  }
}
