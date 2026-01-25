package com.artesanos.sistema_pedidos.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductoDto {
    String nombreProducto;
    Integer precioProducto;
    boolean combinable;
    boolean activo; 
}
