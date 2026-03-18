package com.tfg.nbabackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.nbabackend.dto.CanjearPuntosRequest;
import com.tfg.nbabackend.dto.CanjearPuntosResponse;
import com.tfg.nbabackend.service.TiendaService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con la tienda virtual.
 * 
 * <p>Proporciona endpoints para canjear puntos virtuales por dinero mediante integración
 * con PayPal API. Tasa de cambio: 1000 puntos = 1€.
 * Todos los endpoints están habilitados para CORS desde cualquier origen.
 * 
 * @author TFG
 * @version 1.0
 */
@RestController
@RequestMapping("/tienda")
@CrossOrigin(origins = "*")
public class TiendaController {

    private final TiendaService tiendaService;

    /**
     * Constructor del controlador de tienda.
     * 
     * @param tiendaService servicio de tienda
     */
    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    /**
     * Canjea puntos virtuales por dinero mediante transferencia PayPal.
     * 
     * <p>Valida que el usuario tenga suficientes puntos (mínimo 1000), calcula
     * la cantidad en euros y realiza la transferencia a través de PayPal API.
     * 
     * @param request solicitud con ID de usuario, puntos a canjear y email de PayPal
     * @return respuesta con el resultado del canje (éxito/error, mensaje, euros transferidos)
     * @throws Exception si hay un error al procesar el pago con PayPal
     */
    @PostMapping("/canjear")
    public CanjearPuntosResponse canjearPuntos(@RequestBody CanjearPuntosRequest request) throws Exception {
        return tiendaService.canjearPuntos(request);
    }
}
