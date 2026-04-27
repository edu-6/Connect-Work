export interface UsuarioLoginResponse{
    nombre: string;
    cui ?: string | null;
    rol : string;
    token: string;
    nickname: string;

}