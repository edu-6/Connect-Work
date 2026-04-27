import { Routes } from '@angular/router';
import { HomePage } from '../pages/home-page/home-page';
import { UsuarioFromPage } from '../pages/usuarios/usuario-from-page/usuario-from-page';
import { LoginForm } from '../componentes/login/login-form/login-form';

export const routes: Routes = [
 {path: "", component: HomePage},
 {path: "registro", component: UsuarioFromPage},
 {path: "login", component: LoginForm}









];
