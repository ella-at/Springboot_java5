package com.example.services;

import com.example.models.Brinquedo;
import com.example.repositories.BrinquedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrinquedoService {

    @Autowired
    private BrinquedoRepository repository;

    public List<Brinquedo> listarTodos() {
        return repository.findAll();
    }

    public Brinquedo buscarPorId(Long id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception("Brinquedo não encontrado!"));
    }

    public Brinquedo save(Brinquedo brinquedo) {
        return repository.save(brinquedo);
    }

    public void deletarPorId(Long id) throws Exception {
        Brinquedo brinquedo = buscarPorId(id); // Para garantir que existe
        repository.deleteById(brinquedo.getId());
    }

    public List<Brinquedo> filterBrinquedos(String nome, String tipo, String estado, Double precoMin, Double precoMax, Long id) {
        return repository.findAll().stream()
                .filter(brinquedo -> (nome == null || brinquedo.getNome().toLowerCase().contains(nome.toLowerCase())))
                .filter(brinquedo -> (tipo == null || brinquedo.getTipo().equalsIgnoreCase(tipo)))
                .filter(brinquedo -> (estado == null || brinquedo.getEstado().equalsIgnoreCase(estado)))
                .filter(brinquedo -> (precoMin == null || brinquedo.getPreco() >= precoMin))
                .filter(brinquedo -> (precoMax == null || brinquedo.getPreco() <= precoMax))
                .filter(brinquedo -> (id == null || brinquedo.getId().equals(id)))
                .collect(Collectors.toList());
    }


}
