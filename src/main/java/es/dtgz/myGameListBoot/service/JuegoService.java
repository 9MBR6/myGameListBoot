package es.dtgz.myGameListBoot.service;

import es.dtgz.myGameListBoot.model.Juego;
import es.dtgz.myGameListBoot.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {

    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> getAllJuegos() {
        return juegoRepository.findAll();
    }

    public Optional<Juego> getJuegoById(Long id) {
        return juegoRepository.findById(id);
    }

    public Juego saveJuego(Juego moto) {
        return juegoRepository.save(moto);
    }

    public void deleteJuego(Long id) {
        juegoRepository.deleteById(id);
    }
}
