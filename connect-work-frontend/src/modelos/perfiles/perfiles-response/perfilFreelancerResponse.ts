import { CalificacionResponse } from "../../calificaciones/calificacionResponse";

export interface PerfilFreelancerResponse {
  experiencia: string;
  tarifaPorHora: number;
  biografia: string;
  calificaciones : CalificacionResponse[];
  promedioCalificaciones: number;
}