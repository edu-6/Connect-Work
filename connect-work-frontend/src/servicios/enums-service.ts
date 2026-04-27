import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Rol } from "../modelos/rol";
import { Observable } from "rxjs";
import { ConstantesRest } from "./restConstantes";

@Injectable({
  providedIn: 'root'
})


export class EnumsService {
  constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient) {
  }

  public getRoles(): Observable<Rol[]> {
    return this.httpCliente.get<Rol[]>(this.constantesRest.getApiURL() + 'api/enums/roles');
  }

    /*
  public getPaises(): Observable<Pais[]> {
    return this.httpCliente.get<Pais[]>(this.constantesRest.getApiURL() + 'api/enums/paises');
  }

  public getNacionalidades(): Observable<Nacionalidad[]> {
    return this.httpCliente.get<Nacionalidad[]>(this.constantesRest.getApiURL() + 'api/enums/nacionalidades');
  }

  public getMetodosPago(): Observable<MetodoPago[]> {
    return this.httpCliente.get<MetodoPago[]>(this.constantesRest.getApiURL() + 'api/enums/metodos-pago');
  }

  public getTiposServicio(): Observable<TipoServicio[]> {
    return this.httpCliente.get<TipoServicio[]>(this.constantesRest.getApiURL() + 'api/enums/tipos-servicio');
  }

*/
}