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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
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
            @ApiResponse(responseCode = "200", description = "Pedidos con estado pendiente encontrados", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductoDto.class))))
    })
    @Operation(summary = "Obtener los pedidos")
    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('ROLE_CAJA', 'ROLE_MESERA')")
    public ResponseEntity<List<PedidoDto>> getPedidos() {
        List<PedidoDto> pedidos = pedidoService.listarPedidos();

        return ResponseEntity.ok().body(pedidos);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Intentar crear pedido en mesa que aun tiene uno pendiente", content = @Content),
            @ApiResponse(responseCode = "200", description = "Pedido creado")
    })
    @Operation(summary = "Crear pedidos")
    @PostMapping("/crear/{nombreUsuario}")
    @PreAuthorize("hasAnyAuthority('ROLE_CAJA', 'ROLE_MESERA')")
    public ResponseEntity<?> postPedido(@RequestBody PedidoDto pedidoDto, @PathVariable String nombreUsuario) {
        Optional<Pedido> pedido = pedidoService.save(pedidoDto, nombreUsuario);
        if (pedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    "La mesa ya tiene un pedido pendiente, si requiere agregar productos actualice el pedido pendiente");
        }
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Domicilio creado")
    })
    @Operation(summary = "Crear pedidos")
    @PostMapping("/crear/domicilio/{nombreUsuario}")
    @PreAuthorize("hasAnyAuthority('ROLE_CAJA', 'ROLE_MESERA')")
    public ResponseEntity<?> postPedidoDomicilio(@RequestBody PedidoDto pedidoDto, @PathVariable String nombreUsuario) {
        pedidoService.save(pedidoDto, nombreUsuario);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Intentar actualizar pedido con id no existente", content = @Content),
            @ApiResponse(responseCode = "200", description = "Pedido actualizado")
    })
    @Operation(summary = "Actualizar pedido por id")
    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAuthority('ROLE_MESERA')")
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
    @Operation(summary = "Actualizar estado y metodo de pago de pedido por id")
    @PutMapping("/actualizar/{id}/{estado}/{metodoPago}")
    @PreAuthorize("hasAnyAuthority('ROLE_CAJA', 'ROLE_MESERA')")
    public ResponseEntity<?> putEstadoPedido(@PathVariable Integer id, @PathVariable String estado,
            @PathVariable String metodoPago) {
        return pedidoService.actualizarEstadoPedido(id, estado.toUpperCase(), metodoPago.toUpperCase())
                .map(p -> ResponseEntity.ok().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No existen pedidos para esas fechas", content = @Content),
            @ApiResponse(responseCode = "200", description = "Pedidos Cancelados obtenidos")
    })
    @Operation(summary = "Buscar los pedidos Pagados para el cierre de caja")
    @GetMapping("/resueltos/cierre/{inicio}/{fin}")
    @PreAuthorize("hasAuthority('ROLE_CAJA')")
    public ResponseEntity<List<PedidoDto>> getPedidosPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        LocalDateTime fechaInicio = inicio.atStartOfDay(); // 2026-02-13 00:00:00
        LocalDateTime fechaFin = fin.atTime(LocalTime.MAX); // 2026-02-13 23:59:59.99999
        System.out.println(fechaFin);
        System.out.println(fechaInicio);
        List<PedidoDto> pedidos = pedidoService.findByFechaPedidoBetweenAndEstadoPedido(fechaInicio, fechaFin);
        if (pedidos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedidos);
    }
}
