import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { HabilidadForm } from "../../../componentes/habilidades/habilidad-form/habilidad-form";
import { HabilidadesService } from "../../../servicios/habilidades-service";
import { Habilidad } from "../../../modelos/habilidades/habilidad";
import { ActivatedRoute } from '@angular/router';
import { ErrorBackend } from '../../../modelos/ErrorBackend';

@Component({
  selector: 'app-habilidades-editar-page',
  imports: [Header, HabilidadForm],
  templateUrl: './habilidades-editar-page.html',
  styleUrl: './habilidades-editar-page.css',
})
export class HabilidadesEditarPage implements OnInit {

  idHabilidad !: number;
  habilidad !: Habilidad;
  hayError = signal<boolean>(false);
  mensajeError !: string;

  habilidadEncontrada = signal<boolean>(false);

  constructor(private habilidadesService: HabilidadesService, private router: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.idHabilidad = this.router.snapshot.params['id'];
    this.habilidadesService.buscarParaEditar(this.idHabilidad).subscribe({
      next: (habilidad: Habilidad) => {
        this.habilidad = habilidad;
        this.habilidadEncontrada.set(true);
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }


    private registrarError(httpError: any) {
      this.hayError.set(true);
      const errorData: ErrorBackend = httpError.error;
      this.mensajeError = errorData.detalles;
    }
}
