import { Component } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { HabilidadForm } from "../../../componentes/habilidades/habilidad-form/habilidad-form";

@Component({
  selector: 'app-habilidades-form-page',
  imports: [Header, HabilidadForm],
  templateUrl: './habilidades-form-page.html',
  styleUrl: './habilidades-form-page.css',
})
export class HabilidadesFormPage {}
