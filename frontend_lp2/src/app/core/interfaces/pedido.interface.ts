export interface ItemVentaDTO{
    idProducto: number;
    cantidad: number;
}

export interface VentaDTO{
    idProducto: number;
    cantidad: ItemVentaDTO[];
}