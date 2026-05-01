import { Component, Input, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { HabilidadesService } from '../../../servicios/habilidades-service';
import { CategoriasService } from '../../../servicios/categorias-service';
import { Habilidad } from '../../../modelos/habilidades/habilidad';
import { ErrorBackend } from '../../../modelos/ErrorBackend';

@Component({
  selector: 'app-habilidad-categoria-component',
  imports: [FormsModule],
  templateUrl: './habilidad-categoria-component.html',
  styleUrl: './habilidad-categoria-component.css',
})
export class HabilidadCategoriaComponent implements OnInit {

  constructor(
    private habilidadesService: HabilidadesService,
    private categoriasService: CategoriasService
  ) {
    
   }

  @Input()
  idCategoria !: number;

  hayError = signal<boolean>(false);
  mensajeError!: string;



  habilidadesDB = signal<Habilidad[]>([]);

  habilidadesAntiguas = signal<Habilidad[]>([]);
  habilidadesNuevas = signal<Habilidad[]>([]);

  habilidadesDisponibles = signal<Habilidad[]>([]);


  idSeleccionado: number | null = null;

  ngOnInit(): void {
    this.cargarTodo();
  }



  private cargarTodo() {
    const antiguas$ = this.idCategoria ? this.habilidadesService.buscarHabilidadesEnCategoria(this.idCategoria) : of<Habilidad[]>([]);

    forkJoin({
      todas: this.habilidadesService.buscarActivas(),
      antiguas: antiguas$
    }).subscribe({
      next: ({ todas, antiguas }) => {
        this.habilidadesDB.set(todas);
        this.habilidadesAntiguas.set(antiguas);
        this.habilidadesDisponibles.set([...todas]);
        this.quitarHabilidadesAntiguasDeDisponibles();
      },
      error: (errorHttp: any) => {
        this.registrarError(errorHttp);
      }
    });
  }



  private quitarHabilidadesAntiguasDeDisponibles() {
    const idsAntiguos = new Set(this.habilidadesAntiguas().map(h => h.id));
    this.habilidadesDisponibles.update(lista =>
      lista.filter(h => !idsAntiguos.has(h.id))
    );
  }


  public cargarHabilidades() {
    this.habilidadesService.buscarTodas().subscribe({
      next: (todas: Habilidad[]) => {
        this.habilidadesDB.set(todas);
        this.habilidadesDisponibles.set([...todas]);
      },
      error: (errorHttp: any) => {
        this.registrarError(errorHttp);
      }
    });
  }

  public agregar() {
    if (this.idSeleccionado === null) return;

    const habilidad = this.habilidadesDisponibles().find(h => h.id === Number(this.idSeleccionado));
    if (!habilidad) return;

    const yaExiste = this.habilidadesNuevas().some(h => h.id === habilidad.id);
    if (yaExiste) return;


    this.habilidadesNuevas.update(lista => [...lista, habilidad]);
    this.habilidadesDisponibles.update(lista => lista.filter(h => h.id !== habilidad.id));


    this.idSeleccionado = null;
  }


  public eliminar(habilidad: Habilidad) {
    const esAntigua = this.habilidadesAntiguas().some(h => h.id === habilidad.id);

    if (esAntigua && this.idCategoria) {
      this.habilidadesService.eliminarHabilidadEnCategoria(this.idCategoria, habilidad.id).subscribe({
        next: () => {
          this.habilidadesAntiguas.update(lista => lista.filter(h => h.id !== habilidad.id));
          this.habilidadesNuevas.update(lista => lista.filter(h => h.id !== habilidad.id));
          this.habilidadesDisponibles.update(lista => [...lista, habilidad]);
        },
        error: (errorHttp: any) => {
          this.registrarError(errorHttp);
        }
      });
    } else {
      this.habilidadesNuevas.update(lista => lista.filter(h => h.id !== habilidad.id));
      this.habilidadesDisponibles.update(lista => [...lista, habilidad]);
    }
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }
}
