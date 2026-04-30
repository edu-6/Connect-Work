import { HttpClient } from "@angular/common/http";
import { ConstantesRest } from "./restConstantes";
import { Observable, tap } from "rxjs";
import { Injectable, signal } from "@angular/core";
import { Router } from "@angular/router";
import { UsuarioLoginRequest } from "../modelos/login/usuarioLoginRequest";
import { UsuarioLoginResponse } from "../modelos/login/usuarioLoginResponse";

@Injectable({
  providedIn: 'root'
})


export class AutenticacionServicio {
  private constantesRest = new ConstantesRest();

  constructor(private httpCliente: HttpClient, private router: Router) {
    
  }

  isAuthenticated = signal<boolean>(!!localStorage.getItem('auth_token'));

  login(usuarioLogin: UsuarioLoginRequest): Observable<UsuarioLoginResponse> {
    return this.httpCliente.post<UsuarioLoginResponse>(
      this.constantesRest.getApiURL() + '/api/login',
      usuarioLogin
    ).pipe(
      tap((res) => {
        localStorage.setItem('auth_token', res.token);
        localStorage.setItem('nombre', res.nombre);
        localStorage.setItem('rol', res.rol);
        localStorage.setItem('nickname', res.nickname);

        if (res.perfilCompletado != null) {
          let estadoPerfil = "";
          if (res.perfilCompletado) {
            estadoPerfil = "completado";
          } else {
            estadoPerfil = "incompleto";
          }
          localStorage.setItem('estadoPerfil', estadoPerfil);
        }

        if (res.cui) {
          localStorage.setItem('cui', res.cui);
        }
        this.isAuthenticated.set(true);
      })
    );
  }

  perfilEstaCompletado(){
    return localStorage.getItem('estadoPerfil') === "completado";
  }

  estaLogueado(){
    return localStorage.getItem('rol') != null;
  }


  marcarPerfilCompletado(){
    localStorage.setItem('estadoPerfil', "completado");
  }

  logout() {
    localStorage.clear();
    this.isAuthenticated.set(false);
    this.router.navigate(['/']);
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  getRol(): string | null {
    return localStorage.getItem('rol');
  }

  esAdmin() {
    return localStorage.getItem('rol') === "Admin";
  }

  esCliente() {
    return localStorage.getItem('rol') === "Cliente";
  }

  esFreelancer() {
    return localStorage.getItem('rol') === "Freelancer";
  }


}