package com.artesanos.sistema_pedidos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artesanos.sistema_pedidos.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
