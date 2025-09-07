package com.clienteservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersona;


    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 10)
    private String genero;

    private Integer edad;

    @Column(unique = true, nullable = false, length = 20)
    private String identificacion;

    @Column(length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;
}
