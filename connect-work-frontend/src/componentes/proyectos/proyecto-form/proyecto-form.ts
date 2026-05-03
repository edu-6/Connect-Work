import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Categoria } from '../../../modelos/categorias/categoria';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { ProyectoRequest } from '../../../modelos/proyectos/proyectoRequest';
import { ProyectosService } from '../../../servicios/proyectosService';
import { CategoriasService } from '../../../servicios/categorias-service';

@Component({
  selector: 'app-proyecto-form',
  imports: [ReactiveFormsModule],
  templateUrl: './proyecto-form.html',
  styleUrl: './proyecto-form.css',
})
export class ProyectoForm implements OnInit {

  @Input() proyectoEnEdicion !: ProyectoRequest;

  formulario !: FormGroup;

  enEdicion = signal<boolean>(false);
  intentoEnviarlo = signal<boolean>(false);
  hayError = signal<boolean>(false);
  categorias = signal<Categoria[]>([]);

  mensajeError!: string;

  constructor(
    private formBuilder: FormBuilder,
    private proyectosService: ProyectosService,
    private categoriasService: CategoriasService,
    private router: Router
  ) {

  }

  ngOnInit(): void {
    this.enEdicion.set(this.proyectoEnEdicion != null);
    this.cargarCategorias();
    this.instanciarFormulario();

    if (this.enEdicion()) {
      this.formulario.patchValue(this.proyectoEnEdicion);
    }
  }

  private instanciarFormulario() {
    this.formulario = this.formBuilder.group({
      id: [null],
      nombre: ["", [Validators.required, Validators.maxLength(100)]],
      descripcion: ["", [Validators.required]],
      idCategoria: [null, [Validators.required]],
      presupuestoMaximo: [0, [Validators.required, Validators.min(1)]],
      cuiCliente: [localStorage.getItem('cui'), [Validators.required]],
      fechaEntregaDeseada: ["", [Validators.required]]
    });
  }

  public enviar() {
    this.hayError.set(false);
    this.intentoEnviarlo.set(true);

    if (this.formulario.invalid) return;

    const datos = this.formulario.value as ProyectoRequest;

    if (this.enEdicion()) {
      this.editar(datos);
    } else {
      this.guardarNuevo(datos);
    }
  }

  private guardarNuevo(nuevo: ProyectoRequest) {
    this.proyectosService.crear(nuevo).subscribe({
      next: () => {
        this.redirigirAProyectos();
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  private editar(nuevo: ProyectoRequest) {
    this.proyectosService.editar(nuevo).subscribe({
      next: () => {
        this.redirigirAProyectos();
      },
      error: (error: any) => {
        this.registrarError(error);
      }
    });
  }

  cargarCategorias() {
    this.categoriasService.buscarTodas().subscribe({
      next: (array: Categoria[]) => {
        this.categorias.set(array);
      },
      error: (httpError: any) => {
        this.registrarError(httpError);
      }
    });
  }

  private registrarError(httpError: any) {
    this.hayError.set(true);
    const errorData: ErrorBackend = httpError.error;
    this.mensajeError = errorData.detalles;
  }

  public redirigirAProyectos() {
    this.router.navigate(['/proyectos-crud-page']);
  }

  cancelar() {
    this.redirigirAProyectos();
  }

}
