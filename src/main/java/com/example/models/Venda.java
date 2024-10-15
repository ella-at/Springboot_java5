package com.example.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TDS_Sec_MVC_TB_Vendas_Brinquedos")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brinquedo_id", nullable = false)
    private Brinquedo brinquedo;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    @Column(name = "forma_pagamento", length = 255, nullable = false)
    private String formaPagamento;

    @Column(name = "funcionario", length = 255, nullable = false)
    private String funcionario;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    public Venda() {
        this.dataHora = LocalDateTime.now();
    }
}
