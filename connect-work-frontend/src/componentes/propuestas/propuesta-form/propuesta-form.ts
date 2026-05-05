import { Component, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { PropuestaRequest } from '../../../modelos/propuestas/propuestaRequest';
import { PropuestasService } from '../../../servicios/propuestasService';
import { Router } from '@angular/router';
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';

@Component({
  selector: 'app-propuesta-form',
  imports: [ReactiveFormsModule],
  templateUrl: './propuesta-form.html',
  styleUrl: './propuesta-form.css',
})
export class PropuestaForm implements OnInit {

  @Input({ required: true }) idProyecto!: number;

  @Output()
  cerrarFormulario = new EventEmitter<void>();

  formulario!: FormGroup;
  intentoEnviarlo = signal(false);
  hayError = signal(false);
  mensajeError = "";

  constructor(
    private fb: FormBuilder,
    private propuestasService: PropuestasService,
    private router: Router,
    private servicioAutenticacion: AutenticacionServicio
  ) {
    
   }

  ngOnInit(): void {
    this.formulario = this.fb.group({
      cuiFreelancer: [localStorage.getItem('cui'), [Validators.required]],
      idProyecto: [this.idProyecto, [Validators.required]],
      cartaPresentacion: ["", [Validators.required, Validators.maxLength(500)]],
      presupuestoOfertado: [0, [Validators.required, Validators.min(1)]],
      plazoEntrega: [1, [Validators.required, Validators.min(1)]]
    });
  }

  public enviar() {
    this.intentoEnviarlo.set(true);
    this.hayError.set(false);
    if (this.formulario.invalid) return;

    const datos = this.formulario.value as PropuestaRequest;

    this.propuestasService.crear(datos).subscribe({
      next: () => {
        this.cerrarFormularioAction();
        this.router.navigate(['/proyectos-detalle-page', this.idProyecto]);
      },
      error: (err) => {
        this.hayError.set(true);
        const errorData: ErrorBackend = err.error;
        this.mensajeError = errorData.detalles;
      }
    });
  }

  public cerrarFormularioAction(){
    this.cerrarFormulario.emit();
  }
}
