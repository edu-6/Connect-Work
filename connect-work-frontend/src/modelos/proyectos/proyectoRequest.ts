export interface ProyectoRequest {
  nombre: string;
  descripcion: string;
  idCategoria: number;
  id: number;
  presupuestoMaximo: number;
  cuiCliente: string;
  fechaEntregaDeseada: Date;
}