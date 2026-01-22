package com.artesanos.sistema_pedidos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artesanos.sistema_pedidos.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombre(String nombre);
}
