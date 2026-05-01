import { Component, OnInit, signal } from '@angular/core';
import { Categoria } from '../../../modelos/categorias/categoria';
import { CategoriasService } from '../../../servicios/categorias-service';
import { ActivatedRoute } from '@angular/router';
import { CategoriaResponse } from '../../../modelos/categorias/categoriaResponse';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { CategoriaForm } from "../../../componentes/categorias/categoria-form/categoria-form";
import { Header } from "../../../shared/header/header";

@Component({
  selector: 'app-categorias-editar-page',
  imports: [CategoriaForm, Header],
  templateUrl: './categorias-editar-page.html',
  styleUrl: './categorias-editar-page.css',
})
export class CategoriasEditarPage implements OnInit {
  nombreCategoria !: string;
  
  categoria !: CategoriaResponse;
  hayError = signal<boolean>(false);
  mensajeError !: string;

  categoriaEncontrada = signal<boolean>(false);

  constructor(private categoriasService: CategoriasService, private router: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.nombreCategoria = this.router.snapshot.params['nombre'];
    this.categoriasService.buscarParaEditar(this.nombreCategoria).subscribe({
      next: (categoria: CategoriaResponse) => {
        this.categoria =categoria;
        this.categoriaEncontrada.set(true);
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
