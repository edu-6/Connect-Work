import { Component, EventEmitter, OnInit, Output, signal } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { CarteraDigital } from '../../../modelos/recargos/carteraDigital';
import { CarteraService } from '../../../servicios/recargosService';

@Component({
  selector: 'app-cartera-form',
  imports: [ReactiveFormsModule],
  templateUrl: './cartera-form.html',
  styleUrl: './cartera-form.css',
})
export class CarteraForm implements OnInit {

  @Output()
  cerrarFormulario = new EventEmitter<void>();

  formulario!: FormGroup;
  intentoEnviarlo = signal(false);
  hayError = signal(false);
  mensajeError = "";

  constructor(
    private fb: FormBuilder,
    private carteraService: CarteraService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.formulario = this.fb.group({
      cuiCliente: [localStorage.getItem('cui'), [Validators.required]],
      saldo: [0, [Validators.required, Validators.min(1)]]
    });
  }

  public enviar() {
    this.intentoEnviarlo.set(true);
    this.hayError.set(false);
    
    if (this.formulario.invalid) return;

    const datos = this.formulario.value as CarteraDigital;

    this.carteraService.recargar(datos).subscribe({
      next: () => {
        this.cerrarFormularioAction();
        this.router.navigate(['/mi-cartera']); 
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
