export interface UsuarioPlataformaRequest {
  nickname: string;
  contraseña?: string;
  activo: boolean;
  idRol: number;
  cui: string;
  nombre: string;
  correo: string;
  telefono: string;
  direccion: string;
  fechaNacimiento: string;
  perfilCompletado: boolean;
}