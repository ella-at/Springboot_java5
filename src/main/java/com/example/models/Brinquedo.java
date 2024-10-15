package com.example.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "TDS_Sec_MVC_TB_Brinquedos")
public class Brinquedo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "preco", nullable = false)
    private Double preco;

    @Column(name = "tipo", length = 100)
    private String tipo;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "estado", length = 50)
    private String estado;
}
