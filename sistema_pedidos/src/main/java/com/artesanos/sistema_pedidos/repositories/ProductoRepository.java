package com.artesanos.sistema_pedidos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artesanos.sistema_pedidos.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public Optional<Producto> findByNombreProducto(String nombreProducto);
}
