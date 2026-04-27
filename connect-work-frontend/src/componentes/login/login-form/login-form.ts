import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuarioLoginRequest } from '../../../modelos/login/usuarioLoginRequest';
import { AutenticacionServicio } from '../../../servicios/autenficacion-service';
import { Router } from '@angular/router';
import { ErrorBackend } from '../../../modelos/ErrorBackend';
import { UsuarioLoginResponse } from '../../../modelos/login/usuarioLoginResponse';

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css',
})
export class LoginForm implements OnInit {

  formulario !: FormGroup;


  usuarioRequest !: UsuarioLoginRequest;

  btnPresionado: boolean = false;
  loginFallido: boolean = false;

  constructor(private formBuilder: FormBuilder, private autenticacionService: AutenticacionServicio, private router: Router) {

  }

  ngOnInit(): void {
    this.formulario = this.formBuilder.group({
      nickname: ['', [Validators.required, Validators.maxLength(100)]],
      contraseña: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  private resetearFormulario(): void {
    this.formulario.reset();
  }




  public enviarFormulario(): void {
    this.btnPresionado = true;

    if (this.formulario.valid) {

      this.usuarioRequest = this.formulario.value as UsuarioLoginRequest;

      this.autenticacionService.login(this.usuarioRequest).subscribe({
        next: (empleado: UsuarioLoginResponse) => {
          this.router.navigate(["/"]);
        },
        error: (errorHttp: any) => {
          const errorData: ErrorBackend = errorHttp.error;
          this.loginFallido = true;
          this.resetearFormulario();
        }
      });
    }
  }


}
