import { Component } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { CategoriaForm } from "../../../componentes/categorias/categoria-form/categoria-form";

@Component({
  selector: 'app-categorias-form-page',
  imports: [Header, CategoriaForm],
  templateUrl: './categorias-form-page.html',
  styleUrl: './categorias-form-page.css',
})
export class CategoriasFormPage {}
