package com.alura.literAlura.model;


import jakarta.persistence.*;

@Entity
@Table(name = "libros")

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma idiomas;
    private Integer numeroDeDescargas;


    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.title = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autor().get(0));
        this.idiomas = Idioma.fromString(datosLibro.idiomas().get(0));
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idioma idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "************************************************** \nIdioma: " + idiomas +
                "\nTitulo: " + title +
                "\nAutor: " + autor.getName() +
                "\nNumeroDeDescargas: " + numeroDeDescargas +
                "\n**************************************************\n";
    }
}

