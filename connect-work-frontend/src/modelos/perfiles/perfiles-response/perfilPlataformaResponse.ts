import { PerfilClienteResponse } from "./perfilClienteResponse";
import { PerfilFreelancerResponse } from "./perfilFreelancerResponse";
import { PerfilSimpleResponse } from "./usuarioSimpleResponse";

export interface PerfilPlataformaResponse {
  correo: string;
  telefono: string;
  fechaNacimiento: Date
  cui: string;
  
  perfilSimple: PerfilSimpleResponse;
  perfilCliente?: PerfilClienteResponse;
  perfilFreelancer?: PerfilFreelancerResponse;
}