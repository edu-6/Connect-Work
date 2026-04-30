import { Component, OnInit, signal } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { Habilidad } from '../../../modelos/habilidades/habilidad';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HabilidadesService } from '../../../servicios/habilidades-service';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { Router, RouterLink } from '@angular/router';
import { HabilidadCard } from "../../../componentes/habilidades/habilidad-card/habilidad-card";

@Component({
  selector: 'app-habilidades-page',
  imports: [Header, ReactiveFormsModule, RouterLink, HabilidadCard],
  templateUrl: './habilidades-page.html',
  styleUrl: './habilidades-page.css',
})
export class HabilidadesPage implements OnInit {


  public barraBusqueda !: FormGroup;

   mensajeError !: string;

   // boleanos
   hayError = signal<boolean>(false);
   buscandoTodos = signal<boolean>(false);
   buscandoUno = signal <boolean>(false);

   public habilidadEncontrada = signal<Habilidad | null>(null);

   constructor(private formBuilder: FormBuilder, private habilidadesService: HabilidadesService){

   }
   
   habilidades = signal<Habilidad []> ([]);

  ngOnInit(): void {
    this.buscarTodos();

    this.barraBusqueda = this.formBuilder.group(
      {
        busqueda : ["", [Validators.required]]
      }
    );
  }

  public buscarTodos(){
    this.habilidadesService.buscarTodas().subscribe({
      next: (todos: Habilidad []) =>{
        this.habilidades.set(todos);
        this.buscandoUno.set(false);
        this.buscandoTodos.set(true);
      },
      error: (errorHtttp: any)=>{
       this.registrarError(errorHtttp);
      }
    });
  }

  public buscarHabilidad(){
    if(!this.barraBusqueda.valid) return;
    this.buscandoTodos.set(false);
    this.habilidadesService.buscarHabilidad(this.barraBusqueda.get("busqueda")?.value).subscribe({
      next: (resp: Habilidad ) =>{
        this.habilidadEncontrada.set(resp);
        this.buscandoUno.set(true);
      },
      error: (errorHtttp: any)=>{
        this.registrarError(errorHtttp);
      }
    });
  }

  private registrarError(httpError: any) {
      this.hayError.set(true);
      const errorData: ErrorBackend = httpError.error;
      this.mensajeError = errorData.detalles;
    }
}
