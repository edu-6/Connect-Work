import { Habilidad } from "../habilidades/habilidad";
import { Categoria } from "./categoria";

export interface CategoriaResponse{
    categoria: Categoria;
    habilidades: Habilidad [];
}