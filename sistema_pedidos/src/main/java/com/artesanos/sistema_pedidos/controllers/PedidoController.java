package com.artesanos.sistema_pedidos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artesanos.sistema_pedidos.dtos.PedidoBodyDto;
import com.artesanos.sistema_pedidos.dtos.PedidoDto;
import com.artesanos.sistema_pedidos.dtos.ProductoDto;
import com.artesanos.sistema_pedidos.entities.Pedido;
import com.artesanos.sistema_pedidos.services.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestión de los Pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No hay pedidos con estado pendiente", content = @Content),
            @ApiResponse(responseCode = "200", description = "Pedidos con estado pendiente encontrados", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductoDto.class))))
    })
    @Operation(summary = "Obtener los pedidos")
    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('CAJA','MESERA')")
    public ResponseEntity<List<PedidoDto>> getPedidos() {
        List<PedidoDto> pedidos = pedidoService.listarPedidos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(pedidos);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Intentar crear pedido en mesa que aun tiene uno pendiente", content = @Content),
            @ApiResponse(responseCode = "200", description = "Pedido creado")
    })
    @Operation(summary = "Crear pedidos")
    @PostMapping("/crear/{nombreUsuario}")
    @PreAuthorize("hasRole('MESERA')")
    public ResponseEntity<?> postPedido(@RequestBody PedidoDto pedidoDto, @PathVariable String nombreUsuario) {
        Optional<Pedido> pedido = pedidoService.save(pedidoDto, nombreUsuario);
        if (pedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    "La mesa ya tiene un pedido pendiente, si requiere agregar productos actualice el pedido pendiente");
        }
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Intentar actualizar pedido con id no existente", content = @Content),
            @ApiResponse(responseCode = "200", description = "Pedido actualizado")
    })
    @Operation(summary = "Actualizar pedido por id")
    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('MESERA')")
    public ResponseEntity<?> putPedido(@PathVariable Integer id, @RequestBody PedidoBodyDto pedidoDto) {
        if (pedidoService.actualizarPedido(id, pedidoDto).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe pedido con ese id");
        }
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Intentar actualizar pedido con id no existente", content = @Content),
            @ApiResponse(responseCode = "200", description = "Estado de pedido actualizado")
    })
    @Operation(summary = "Actualizar estado de pedido por id")
    @PutMapping("/actualizar/{id}/{estado}")
    @PreAuthorize("hasAnyRole('CAJA','MESERA')")
    public ResponseEntity<?> putEstadoPedido(@PathVariable Integer id, @PathVariable String estado) {
        return pedidoService.actualizarEstadoPedido(id, estado.toUpperCase()).map(p -> ResponseEntity.ok().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }
}
