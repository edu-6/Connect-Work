import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReportesServicio } from '../../../servicios/reportesServicio';
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ReporteRequest } from '../../../modelos/reporteRequest';
import { Header } from "../../../shared/header/header";
import { ReporteHistorialProyecto } from '../../../modelos/reportes/reporteHistorialProyectos';
import { HistorialProyectosComponent } from "../../../componentes/reportes/historial-proyectos-component/historial-proyectos-component";
import { ReporteRecarga } from '../../../modelos/reportes/reporteRecargas';
import { HistorialRecargasComponent } from "../../../componentes/reportes/historial-recargas-component/historial-recargas-component";
import { ReporteGastoCategoria } from '../../../modelos/reportes/reporteGastoCategoria';
import { ReporteGastoCategoriaComponent } from "../../../componentes/reportes/reporte-gasto-categoria-component/reporte-gasto-categoria-component";

@Component({
  selector: 'app-reportes-page',
  imports: [ReactiveFormsModule, Header, HistorialProyectosComponent, HistorialRecargasComponent, ReporteGastoCategoriaComponent],
  templateUrl: './reportes-page.html',
  styleUrl: './reportes-page.css',
})
export class ReportesPage implements OnInit {

  formulario!: FormGroup;
  mensajeError!: string;


  // Signals de estado
  hayError = signal<boolean>(false);
  reporteEncontrado = signal<boolean>(false);
  reporteActual = signal<string | null>(null);


  public listaReportes: string[] = [];

  public readonly REPORTES_CLIENTE: string[] = [
    "CLIENTE_HISTORIAL_PROYECTOS",
    "CLIENTE_HISTORIAL_RECARGAS",
    "CLIENTE_GASTO_POR_CATEGORIA"
  ];


  public readonly REPORTES_FREELANCER: string[] = [
    "FREELANCER_SALDO_ACTUAL",
    "FREELANCER_CONTRATOS_COMPLETADOS",
    "FREELANCER_TOP_CATEGORIAS",
    "FREELANCER_PROPUESTAS_ENVIADAS"
  ];


  public readonly REPORTES_ADMIN: string[] = [
    "ADMIN_HISTORIAL_COMISIONES",
    "ADMIN_TOP_FREELANCERS_INGRESOS",
    "ADMIN_TOP_CATEGORIAS_ACTIVIDAD",
    "ADMIN_TOTAL_INGRESOS_PLATAFORMA"
  ];

  private readonly REPORTES_CON_FECHA: string[] = [
    "CLIENTE_HISTORIAL_PROYECTOS",
    "CLIENTE_GASTO_POR_CATEGORIA",
    "ADMIN_HISTORIAL_COMISIONES"
  ];


  public reporteHostorialProyectos = signal<ReporteHistorialProyecto[] | null>(null);
  public reporteHistorialRecargas = signal<ReporteRecarga[] | null>(null);
  public reporteGastosPorCategoria = signal<ReporteGastoCategoria[] | null>(null);


  constructor(private formBuiler: FormBuilder,
    private reportesService: ReportesServicio,
    private autenticacionService: AutenticacionServicio
  ) { }

  ngOnInit(): void {
    this.asignarArraySegunRol();
  }
  public asignarArraySegunRol() {
    if (this.autenticacionService.esAdmin()) {
      this.listaReportes = this.REPORTES_ADMIN;
    }
    if (this.autenticacionService.esCliente()) {
      this.listaReportes = this.REPORTES_CLIENTE;
    }

    if (this.autenticacionService.esFreelancer()) {
      this.listaReportes = this.REPORTES_FREELANCER;
    }
    this.instanciarFormulario();
  }

  instanciarFormulario() {
    this.formulario = this.formBuiler.group({
      tipoReporte: [this.listaReportes[0] || '', Validators.required],
      fechaInicio: [null],
      fechaFinal: [null],
      cuiUsuario: localStorage.getItem('cui')
    });
  }

  mostrarFiltroFechas(): boolean {
    const tipo = this.formulario.get('tipoReporte')?.value;
    return this.REPORTES_CON_FECHA.includes(tipo);
  }

  resetearFormulario(tipoReporte: string) {
    this.formulario.patchValue({
      tipoReporte: tipoReporte,
      fechaInicio: null,
      fechaFinal: null,
      cuiUsuario: localStorage.getItem('cui')
    });
    this.reporteActual.set(null);
  }

  public reporteActivo(tipo: string): boolean {
    return tipo === this.reporteActual();
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData?.detalles || "Error desconocido en el servidor";
  }

  public generarReporte(tipoReporte: string) {
    this.hayError.set(false);
    this.reporteEncontrado.set(false);
    this.reporteActual.set(tipoReporte);
    const reporteRequest = this.formulario.value as ReporteRequest;

    switch (tipoReporte) {
      case "CLIENTE_HISTORIAL_PROYECTOS":
        this.reporteHostorialProyectos.set(null);
        this.generarHistorialProyectos(reporteRequest);
        break;
      case "CLIENTE_HISTORIAL_RECARGAS":
        this.reporteHistorialRecargas.set(null);
        this.generarHistorialRecargas(reporteRequest);
        break;
      case "CLIENTE_GASTO_POR_CATEGORIA":
        this.reporteGastosPorCategoria.set(null);
        this.generarReporteGastosPorCategoria(reporteRequest);
        break;


    }

  }

  public generarHistorialProyectos(request: ReporteRequest) {
    console.log(request);
    this.reportesService.obtenerReporteHistorialProyectos(request).subscribe({

      next: (resp: ReporteHistorialProyecto[]) => {
        this.reporteHostorialProyectos.set(resp);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  public generarHistorialRecargas(request: ReporteRequest) {
    this.reportesService.obtenerReporteRecargas(request).subscribe({
      next: (data) => this.reporteHistorialRecargas.set(data),
      error: (err) => this.registrarError(err)
    });
  }

  public generarReporteGastosPorCategoria(request: ReporteRequest) {
    this.reportesService.obtenerGastosPorCategoria(request).subscribe({
      next: (data) => this.reporteGastosPorCategoria.set(data),
      error: (err) => this.registrarError(err)
    });
  }

}
