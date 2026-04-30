import { Routes } from '@angular/router';
import { HomePage } from '../pages/home-page/home-page';
import { UsuarioFromPage } from '../pages/usuarios/usuario-from-page/usuario-from-page';
import { LoginForm } from '../componentes/login/login-form/login-form';
import { CompletacionPerfilFreelancerPage } from '../pages/usuarios/perfiles/completacion-perfil-freelancer-page/completacion-perfil-freelancer-page';
import { CompletacionPerfilClientePage } from '../pages/usuarios/perfiles/completacion-perfil-cliente-page/completacion-perfil-cliente-page';
import { HabilidadesPage } from '../pages/habilidades/habilidades-page/habilidades-page';
import { HabilidadesFormPage } from '../pages/habilidades/habilidades-form-page/habilidades-form-page';
import { HabilidadesEditarPage } from '../pages/habilidades/habilidades-editar-page/habilidades-editar-page';

export const routes: Routes = [
    { path: "", component: HomePage },
    { path: "registro", component: UsuarioFromPage },
    { path: "login", component: LoginForm },

    { path: "completarPerfilFreelancer", component: CompletacionPerfilFreelancerPage },
    { path: "completarPerfilCliente", component: CompletacionPerfilClientePage },

    { path: "habilidades", component: HabilidadesPage },
    { path: "habilidades-form-page", component: HabilidadesFormPage },
    { path: "habilidades-editar-page/:id", component: HabilidadesEditarPage },












];
