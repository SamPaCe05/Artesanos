package com.artesanos.sistema_pedidos.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacturaDto {
    Integer idPedido;
    String impresoraIp;
    LocalDate fechaFactura;
    String numeroMesa;
    String nombreDomicilio;
    String numeroCliente; 
    List<ProductoDetalleDto> productos;
    Integer total;

}

// {
//     "idPedido": 101,
//     "impresoraIp": "192.168.0.100",
//     "fechaFactura": "2026-02-17",
//     "numeroMesa": "",
//     "nombreDomicilio": "Maximus",
//     "numeroCliente": "3102278330",
//     "productos": [
//         {
//             "nombreProducto": "pizza albahaca a la vaca + espanta brujas ",
//             "cantidadProducto": 1,
//             "subtotalPedido": 21900
//         },
//         {
//             "nombreProducto": "pizza albahaca a la vaca + campesina",
//             "cantidadProducto": 2,
//             "subtotalPedido": 21900
//         }, 
//         {
//             "nombreProducto": "adicion queso",
//             "cantidadProducto": 1,
//             "subtotalPedido": 4000
//         },
//         {
//             "nombreProducto": "pizza meditando",
//             "cantidadProducto": 1,
//             "subtotalPedido": 35000
//         }
//     ],
//     "total": 30000
// }