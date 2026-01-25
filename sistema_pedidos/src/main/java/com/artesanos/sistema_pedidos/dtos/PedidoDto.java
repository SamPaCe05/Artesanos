package com.artesanos.sistema_pedidos.dtos;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoDto {
    Integer id;
    Integer numeroMesa;
    Integer total;
    List<ProductoDetalleDto> productos;

    public PedidoDto(Integer id, Integer total, Integer numeroMesa) {
        this.id = id;
        this.total = total;
        this.numeroMesa = numeroMesa;
    }

    public PedidoDto(Integer id, Integer numeroMesa) {
        this.id = id;
        this.numeroMesa = numeroMesa;
    }
}
