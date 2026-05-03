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
import { ProyectoRequest } from "../modelos/proyectos/proyectoRequest";
import { BusquedaProyecto } from "../modelos/proyectos/busquedaProyecto";
import { ProyectoResponse } from "../modelos/proyectos/proyectoResponse";

@Injectable({
  providedIn: 'root'
})


export class ProyectosService {

  private constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) { }

  public crear(nuevo: ProyectoRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/proyectos', nuevo);
  }

  public editar(edicion: ProyectoRequest): Observable<void> {
    return this.httpCliente.put<void>(this.constantesRest.getApiURL() + 'api/proyectos', edicion);
  }


  public buscar(busqueda: BusquedaProyecto): Observable<ProyectoResponse[]>{
    return this.httpCliente.post<ProyectoResponse[]>(this.constantesRest.getApiURL()+'api/busquedasProyectos', busqueda);
  }

  






}

