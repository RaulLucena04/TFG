package com.tfg.nbabackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.nbabackend.dto.CanjearPuntosRequest;
import com.tfg.nbabackend.dto.CanjearPuntosResponse;
import com.tfg.nbabackend.service.TiendaService;

@RestController
@RequestMapping("/tienda")
@CrossOrigin(origins = "*")
public class TiendaController {

    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @PostMapping("/canjear")
    public CanjearPuntosResponse canjearPuntos(@RequestBody CanjearPuntosRequest request) throws Exception {
        return tiendaService.canjearPuntos(request);
    }
}
