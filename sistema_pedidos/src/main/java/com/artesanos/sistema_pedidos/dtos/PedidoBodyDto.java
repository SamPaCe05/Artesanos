package com.artesanos.sistema_pedidos.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PedidoBodyDto {
    Integer numeroMesa;
    List<ProductoDto> productos;

    public PedidoBodyDto(Integer numeroMesa, List<ProductoDto> productoDtos) {
        this.numeroMesa = numeroMesa;
        this.productos = productoDtos;
    }
}
