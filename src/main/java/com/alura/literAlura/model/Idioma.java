package com.alura.literAlura.model;

import java.util.Calendar;

public enum Idioma {
    ESPANOL("es", "español"),
    INGLES("en", "ingles"),
    FRANCES("fr", "frances"),
    PORTUGUES("pt", "portugues");

    private String idiomaOmdb;
    private String idiomaEspanol;

    Idioma(String idiomaOmdb, String idiomaEspanol){
        this.idiomaOmdb = idiomaOmdb;
        this.idiomaEspanol = idiomaEspanol;
    }

    public static Idioma fromString(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idiomaOmdb.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontró la categoría: " + text);

    }
    public static Idioma fromEspanol(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idiomaEspanol.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontró la categoría: " + text);

    }

}
