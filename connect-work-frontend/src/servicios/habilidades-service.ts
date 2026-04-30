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

@Injectable({
  providedIn: 'root'
})


export class HabilidadesService {

  constructor(private httpCliente: HttpClient) {
  }


  private constantesRest = new ConstantesRest();

  public crear(nuevo: Habilidad): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/habilidades', nuevo);
  }

  public editar(edicion: Habilidad): Observable<void> {
    return this.httpCliente.put<void>(this.constantesRest.getApiURL() + 'api/habilidades', edicion);
  }


  public buscarParaEditar(id: number): Observable<Habilidad> {
    return this.httpCliente.get<Habilidad>(this.constantesRest.getApiURL() + 'api/habilidades' + '/' + id);
  }

  public buscarHabilidad(id: number): Observable<Habilidad> {
    return this.httpCliente.get<Habilidad>(this.constantesRest.getApiURL() + 'api/habilidades' + '/' + id);
  }


  public buscarTodas(): Observable<Habilidad[]> {
    return this.httpCliente.get<Habilidad[]>(this.constantesRest.getApiURL() + 'api/habilidades/todas');
  }


  public buscarActivas(): Observable<Habilidad[]> {
    return this.httpCliente.get<Habilidad[]>(this.constantesRest.getApiURL() + 'api/habilidades/activas');
  }
}

