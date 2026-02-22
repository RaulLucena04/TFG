package com.tfg.nbabackend.service;

import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.enums.EstadoPartido;
import com.tfg.nbabackend.repository.PartidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

        partido.setPuntosLocal(puntosLocal);
        partido.setPuntosVisitante(puntosVisitante);
        partido.setEstado(EstadoPartido.FINALIZADO);

        partidoRepository.save(partido);

        // 🔥 Resolver apuestas automáticamente
        apuestaService.resolverApuestas(partido);

        return partido;
    }
}