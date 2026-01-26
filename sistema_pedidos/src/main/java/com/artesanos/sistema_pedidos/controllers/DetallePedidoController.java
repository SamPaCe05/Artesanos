package com.artesanos.sistema_pedidos.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.artesanos.sistema_pedidos.dtos.ProductoDetalleDto;
import com.artesanos.sistema_pedidos.services.DetallePedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "/api/detallePedido")
@Tag(name = "Detalles Pedidos", description = "Consulta Detalles de un Pedido")
public class DetallePedidoController {
    DetallePedidoService detallePedidoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No hay pedido con ese id", content = @Content),
            @ApiResponse(responseCode = "200", description = "Pedido encontrado", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductoDetalleDto.class))))
    })
    @Operation(summary = "Obtener detalle de un pedido por id del pedido")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CAJA','MESERA')")
    public ResponseEntity<List<ProductoDetalleDto>> getDetallesPedido(@PathVariable Integer id) {
        List<ProductoDetalleDto> productoDtos = detallePedidoService.getDetallesPedido(id);
        if (productoDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(productoDtos);
    }

}
