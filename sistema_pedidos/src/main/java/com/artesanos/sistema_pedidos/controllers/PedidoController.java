package com.artesanos.sistema_pedidos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artesanos.sistema_pedidos.dtos.PedidoBodyDto;
import com.artesanos.sistema_pedidos.dtos.PedidoDto;
import com.artesanos.sistema_pedidos.services.PedidoService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> getPedidos() {
        List<PedidoDto> pedidos = pedidoService.getPedidosCompletos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(pedidos);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> postPedidos(@RequestBody PedidoBodyDto pedidoDto) {

        return ResponseEntity.ok().build();
    }

}
