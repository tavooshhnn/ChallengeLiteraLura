package com.alurareto.literalura.modelo;

public enum Idioma {
    ESPAÑOL("es","español"),
    INGLES ("en","ingles"),
    FRANCES("fr", "frances"),
    PORTUGUES("pt","portugues"),
    LATIN("la", "latin"),
    ALEMAN("de", "aleman"),
    ITALIANO("it", "italiano");


    private String codigoIdioma;
    private String nombreIdioma;

    Idioma(String codigoIdioma, String nombreIdioma) {
        this.codigoIdioma = codigoIdioma;
        this.nombreIdioma = nombreIdioma;
    }
    public static Idioma fromString(String texto) {
        for (Idioma idioma: Idioma.values()) {
            if (idioma.codigoIdioma.equalsIgnoreCase(texto)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún idioma con el código: " + texto);
    }

    public static Idioma fromTotalString(String texto) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.nombreIdioma.equalsIgnoreCase(texto)) {
                return idioma;

            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + texto);
    }
}
