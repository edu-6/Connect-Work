import { RechazoEntrega } from "./rechazoEntrega";

export interface EntregaResponse {
  id: number;
  descripcion: string;
  archivos: string[];
  estado: string;
  fechaEnvio: Date;
  rechazo?: RechazoEntrega; 
}