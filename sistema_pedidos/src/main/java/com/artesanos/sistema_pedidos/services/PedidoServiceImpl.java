package com.artesanos.sistema_pedidos.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artesanos.sistema_pedidos.dtos.PedidoBodyDto;
import com.artesanos.sistema_pedidos.dtos.PedidoDto;
import com.artesanos.sistema_pedidos.dtos.ProductoDetalleDto;
import com.artesanos.sistema_pedidos.entities.DetallePedido;
import com.artesanos.sistema_pedidos.entities.Pedido;
import com.artesanos.sistema_pedidos.entities.Producto;
import com.artesanos.sistema_pedidos.entities.Usuario;
import com.artesanos.sistema_pedidos.enums.EstadoPedido;
import com.artesanos.sistema_pedidos.repositories.PedidoRepository;
import com.artesanos.sistema_pedidos.repositories.ProductoRepository;
import com.artesanos.sistema_pedidos.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository,
            UsuarioRepository usuarioRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDto> listarPedidos() {
        return pedidoRepository.findAllPedidos();
    }

    @Override
    @Transactional
    public Optional<Pedido> save(PedidoDto pedidoDto, String usuarioNombre) {
        if (pedidoRepository.findPedidoPendienteByMesa(pedidoDto.getNumeroMesa()).size() > 0) {
            return Optional.empty();
        }
        Pedido pedido = new Pedido();

        List<DetallePedido> detallePedidos = new ArrayList<>();

        Integer total = 0;

        for (ProductoDetalleDto i : pedidoDto.getProductos()) {

            DetallePedido detallePedido = new DetallePedido();
            Producto prodOptional = productoRepository.findByNombreProducto(i.getNombreProducto()).orElseThrow();

            int subtotal = i.getCantidadProducto() * i.getPrecioMomento();

            detallePedido.setProducto(prodOptional);
            detallePedido.setCantidadProducto(i.getCantidadProducto());
            detallePedido.setPedido(pedido);
            detallePedido.setPrecioMomento(prodOptional.getPrecio());
            detallePedido.setSubtotalPedido(subtotal);

            total += subtotal;

            detallePedidos.add(detallePedido);
        }

        Optional<Usuario> usuario = usuarioRepository.findByNombre(usuarioNombre);

        pedido.setUsuario(usuario.orElseThrow());
        pedido.setNumeroMesa(pedidoDto.getNumeroMesa());
        pedido.setFechaPedido(LocalDate.now());
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setDetallesPedido(detallePedidos);
        pedido.setTotalPedido(total);

        return Optional.of(pedidoRepository.save(pedido));

    }

    @Transactional
    @SuppressWarnings("null")
    @Override
    public Optional<Pedido> actualizarEstadoPedido(Integer id, String estado) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setEstadoPedido(EstadoPedido.fromString(estado));
            return pedidoRepository.save(pedido);
        });
    }

    @Transactional
    @SuppressWarnings("null")
    @Override
    public Optional<Pedido> actualizarPedido(Integer id, PedidoBodyDto pedidoBodyDto) {
        return pedidoRepository.findById(id).map(pedido -> {

            if (pedidoBodyDto.getNumeroMesa() != null) {
                pedido.setNumeroMesa(pedidoBodyDto.getNumeroMesa());
            }

            if (pedidoBodyDto.getProductos() == null) {
                return pedidoRepository.save(pedido);
            }

            pedido.getDetallesPedido().clear();
            int totalAcumulado = 0;

            for (ProductoDetalleDto i : pedidoBodyDto.getProductos()) {
                DetallePedido detallePedido = new DetallePedido();

                Producto prod = productoRepository.findByNombreProducto(i.getNombreProducto())
                        .orElseThrow(
                                () -> new EntityNotFoundException("Producto no encontrado: " + i.getNombreProducto()));

                int subtotal = i.getCantidadProducto() * prod.getPrecio();

                detallePedido.setPedido(pedido);
                detallePedido.setProducto(prod);
                detallePedido.setCantidadProducto(i.getCantidadProducto());
                detallePedido.setPrecioMomento(prod.getPrecio());
                detallePedido.setSubtotalPedido(subtotal);

                totalAcumulado += subtotal;
                pedido.getDetallesPedido().add(detallePedido);
            }

            pedido.setTotalPedido(totalAcumulado);
            return pedidoRepository.save(pedido);
        });
    }

}
