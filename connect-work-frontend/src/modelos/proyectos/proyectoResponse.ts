export interface ProyectoResponse {
    nombre: string;
    descripcion: string;
    categoria: string;
    estado: string;
    presupuestoMaximo: number;
    nombreCliente: string;
    fechaCreacion: Date;
    fechaEntregaDeseada: Date;
    id: number;
    idEstado: number;
    editable: boolean;
    nicknameCliente: string;
}