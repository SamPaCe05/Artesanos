package com.artesanos.sistema_pedidos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.artesanos.sistema_pedidos.dtos.PedidoDto;
import com.artesanos.sistema_pedidos.repositories.DetallePedidoRepository;
import com.artesanos.sistema_pedidos.repositories.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, DetallePedidoRepository detallePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    public List<PedidoDto> listarPedidos() {
        return pedidoRepository.findAllPedidos();
    }

    @Override
    public List<PedidoDto> getPedidosCompletos() {
        List<PedidoDto> pedidoDtos = pedidoRepository.findAllPedidos();
        pedidoDtos.forEach(p -> {
            p.setProductos(detallePedidoRepository.findProductoPorPedidoId(p.getId()));
        });
        return pedidoDtos;
    }

}
