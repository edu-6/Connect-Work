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
import { ReporteRequest } from "../modelos/reporteRequest";
import { ReporteHistorialProyecto } from "../modelos/reportes/reporteHistorialProyectos";

@Injectable({
  providedIn: 'root'
})


export class ReportesServicio {

  constructor(private httpCliente: HttpClient) {
  }


  private constantesRest = new ConstantesRest();

  public obtenerReporteHistorialProyectos(request: ReporteRequest): Observable<ReporteHistorialProyecto[]> {
    return this.httpCliente.post<ReporteHistorialProyecto []>(this.constantesRest.getApiURL() + 'api/reportes', request);
  }
}

