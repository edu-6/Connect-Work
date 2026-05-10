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
import { EntregaAceptacion } from "../modelos/entregas/entregaAceptacion";
import { CalificacionResponse } from "../modelos/calificaciones/calificacionResponse";
import { CalificacionRequest } from "../modelos/calificaciones/calificacionRequest";

@Injectable({
  providedIn: 'root'
})


export class CalificaconesService {

  constructor(private httpCliente: HttpClient) {
  }


  private constantesRest = new ConstantesRest();

  public crear(nueva: CalificacionRequest): Observable<void> {
    return this.httpCliente.post<void>(this.constantesRest.getApiURL() + 'api/calificaciones', nueva);
  }
  

  




}

