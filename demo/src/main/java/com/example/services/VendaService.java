package com.example.services;

import com.example.models.Brinquedo;
import com.example.models.Venda;
import com.example.repositories.BrinquedoRepository;
import com.example.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private BrinquedoRepository brinquedoRepository;

    public void realizarVenda(Long brinquedoId, int quantidade, String formaPagamento, String funcionario) throws Exception {
        Brinquedo brinquedo = brinquedoRepository.findById(brinquedoId)
                .orElseThrow(() -> new Exception("Brinquedo não encontrado"));

        if (quantidade > brinquedo.getQuantidade()) {
            throw new Exception("Quantidade indisponível em estoque");
        }

        // Atualizar o estoque
        brinquedo.setQuantidade(brinquedo.getQuantidade() - quantidade);
        brinquedoRepository.save(brinquedo);

        // Criar e salvar a venda
        Venda venda = new Venda();
        venda.setBrinquedo(brinquedo);
        venda.setQuantidade(quantidade);
        venda.setValorTotal(brinquedo.getPreco() * quantidade);
        venda.setFormaPagamento(formaPagamento);
        venda.setFuncionario(funcionario);
        venda.setDataHora(LocalDateTime.now());

        vendaRepository.save(venda);
    }
}
