package com.artesanos.sistema_pedidos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artesanos.sistema_pedidos.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public Optional<Producto> findByNombreProducto(String nombreProducto);

    List<Producto> findByActivoTrue();

    public List<Producto> findByNombreProductoContainingIgnoreCaseAndActivoTrue(String nombreProducto);

}
