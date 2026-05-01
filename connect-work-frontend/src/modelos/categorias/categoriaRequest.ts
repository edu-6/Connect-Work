import { Categoria } from "./categoria";
import { HabilidadCategoria } from "./habilidad-categoria";

export interface CategoriaRequest{
    categoria: Categoria;
    habilidades: HabilidadCategoria [];
    
}


