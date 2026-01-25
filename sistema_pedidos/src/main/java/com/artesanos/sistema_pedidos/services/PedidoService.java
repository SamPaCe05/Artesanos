package com.artesanos.sistema_pedidos.services;

import java.util.List;
import java.util.Optional;

import com.artesanos.sistema_pedidos.dtos.PedidoBodyDto;
import com.artesanos.sistema_pedidos.dtos.PedidoDto;
import com.artesanos.sistema_pedidos.entities.Pedido;

public interface PedidoService {
    public List<PedidoDto> listarPedidos();

    public Optional<Pedido> save(PedidoDto pedido, String usuarioNombre);

    public Optional<Pedido> actualizarEstadoPedido(Integer id, String estado);

    public Optional<Pedido> actualizarPedido(Integer id, PedidoBodyDto pedidoBodyDto);
}
