import { Component } from '@angular/core';
import { UsuarioForm } from "../../../componentes/usuarios/usuario-form/usuario-form";
import { Header } from "../../../shared/header/header";

@Component({
  selector: 'app-usuario-from-page',
  imports: [UsuarioForm, Header],
  templateUrl: './usuario-from-page.html',
  styleUrl: './usuario-from-page.css',
})
export class UsuarioFromPage {}
