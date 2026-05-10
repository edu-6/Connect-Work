import { Component, Input } from '@angular/core';
import { PerfilPlataformaResponse } from '../../../../modelos/perfiles/perfiles-response/perfilPlataformaResponse';

@Component({
  selector: 'app-usuario-plataforma-card',
  imports: [],
  templateUrl: './usuario-plataforma-card.html',
  styleUrl: './usuario-plataforma-card.css',
})
export class UsuarioPlataformaCard {


  @Input({required:true})
  perfil!: PerfilPlataformaResponse
}
