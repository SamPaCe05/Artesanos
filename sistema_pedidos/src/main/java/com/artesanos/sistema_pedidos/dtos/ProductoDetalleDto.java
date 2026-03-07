package com.artesanos.sistema_pedidos.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductoDetalleDto {
    String nombreProducto;
    Integer cantidadProducto;
    Integer subtotalPedido;
    Integer precioMomento;
    String peticionCliente;
    LocalDateTime fechaModificacion; 

    public ProductoDetalleDto(String nombre, Integer cantidad, Integer precio, Integer subtotal,
            String peticionCliente, LocalDateTime fechaModificacion) {
        this.nombreProducto = nombre;
        this.cantidadProducto = cantidad;
        this.precioMomento = precio;
        this.subtotalPedido = subtotal;
        this.peticionCliente = peticionCliente;
        this.fechaModificacion= fechaModificacion;
    }
}
