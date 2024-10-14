package com.example.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "TDS_Sec_MVC_TB_Users_Brinquedos")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @Column(name = "role", length = 50)
    private String role;
}
