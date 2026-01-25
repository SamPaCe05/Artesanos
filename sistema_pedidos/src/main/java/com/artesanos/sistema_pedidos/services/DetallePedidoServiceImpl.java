package com.artesanos.sistema_pedidos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artesanos.sistema_pedidos.dtos.ProductoDetalleDto;
import com.artesanos.sistema_pedidos.repositories.DetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoDetalleDto> getDetallesPedido(Integer id) {
        return detallePedidoRepository.findProductosDePedido(id);
    }

}
