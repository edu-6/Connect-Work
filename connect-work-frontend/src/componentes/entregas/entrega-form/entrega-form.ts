import { Component, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule, NgModel, FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { EntregaRequest } from '../../../modelos/entregas/entregaRequest';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { EntregasService } from '../../../servicios/entregasService.';

@Component({
  selector: 'app-entrega-form',
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './entrega-form.html',
  styleUrl: './entrega-form.css',
})
export class EntregaForm implements OnInit {

  @Input({ required: true }) idProyecto!: number;

  @Output()
  cerrarFormulario = new EventEmitter<void>();

  formulario!: FormGroup;

  intentoEnviarlo = signal(false);
  hayError = signal(false);
  mensajeError = "";

  archivos = signal<string[]>([]);

  constructor(
    private fb: FormBuilder,
    private entregasService: EntregasService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.formulario = this.fb.group({
      descripcion: ["", [Validators.required, Validators.maxLength(300)]],
      idProyecto: [this.idProyecto, [Validators.required]],
      archivoTemporal: [""]
    });
  }

  public agregarArchivo() {
    const archivo = this.formulario.value.archivoTemporal?.trim();

    if (!archivo) return;

    this.archivos.update(lista => [...lista, archivo]);

    this.formulario.patchValue({
      archivoTemporal: ""
    });
  }

  public eliminarArchivo(nombre: string) {
    const filtrados = this.archivos().filter(a => a !== nombre);
    this.archivos.set(filtrados);
  }

  public enviar() {
    this.intentoEnviarlo.set(true);
    this.hayError.set(false);

    if (this.formulario.invalid) return;

    const datos: EntregaRequest = {
      descripcion: this.formulario.value.descripcion,
      idProyecto: this.idProyecto,
      archivos: this.archivos()
    };

    this.entregasService.crear(datos).subscribe({
      next: () => {
        this.cerrarFormularioAction();
      },
      error: (err) => {
        this.hayError.set(true);
        const errorData: ErrorBackend = err.error;
        this.mensajeError = errorData.detalles;
      }
    });
  }

  public cerrarFormularioAction() {
    this.cerrarFormulario.emit();
  }
}
