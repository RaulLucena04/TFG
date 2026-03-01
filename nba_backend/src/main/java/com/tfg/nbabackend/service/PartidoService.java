package com.tfg.nbabackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.nbabackend.enums.EstadoPartido;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.repository.PartidoRepository;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final ApuestaService apuestaService;

    public PartidoService(PartidoRepository partidoRepository,
            ApuestaService apuestaService) {
        this.partidoRepository = partidoRepository;
        this.apuestaService = apuestaService;
    }

    public List<Partido> listarPartidos() {
        return partidoRepository.findAll();
    }

    public Partido guardar(Partido partido) {
        return partidoRepository.save(partido);
    }

    public Partido finalizarPartido(Long id, Integer puntosLocal, Integer puntosVisitante) {

        Partido partido = partidoRepository.findById(id).orElseThrow();

        // ⚠️ Evitar finalizar dos veces
        if (partido.getEstado() == EstadoPartido.FINALIZADO) {
            throw new RuntimeException("El partido ya está finalizado");
        }

        partido.setPuntosLocal(puntosLocal);
        partido.setPuntosVisitante(puntosVisitante);
        partido.setEstado(EstadoPartido.FINALIZADO);

        partidoRepository.save(partido);

        // Resolver apuestas solo una vez
        apuestaService.resolverApuestas(partido);

        return partido;
    }
}
