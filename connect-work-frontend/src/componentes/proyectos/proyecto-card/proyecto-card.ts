import { Component, Input } from '@angular/core';
import { ProyectoResponse } from '../../../modelos/proyectos/proyectoResponse';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-proyecto-card',
  imports: [RouterLink],
  templateUrl: './proyecto-card.html',
  styleUrl: './proyecto-card.css',
})
export class ProyectoCard {

  constructor(private router: Router) { }

  @Input({ required: true })
  proyecto !: ProyectoResponse;

  @Input({ required: true })
  mostrarBtnEditar !: boolean;



  redirigirAlPerfilCliente(){
    this.router.navigate(['/perfil', this.proyecto.nicknameCliente]);
    }
}
