package com.artesanos.sistema_pedidos.services;

import java.util.List;
import java.util.Optional;

import com.artesanos.sistema_pedidos.dtos.PedidoDto;
import com.artesanos.sistema_pedidos.entities.Pedido;

public interface PedidoService {
    List<PedidoDto> listarPedidos();

    Optional<Pedido> save(PedidoDto pedido, String usuarioNombre);
}
