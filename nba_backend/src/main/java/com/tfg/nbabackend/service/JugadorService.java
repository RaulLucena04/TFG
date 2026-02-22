package com.tfg.nbabackend.service;

import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.repository.JugadorRepository;
import com.tfg.nbabackend.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    public JugadorService(JugadorRepository jugadorRepository,
                          EquipoRepository equipoRepository) {
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
    }

    public List<Jugador> listarTodos() {
        return jugadorRepository.findAll();
    }

    public List<Jugador> obtenerPorEquipo(Long equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return jugadorRepository.findByEquipo(equipo);
    }

    public Jugador guardar(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }
}