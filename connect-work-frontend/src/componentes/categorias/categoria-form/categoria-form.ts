import { Component, Input, OnInit, signal, ViewChild, viewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { Habilidad } from '../../../modelos/habilidades/habilidad';
import { CategoriasService } from '../../../servicios/categorias-service';
import { Categoria } from '../../../modelos/categorias/categoria';
import { HabilidadCategoriaComponent } from "../habilidad-categoria-component/habilidad-categoria-component";
import { HabilidadCategoria } from '../../../modelos/categorias/habilidad-categoria';
import { CategoriaRequest } from '../../../modelos/categorias/categoriaRequest';

@Component({
  selector: 'app-categoria-form',
  imports: [ReactiveFormsModule, RouterLink, HabilidadCategoriaComponent],
  templateUrl: './categoria-form.html',
  styleUrl: './categoria-form.css',
})
export class CategoriaForm implements OnInit {

  enEdicion = signal<boolean>(false);
  formulario!: FormGroup;

  mensajeEdicion: String = "Editar categoría";
  mensajeCreacion: String = "Crear categoría";

  mensajeError!: string;

  creadoConExito = signal<boolean>(false);
  editadoConExito = signal<boolean>(false);
  intentoEnviarlo = signal<boolean>(false);
  hayError = signal<boolean>(false);



  @ViewChild('formularioHabilidades') formularioHabilidades!: HabilidadCategoriaComponent;

  constructor(
    private formBuilder: FormBuilder,
    private categoriasService: CategoriasService,
    private router: Router
  ) { }

  @Input()
  categoriaEnEdicion!: Categoria;

  ngOnInit(): void {
    this.enEdicion.set(this.categoriaEnEdicion != null);
    
    this.instanciarFormulario();

    if (this.enEdicion()) {
      this.formulario.patchValue(this.categoriaEnEdicion);
    }
  }

  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      id: [null],
      nombre: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(30)]],
      activa: [true, Validators.required]
    });
  }

  public enviar() {
    this.reiniciarBooleanos();
    this.intentoEnviarlo.set(true);

    if (!this.formulario.valid) return;

    if (this.enEdicion()) {
      this.editar();
    } else {
      this.crear();
    }
  }


  private crearCategoriaRequest(): CategoriaRequest {
    const nuevo = this.formulario.value as Categoria;
    const habilidadesParaEnviar: HabilidadCategoria[] = this.formularioHabilidades.habilidadesNuevas()
      .map(h => ({
        idCategoria: nuevo.id,
        idHabilidad: h.id
      }));

    return {
      categoria: nuevo,
      habilidades: habilidadesParaEnviar
    };
  }

  private crear() {
    let nuevo = this.crearCategoriaRequest();
    this.categoriasService.crear(nuevo).subscribe({
      next: () => {
        this.router.navigate(['/categorias']);
      },
      error: (errorHttp: any) => {
        this.registrarError(errorHttp);
      }
    });
  }

  private editar() {
    let nuevo = this.crearCategoriaRequest();
    this.categoriasService.editar(nuevo).subscribe({
      next: () => {
        this.editadoConExito.set(true);
        this.router.navigate(['/categorias']);
      },
      error: (errorHttp: any) => {
        this.editadoConExito.set(false);
        this.registrarError(errorHttp);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }

  private reiniciarBooleanos() {
    this.editadoConExito.set(false);
    this.hayError.set(false);
    this.intentoEnviarlo.set(false);
  }
}
