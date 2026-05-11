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
import { CambioEnSolicitud } from "../modelos/solicitudes/cambioEnSolicitud";
import { SolicitudResponse } from "../modelos/solicitudes/solicitudResponse";
import { SolicituRequest } from "../modelos/solicitudes/solicitudRequest";

@Injectable({
  providedIn: 'root'
})


export class SolicitudesService {

  constructor(private httpCliente: HttpClient) {
  }


  private constantesRest = new ConstantesRest();

  public mandarSolicitudHabilidad(nuevo: SolicituRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/solicitudes/habilidades', nuevo);
  }


  public mandarSolicitudCategoria(nuevo: SolicituRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/solicitudes/categorias', nuevo);
  }


  public hacerCambioEnSolicitudCategoria(cambio: CambioEnSolicitud): Observable<void> {
    return this.httpCliente.put<void>(this.constantesRest.getApiURL() + 'api/solicitudes/categorias', cambio);
  }


  public hacerCambioEnSolicitudHabilidad(cambio: CambioEnSolicitud): Observable<void> {
    return this.httpCliente.put<void>(this.constantesRest.getApiURL() + 'api/solicitudes/habilidades', cambio);
  }






  public buscarSolicitudesDeHabilidadPropias(cui: string): Observable<SolicitudResponse []> {
    return this.httpCliente.get<SolicitudResponse []>(this.constantesRest.getApiURL() + 'api/solicitudes/habilidades' + '/' + cui);
  }

  public buscarSolicitudesDeCategoriaPropias(cui: string): Observable<SolicitudResponse []> {
    return this.httpCliente.get<SolicitudResponse []>(this.constantesRest.getApiURL() + 'api/solicitudes/categorias' + '/' + cui);
  }

  public buscarSolicitudesDeCategorias(): Observable<SolicitudResponse []> {
    return this.httpCliente.get<SolicitudResponse []>(this.constantesRest.getApiURL() + 'api/listar/categorias');
  }

  public buscarSolicitudesDeHabilidades(): Observable<SolicitudResponse []> {
    return this.httpCliente.get<SolicitudResponse []>(this.constantesRest.getApiURL() + 'api/listar/habilidades');
  }




  
  






}

