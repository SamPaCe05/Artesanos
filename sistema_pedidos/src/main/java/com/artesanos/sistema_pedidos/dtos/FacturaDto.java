package com.artesanos.sistema_pedidos.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacturaDto {
    Integer id;
    Integer total;
    LocalDate fechaFactura;
    List<ProductoDetalleDto> productos;

}
