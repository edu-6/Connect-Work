import { Component, OnInit, signal } from '@angular/core';
import { CarteraService } from '../../../servicios/recargosService';
import { Header } from "../../../shared/header/header";
import { CarteraCard } from "../../../componentes/cartera/cartera-card/cartera-card";
import { CarteraForm } from "../../../componentes/cartera/cartera-form/cartera-form";

@Component({
  selector: 'app-cartera-page',
  imports: [Header, CarteraCard, CarteraForm],
  templateUrl: './cartera-page.html',
  styleUrl: './cartera-page.css',
})
export class CarteraPage implements OnInit {
  
  saldoActual = signal<number>(0);
  editando = signal<boolean>(false);

  constructor(private carteraService: CarteraService) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    const cui = localStorage.getItem('cui');
    if (cui) {
      this.carteraService.buscarCartera(cui).subscribe({
        next: (res) => {
          this.saldoActual.set(res.saldo);
        }
      });
    }
  }

  onRecargaFinalizada() {
    this.editando.set(false);
    this.cargarDatos();
  }
}
