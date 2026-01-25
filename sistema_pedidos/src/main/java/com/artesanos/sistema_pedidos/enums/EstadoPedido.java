package com.artesanos.sistema_pedidos.enums;

public enum EstadoPedido {
    PENDIENTE, RESUELTO, CANCELADO;

    public static EstadoPedido fromString(String estado) {
        for (EstadoPedido e : EstadoPedido.values()) {
            if (e.name().equals(estado)) {
                return e;
            }
        }

        return PENDIENTE;
    }
}
