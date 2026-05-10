import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { UsuarioPlataformaRequest } from "../modelos/usuarios/usuarioRequest";
import { AdminRequest } from "../modelos/adminRequest";

@Injectable({
  providedIn: 'root'
})


export class usuariosService {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {
  }


  public crear(nuevo: UsuarioPlataformaRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/usuarios', nuevo);
  }

  public crearAdmin(nuevo: AdminRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/admins', nuevo);
  }




  
  public cambiarEstadoActivo(nickname: string): Observable<void> {
    return this.httpCliente.get<void>(this.constantesRest.getApiURL() + 'api/usuarios/'+nickname);
  }

  /*


  public buscarParaEditar(id: string): Observable<ClienteRequest> {
    return this.httpCliente.get<ClienteRequest>(this.constantesRest.getApiURL() + 'api/clientes' + '/' + id);
  }


  public buscarParaMostrar(id: string): Observable<ClienteResponse> {
    return this.httpCliente.get<ClienteResponse>( `${this.constantesRest.getApiURL()}api/clientes/${id}`);
  }

  public editarCliente( edicion: ClienteRequest): Observable<void>{
    return this.httpCliente.put<void>(this.constantesRest.getApiURL()+'api/clientes',edicion);
  }


    public eliminar(id: string): Observable<void>{
    return this.httpCliente.delete<void>(`${this.constantesRest.getApiURL()}api/clientes/${id}`);
  }*/



}