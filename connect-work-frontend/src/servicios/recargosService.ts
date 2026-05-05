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
import { CategoriaRequest } from "../modelos/categorias/categoriaRequest";
import { CategoriaResponse } from "../modelos/categorias/categoriaResponse";
import { Categoria } from "../modelos/categorias/categoria";
import { CarteraDigital } from "../modelos/recargos/carteraDigital";

@Injectable({
  providedIn: 'root'
})


export class CarteraService {

  private constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) { }

  public buscarCartera(cui: string): Observable<CarteraDigital> {
    return this.httpCliente.get<CarteraDigital>(this.constantesRest.getApiURL() + 'api/cartera/' + cui);
  }

  public recargar(datos: CarteraDigital): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/cartera', datos);
  }

}

