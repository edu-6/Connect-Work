import { Component, OnInit, signal } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { Habilidad } from '../../../modelos/habilidades/habilidad';
import { HabilidadesService } from '../../../servicios/habilidades-service';
import { Categoria } from '../../../modelos/categorias/categoria';
import { CategoriasService } from '../../../servicios/categorias-service';
import { CategoriaResponse } from '../../../modelos/categorias/categoriaResponse';
import { Header } from "../../../shared/header/header";
import { RouterLink } from '@angular/router';
import { CategoriaCard } from "../../../componentes/categorias/categoria-card/categoria-card";

@Component({
  selector: 'app-categorias-page',
  imports: [ReactiveFormsModule, Header, RouterLink, CategoriaCard],
  templateUrl: './categorias-page.html',
  styleUrl: './categorias-page.css',
})
export class CategoriasPage implements OnInit {

  public barraBusqueda !: FormGroup;

   mensajeError !: string;
   hayError = signal<boolean>(false);
   buscandoTodos = signal<boolean>(false);
   buscandoUno = signal <boolean>(false); 

   public categoriaEncontrada = signal<Categoria | null>(null);

   constructor(private formBuilder: FormBuilder, private categoriasService: CategoriasService){

   }
   
   categorias = signal<Categoria []> ([]);

  ngOnInit(): void {
    this.buscarTodos();

    this.barraBusqueda = this.formBuilder.group(
      {
        busqueda : ["", [Validators.required]]
      }
    );
  }

  public buscarTodos(){
    this.categoriasService.buscarTodas().subscribe({
      next: (todos: Categoria []) =>{
        this.categorias.set(todos);
        this.buscandoUno.set(false);
        this.buscandoTodos.set(true);
      },
      error: (errorHtttp: any)=>{
       this.registrarError(errorHtttp);
      }
    });
  }

  public buscarCategoria(){
    if(!this.barraBusqueda.valid) return;
    this.buscandoTodos.set(false);
    this.categoriasService.buscarCategoria(this.barraBusqueda.get("busqueda")?.value).subscribe({
      next: (resp: CategoriaResponse ) =>{
        this.categoriaEncontrada.set(resp.categoria);
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
