package com.gmarcolla.demo_park_api.service;

import com.gmarcolla.demo_park_api.entity.Vaga;
import com.gmarcolla.demo_park_api.exception.CodigoUniqueViolateionException;
import com.gmarcolla.demo_park_api.exception.EntityNotFoundException;
import com.gmarcolla.demo_park_api.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gmarcolla.demo_park_api.entity.Vaga.StatusVaga.LIVRE;

@RequiredArgsConstructor
@Service
public class VagaService {
    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar (Vaga vaga){
        try {
            return vagaRepository.save(vaga);
        }catch (DataIntegrityViolationException ex){
            throw new CodigoUniqueViolateionException(
                    String.format("Vaga com o código '%s' já cadastrada", vaga.getCodigo())
            );
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo (String codigo) {
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com o código '%s' não foi encontrada", codigo))
        );
    }
    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new EntityNotFoundException("Nenhuma vaga livre foi encontrada")
        );
    }
}
