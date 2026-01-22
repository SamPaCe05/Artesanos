package com.artesanos.sistema_pedidos.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductoDto {
    String nombreProducto;
    Integer cantidadProducto;
    Integer subtotalPedido;
    Integer precioMomento;

    public ProductoDto(String nombre, Integer cantidad, Integer precio, Integer subtotal) {
        this.nombreProducto = nombre;
        this.cantidadProducto = cantidad;
        this.precioMomento = precio;
        this.subtotalPedido = subtotal;
    }
}
