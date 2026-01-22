package com.artesanos.sistema_pedidos.services;

import java.util.List;

import com.artesanos.sistema_pedidos.dtos.PedidoDto;

public interface PedidoService {
    List<PedidoDto> listarPedidos();

    List<PedidoDto> getPedidosCompletos();
}
