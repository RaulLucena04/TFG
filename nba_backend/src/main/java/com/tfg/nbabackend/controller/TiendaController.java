package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.dto.CanjearPuntosRequest;
import com.tfg.nbabackend.dto.CanjearPuntosResponse;
import com.tfg.nbabackend.service.TiendaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tienda")
@CrossOrigin(origins = "*")
public class TiendaController {

    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @PostMapping("/canjear")
    public CanjearPuntosResponse canjearPuntos(@RequestBody CanjearPuntosRequest request) {
        return tiendaService.canjearPuntos(request);
    }
}
