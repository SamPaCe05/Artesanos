package com.artesanos.sistema_pedidos.services;

import java.util.List;

import com.artesanos.sistema_pedidos.dtos.ProductoDetalleDto;

public interface DetallePedidoService {
    public List<ProductoDetalleDto> getDetallesPedido(Integer id);
}
