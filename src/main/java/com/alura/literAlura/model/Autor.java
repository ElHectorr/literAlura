package com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "autores")

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String birth_year;
    private String death_year;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> librosEscritos = new ArrayList<>();

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.name = datosAutor.nombre();
        this.birth_year = datosAutor.fechaDeNacimiento();
        this.death_year = datosAutor.fechaDeFallecimeinto();
    }



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFechaDeNacimiento() {
        return birth_year;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.birth_year = fechaDeNacimiento;
    }

    public String getDeath_year() {
        return death_year;
    }

    public void setDeath_year(String death_year) {
        this.death_year = death_year;
    }

    public List<Libro> getLibrosEscritos() {
        return librosEscritos;
    }

    public void setLibrosEscritos(List<Libro> librosEscritos) {
        this.librosEscritos = new ArrayList<>();
        librosEscritos.forEach(l -> {
            l.setAutor(this);
            this.librosEscritos.add(l);
        });
    }

    @Override
    public String toString() {
        return "-----------------------------------------------" +
                "\nNombre: "+ name +
                "\nAño de nacimiento: " + birth_year +
                "\nAño de fallecimiento: " + death_year +
                "\n-----------------------------------------------";
    }
}

