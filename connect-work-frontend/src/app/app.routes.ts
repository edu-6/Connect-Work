import { Routes } from '@angular/router';
import { HomePage } from '../pages/home-page/home-page';
import { UsuarioFromPage } from '../pages/usuarios/usuario-from-page/usuario-from-page';
import { LoginForm } from '../componentes/login/login-form/login-form';
import { CompletacionPerfilFreelancerPage } from '../pages/usuarios/perfiles/completacion-perfil-freelancer-page/completacion-perfil-freelancer-page';
import { CompletacionPerfilClientePage } from '../pages/usuarios/perfiles/completacion-perfil-cliente-page/completacion-perfil-cliente-page';

export const routes: Routes = [
    { path: "", component: HomePage },
    { path: "registro", component: UsuarioFromPage },
    { path: "login", component: LoginForm },
    { path: "completarPerfilFreelancer", component: CompletacionPerfilFreelancerPage },
    { path: "completarPerfilCliente", component: CompletacionPerfilClientePage }












];
