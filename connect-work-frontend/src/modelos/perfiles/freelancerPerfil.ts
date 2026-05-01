import { HabilidadFreelancer } from "./habilidadFreelancer";

export interface PefilFreelancer{
 cuiFreelancer: string;
 biografia: string;
 tarifaHora: number;
 idNivelExperiencia: number;
 habilidades: HabilidadFreelancer[];
}