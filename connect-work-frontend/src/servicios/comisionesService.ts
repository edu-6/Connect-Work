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
import { Comision } from "../modelos/comision";

@Injectable({
  providedIn: 'root'
})
export class ComisionesService {

  private constantesRest = new ConstantesRest();
  private readonly URL = this.constantesRest.getApiURL() + 'api/comisiones';

  constructor(private httpCliente: HttpClient) { }


  public obtenerComision(): Observable<Comision> {
    return this.httpCliente.get<Comision>(this.URL);
  }
  
  public actualizar(comision: Comision): Observable<void> {
    return this.httpCliente.put<void>(this.URL, comision);
  }

}

