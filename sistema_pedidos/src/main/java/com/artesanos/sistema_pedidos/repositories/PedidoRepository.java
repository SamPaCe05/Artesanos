package com.artesanos.sistema_pedidos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.artesanos.sistema_pedidos.dtos.PedidoDto;
import com.artesanos.sistema_pedidos.entities.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("""
            select p from Pedido p where p.numeroMesa =?1 and p.estadoPedido ='PENDIENTE'
                """)
    public List<Pedido> findPedidoPendienteByMesa(Integer numeroMesa);
    @Query("""
                select new com.artesanos.sistema_pedidos.dtos.PedidoDto(
                    p.id,
                    p.numeroMesa
                )
                from Pedido p
                where p.estadoPedido = 'PENDIENTE'
            """)
    public List<PedidoDto> findAllPedidos();
    

}
