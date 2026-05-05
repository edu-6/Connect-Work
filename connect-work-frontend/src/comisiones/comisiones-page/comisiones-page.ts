import { Component, OnInit, signal } from '@angular/core';
import { Comision } from '../../modelos/comision';
import { ComisionesService } from '../../servicios/comisionesService';
import { ComisionCard } from "../../componentes/comisiones/comision-card/comision-card";
import { ComisionForm } from "../../componentes/comisiones/comision-form/comision-form";
import { Header } from "../../shared/header/header";

@Component({
  selector: 'app-comisiones-page',
  imports: [ComisionCard, ComisionForm, Header],
  templateUrl: './comisiones-page.html',
  styleUrl: './comisiones-page.css',
})
export class ComisionesPage implements OnInit {
  
  comisionGlobal?: Comision;
  porcentajeActual = signal<number>(0);
  editando = signal<boolean>(false);

  constructor(private comisionesService: ComisionesService) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    this.comisionesService.obtenerComision().subscribe({
      next: (res) => {
        this.comisionGlobal = res;
        this.porcentajeActual.set(res.porcentajeComision);
      }
    });
  }

  onCambioExitoso() {
    this.editando.set(false);
    this.cargarDatos();
  }
}
