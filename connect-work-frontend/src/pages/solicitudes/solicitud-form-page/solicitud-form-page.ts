import { Component } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { SolicitudesForm } from "../../../componentes/solicitudes/solicitudes-form/solicitudes-form";

@Component({
  selector: 'app-solicitud-form-page',
  imports: [Header, SolicitudesForm],
  templateUrl: './solicitud-form-page.html',
  styleUrl: './solicitud-form-page.css',
})
export class SolicitudFormPage {}
