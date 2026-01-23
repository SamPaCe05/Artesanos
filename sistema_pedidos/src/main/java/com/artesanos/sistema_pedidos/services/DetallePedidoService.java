package com.artesanos.sistema_pedidos.services;

import java.util.List;

import com.artesanos.sistema_pedidos.dtos.ProductoDto;

public interface DetallePedidoService {
    List<ProductoDto> getDetallesPedido(Integer id);
}
