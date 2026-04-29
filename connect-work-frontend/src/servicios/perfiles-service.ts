import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable, tap } from "rxjs";
import { Injectable, signal } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioLoginRequest } from "../modelos/login/usuarioLoginRequest";
import { UsuarioLoginResponse } from "../modelos/login/usuarioLoginResponse";
import { PefilFreelancer } from "../modelos/perfiles/freelancerPerfil";
import { ClientePefil } from "../modelos/perfiles/usuarioPerfil";

@Injectable({
  providedIn: 'root'
})


export class PerfilesService {

  constructor(private httpCliente: HttpClient) {
  }


  private constantesRest = new ConstantesRest();

  public crearPerfilFreelancer(nuevo: PefilFreelancer): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/perfiles/freelancer', nuevo);
  }

  public crearPefilcliente(nuevo: ClientePefil): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/perfiles/cliente', nuevo);
  }
}

