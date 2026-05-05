import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable, tap } from "rxjs";
import { Injectable, signal } from "@angular/core";
import { PropuestaRequest } from "../modelos/propuestas/propuestaRequest";
import { BusquedaPropuesta } from "../modelos/propuestas/busquedaPropuesta";
import { PropuestaResponse } from "../modelos/propuestas/propuestasResponse";
import { observableToBeFn } from "rxjs/internal/testing/TestScheduler";

@Injectable({
  providedIn: 'root'
})


export class PropuestasService {

  constructor(private httpCliente: HttpClient) {
  }


  private constantesRest = new ConstantesRest();

  public crear(nuevo: PropuestaRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/propuestas', nuevo);
  }

  private buscarPorId(b: BusquedaPropuesta): Observable<PropuestaResponse> {
    return this.httpCliente.post<PropuestaResponse>(this.constantesRest.getApiURL() + 'api/propuestasBusquedas', b);
  }

  private buscarDeFreelancerEnProyecto(b: BusquedaPropuesta): Observable<PropuestaResponse> {
    return this.httpCliente.post<PropuestaResponse>(this.constantesRest.getApiURL() + 'api/propuestasBusquedas', b);
  }


  public buscarTodasEnProyecto(b: BusquedaPropuesta): Observable<PropuestaResponse[]> {
    return this.httpCliente.post<PropuestaResponse[]>(this.constantesRest.getApiURL() + 'api/propuestasBusquedas', b);
  }



  public rechazarPropuesta(id: number): Observable<void>{
    return this.httpCliente.delete<void>(this.constantesRest.getApiURL()+'api/propuestasRechazo/'+ id);
  }



  public eliminarPropuesta(id: number): Observable<void> {
    return this.httpCliente.delete<void>(this.constantesRest.getApiURL() + 'api/propuestas/' + id);
  }

  public buscarPropuestas(b: BusquedaPropuesta):Observable<PropuestaResponse[]|PropuestaResponse > {
    return this.httpCliente.post<PropuestaResponse[] | PropuestaResponse>(this.constantesRest.getApiURL() + 'api/propuestasBusquedas', b);
  }





}

