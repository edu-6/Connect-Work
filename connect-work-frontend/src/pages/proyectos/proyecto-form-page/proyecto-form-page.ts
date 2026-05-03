import { Component } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { ProyectoForm } from "../../../componentes/proyectos/proyecto-form/proyecto-form";

@Component({
  selector: 'app-proyecto-form-page',
  imports: [Header, ProyectoForm],
  templateUrl: './proyecto-form-page.html',
  styleUrl: './proyecto-form-page.css',
})
export class ProyectoFormPage {}
