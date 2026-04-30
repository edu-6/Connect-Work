import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AutenticacionServicio } from '../../servicios/autenficacion-service';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {



  titulo = signal<string>("titulo de la pagina");


  constructor(public autenticacionService: AutenticacionServicio){

  }


}
