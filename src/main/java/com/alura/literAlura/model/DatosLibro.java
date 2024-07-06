package com.alura.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer numeroDeDescargas) {

//    @Override
//    public String toString() {
//        return "--------------------------------------- \nTitulo: " + titulo +
//                "\nAutor: " + autor +
//                "\nIdioma: " + idiomas +
//                "\nNumeroDeDescargas: " + numeroDeDescargas +
//                "\n---------------------------------------\n";
//    }
}
