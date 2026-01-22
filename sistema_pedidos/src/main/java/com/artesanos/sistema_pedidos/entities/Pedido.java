package com.artesanos.sistema_pedidos.entities;

import java.time.LocalDate;

import com.artesanos.sistema_pedidos.enums.EstadoPedido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_pedido")
    Integer id;
    @Column(name = "fecha_pedido")
    LocalDate fechaPedido;
    @Column(name = "total")
    Integer totalPedido;
    @Column(name = "n_mesa")
    Integer numeroMesa;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    EstadoPedido estadoPedido;

    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    Usuario usuario;


    public Pedido() {
    }
}
