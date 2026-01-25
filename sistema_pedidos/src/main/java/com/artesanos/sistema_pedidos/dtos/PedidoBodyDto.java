package com.artesanos.sistema_pedidos.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PedidoBodyDto {
    Integer numeroMesa;
    List<ProductoDetalleDto> productos;

    public PedidoBodyDto(Integer numeroMesa, List<ProductoDetalleDto> productoDtos) {
        this.numeroMesa = numeroMesa;
        this.productos = productoDtos;
    }

    public PedidoBodyDto(List<ProductoDetalleDto> productoDtos) {
        this.productos = productoDtos;
    }

    public PedidoBodyDto(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }
}
