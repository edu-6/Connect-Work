import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable, tap } from "rxjs";
import { Injectable, signal } from "@angular/core";
import { PropuestaRequest } from "../modelos/propuestas/propuestaRequest";
import { BusquedaPropuesta } from "../modelos/propuestas/busquedaPropuesta";
import { PropuestaResponse } from "../modelos/propuestas/propuestasResponse";
import { observableToBeFn } from "rxjs/internal/testing/TestScheduler";
import { ContratoRequest } from "../modelos/contratos/contratoRequest";
import { ContratoResponse } from "../modelos/contratos/contratoResponse";

@Injectable({
  providedIn: 'root'
})


export class ContratosService {

  constructor(private httpCliente: HttpClient) {
  }


  private constantesRest = new ConstantesRest();

  public crear(nuevo: ContratoRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/contratos', nuevo);
  }

  public cancelarContrato(id: number): Observable<void>{
    return this.httpCliente.delete<void>(this.constantesRest.getApiURL()+'api/contratos/'+ id);
  }

  public buscarContratoDeProyecto(idProyecto: number): Observable<ContratoResponse> {
    return this.httpCliente.get<ContratoResponse>(this.constantesRest.getApiURL() + 'api/contratos/'+idProyecto);
  }







}

