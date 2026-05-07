import { Routes } from '@angular/router';
import { HomePage } from '../pages/home-page/home-page';
import { UsuarioFromPage } from '../pages/usuarios/usuario-from-page/usuario-from-page';
import { LoginForm } from '../componentes/login/login-form/login-form';
import { CompletacionPerfilFreelancerPage } from '../pages/usuarios/perfiles/completacion-perfil-freelancer-page/completacion-perfil-freelancer-page';
import { CompletacionPerfilClientePage } from '../pages/usuarios/perfiles/completacion-perfil-cliente-page/completacion-perfil-cliente-page';
import { HabilidadesPage } from '../pages/habilidades/habilidades-page/habilidades-page';
import { HabilidadesFormPage } from '../pages/habilidades/habilidades-form-page/habilidades-form-page';
import { HabilidadesEditarPage } from '../pages/habilidades/habilidades-editar-page/habilidades-editar-page';
import { CategoriasPage } from '../pages/categorias/categorias-page/categorias-page';
import { CategoriasFormPage } from '../pages/categorias/categorias-form-page/categorias-form-page';
import { CategoriasEditarPage } from '../pages/categorias/categorias-editar-page/categorias-editar-page';
import { ProyectosCrudPage } from '../pages/proyectos/proyectos-crud-page/proyectos-crud-page';
import { ProyectoFormPage } from '../pages/proyectos/proyecto-form-page/proyecto-form-page';
import { ProyectoEditarPage } from '../pages/proyectos/proyecto-editar-page/proyecto-editar-page';
import { ProyectoDetallePage } from '../pages/proyectos/proyecto-detalle-page/proyecto-detalle-page';
import { ProyectosBuscarPage } from '../pages/proyectos/proyectos-buscar-page/proyectos-buscar-page';
import { ComisionesPage } from '../comisiones/comisiones-page/comisiones-page';
import { CarteraPage } from '../pages/carteras/cartera-page/cartera-page';
import { ProyectosDelFreelancerPage } from '../pages/proyectos/proyectos-del-freelancer-page/proyectos-del-freelancer-page';

export const routes: Routes = [
    { path: "", component: HomePage },
    { path: "registro", component: UsuarioFromPage },
    { path: "login", component: LoginForm },

    { path: "completarPerfilFreelancer", component: CompletacionPerfilFreelancerPage },
    { path: "completarPerfilCliente", component: CompletacionPerfilClientePage },

    { path: "habilidades", component: HabilidadesPage },
    { path: "habilidades-form-page", component: HabilidadesFormPage },
    { path: "habilidades-editar-page/:id", component: HabilidadesEditarPage },


    { path: "categorias", component: CategoriasPage },
    { path: "categorias-form-page", component: CategoriasFormPage },
    { path: "categorias-editar-page/:nombre", component: CategoriasEditarPage },


    { path: "proyectos-crud-page", component: ProyectosCrudPage },
    { path: "proyectos-form-page", component: ProyectoFormPage },
    { path: "proyectos-editar-page/:id", component: ProyectoEditarPage },
    { path: "proyectos-detalle-page/:id", component: ProyectoDetallePage },
    { path: "proyectos-buscar-page", component: ProyectosBuscarPage },
    { path: "proyectos-activos-freelancer", component: ProyectosDelFreelancerPage },

    { path: "comisiones-page", component: ComisionesPage },


    { path: "cartera", component: CarteraPage },













];
