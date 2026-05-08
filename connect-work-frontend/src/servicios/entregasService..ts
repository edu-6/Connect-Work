import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable, tap } from "rxjs";
import { Injectable, signal } from "@angular/core";
import { PropuestaRequest } from "../modelos/propuestas/propuestaRequest";
import { BusquedaPropuesta } from "../modelos/propuestas/busquedaPropuesta";
import { PropuestaResponse } from "../modelos/propuestas/propuestasResponse";
import { observableToBeFn } from "rxjs/internal/testing/TestScheduler";
import { EntregaRequest } from "../modelos/entregas/entregaRequest";
import { EntregaResponse } from "../modelos/entregas/entregaResponse";
import { RechazoEntrega } from "../modelos/rechazoPrpuestaProyecto";

@Injectable({
  providedIn: 'root'
})


export class EntregasService {

  constructor(private httpCliente: HttpClient) {
  }


  private constantesRest = new ConstantesRest();

  public crear(nueva: EntregaRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/entregas', nueva);
  }


  public rechazarEntrega(rechazo: RechazoEntrega): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/rechazosEntregas',rechazo);
  }
  

  public buscarEntregaSinRevisar(idProyecto: number): Observable<EntregaResponse> {
    return this.httpCliente.get<EntregaResponse>(this.constantesRest.getApiURL() + 'api/entregas/' + idProyecto);
  }

   public buscarHistorialEntregaas(idProyecto: number): Observable<EntregaResponse []> {
    return this.httpCliente.get<EntregaResponse []>(this.constantesRest.getApiURL() + 'api/entregas-historial/' + idProyecto);
  }
  

  




}

