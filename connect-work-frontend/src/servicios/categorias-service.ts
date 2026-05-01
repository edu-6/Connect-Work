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

@Injectable({
  providedIn: 'root'
})


export class CategoriasService {

  constructor(private httpCliente: HttpClient) {
  }


  private constantesRest = new ConstantesRest();

  public crear(nuevo: CategoriaRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/categorias', nuevo);
  }

  public editar(edicion: CategoriaRequest): Observable<void> {
    return this.httpCliente.put<void>(this.constantesRest.getApiURL() + 'api/categorias', edicion);
  }


  public buscarParaEditar(nombre: string): Observable<CategoriaResponse> {
    return this.httpCliente.get<CategoriaResponse>(this.constantesRest.getApiURL() + 'api/categorias' + '/' + nombre);
  }

  public buscarCategoria(id: number): Observable<CategoriaResponse> {
    return this.httpCliente.get<CategoriaResponse>(this.constantesRest.getApiURL() + 'api/categorias' + '/' + id);
  }


  public buscarTodas(): Observable<Categoria[]> {
    return this.httpCliente.get<Categoria[]>(this.constantesRest.getApiURL() + 'api/categorias/todas');
  }


  public buscarActivas(): Observable<Categoria[]> {
    return this.httpCliente.get<Categoria[]>(this.constantesRest.getApiURL() + 'api/categorias/activas');
  }

  






}

