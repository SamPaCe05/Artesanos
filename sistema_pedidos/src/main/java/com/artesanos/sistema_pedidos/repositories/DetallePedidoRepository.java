package com.artesanos.sistema_pedidos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.artesanos.sistema_pedidos.dtos.ProductoDto;
import com.artesanos.sistema_pedidos.entities.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    @Query("""
            select new com.artesanos.sistema_pedidos.dtos.ProductoDto(
            dt.producto.nombreProducto,
            dt.cantidadProducto,
            dt.precioMomento, 
            dt.subtotalPedido)
            from DetallePedido dt
            where dt.pedido.id=?1
        """)
    List<ProductoDto> findProductosDePedido(Integer id);
}
