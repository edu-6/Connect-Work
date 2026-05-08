import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable, tap } from "rxjs";
import { Injectable, signal } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioLoginRequest } from "../modelos/login/usuarioLoginRequest";
import { UsuarioLoginResponse } from "../modelos/login/usuarioLoginResponse";
import { PefilFreelancer } from "../modelos/perfiles/freelancerPerfil";
import { ClientePefil } from "../modelos/perfiles/usuarioPerfil";
import { Habilidad } from "../modelos/habilidades/habilidad";
import { HabilidadCategoria } from "../modelos/categorias/habilidad-categoria";
import { RechazoEntrega } from "../modelos/rechazoPrpuestaProyecto";

@Injectable({
  providedIn: 'root'
})
export class rechazoEntregaService {

  private constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) { }

  


}