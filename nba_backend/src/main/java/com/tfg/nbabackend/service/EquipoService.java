package com.tfg.nbabackend.service;

import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> listarTodos() {
        return equipoRepository.findAll();
    }

    public Optional<Equipo> obtenerPorId(Long id) {
        return equipoRepository.findById(id);
    }

    public Equipo guardar(Equipo equipo) {
        return equipoRepository.save(equipo);
    }
}