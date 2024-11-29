package com.alurareto.literalura.principal;

import com.alurareto.literalura.modelo.*;
import com.alurareto.literalura.repository.IAutorRepository;
import com.alurareto.literalura.repository.ILibroRepository;
import com.alurareto.literalura.servicio.ConsumoAPI;
import com.alurareto.literalura.servicio.ConvierteDatos;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI;
    private ConvierteDatos convierteDatos;
    private ILibroRepository libroRepository;
    private IAutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    private final String URL_BASE = "https://gutendex.com/books?search=";

    @Autowired
    public Principal(ConsumoAPI consumoAPI, ConvierteDatos convierteDatos,
                     ILibroRepository libroRepository, IAutorRepository autorRepository) {
        this.consumoAPI = consumoAPI;
        this.convierteDatos = convierteDatos;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu(){
        System.out.println("***************************");
        System.out.println("* LITERALURA *-* TVS *-*  *");
        System.out.println("***************************");

        int option;
        try (Scanner teclado = new Scanner(System.in)) {
            do {
                option = -1;
                mostrarMenu();
                option = obtenerOpcionUsuario(teclado);
                manejarOpcionMenu(option, teclado);
            } while (option != 0);
        }

    }

    private void mostrarMenu(){
        String menu = """
                ######################################################
                #1- Buscar Libro                                     #
                #2- Lista de libros registados                       #
                #3- Lista de Libros por idioma (es, en , fr pt, la   #
                #4- Lista de autores registrados                     #
                #5- Top 10 Libros mas descargados                    #
                #6- Analisis de los datos                            #
                #----------------------------------------------------#
                #0- Salir                                            #
                ######################################################
                """;
        System.out.println(menu);
        System.out.print("Elige una opción por favor *-*: ");
    }

    private int obtenerOpcionUsuario(Scanner teclado) {
        try {
            return this.teclado.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Opción inválida. Ingresa un número del 0 al 6 ");
            this.teclado.nextLine();
            return -1;
        }
    }

    private void manejarOpcionMenu(int option, Scanner teclado) {
        this.teclado.nextLine();
        switch (option) {
            case 1 -> buscarLibro(teclado);
            case 2 -> listaLibros();
            case 3 -> listaIdioma(teclado);
            case 4 -> listaAutores();
            case 5 -> listaTop10();
            case 6 -> analizarDatos();
            case 0 -> System.out.println("Gracias por usar la aplicación, cerrando el programa :) ");
            default -> System.out.println("Opción inválida. Por favor, elige una opción del menú.");
        }
    }
    private DatosLibro obtenerDatosDelUsuario(Scanner teclado) {
        System.out.print("¿Qué libro deseas buscar hoy? :) :  ");
        var title = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + title.toLowerCase().replace(" ", "+"));

        DatosResultado dataResult = convierteDatos.obtenerDatos(json, DatosResultado.class);
        return dataResult.resultados().stream()
                .filter(b -> b.titulo().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }
    @Transactional
    private void buscarLibro(Scanner teclado) {
        try {
            DatosLibro datosLibro = obtenerDatosDelUsuario(teclado);
            if (datosLibro != null) {
                Libro libro = new Libro(datosLibro);
                for (DatosAutor datosAutor : datosLibro.autores()) {
                    Autor autor;
                    Optional<Autor> existeAutor = autorRepository.findAutorByNombre(datosAutor.nombre());
                    if (existeAutor.isPresent()) {
                        autor = existeAutor.get();
                    } else {
                        autor = new Autor(datosAutor);
                        autorRepository.save(autor);
                    }
                    libro.addAutor(autor);
                }
                libroRepository.save(libro);
                System.out.println("El libro se guardo correctamente *-*: \n" + libro);
            } else {
                System.out.println("El libro no se encontró en la API. :( ");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listaLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("Aun no hay libros registrados. ^-^ ");
        } else {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo, Comparator.nullsLast(String::compareTo)))
                    .forEach(System.out::println);
        }
    }

    public void listaIdioma(Scanner teclado) {
        System.out.println("Ingresa el idioma por el que deseas filtrar los libros: ");
        var idioLibro = this.teclado.nextLine();
        var idioma = Idioma.fromString(idioLibro.toLowerCase());
        List<Libro> idiomaLibroLibro = libroRepository.findByIdioma(idioma);
        System.out.println("Libros en " + idioLibro + ":");
        idiomaLibroLibro.forEach(libro -> System.out.println(libro));
    }

    private void listaAutores() {
        autores = autorRepository.findAllAutoresWithLibros();
        if (autores.isEmpty()) {
            System.out.println("No hay datos de autores ^-^ ");
        } else {
            autores.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(autor -> System.out.println(autor));
        }
    }

    private void listaTop10() {
        List<Libro> top10Libros =libroRepository.findTop10ByOrderByNumeroDeDescargasDesc();
        top10Libros.forEach(libro -> System.out.println(libro));
    }

    private void analizarDatos() {
        List<Libro> libros = libroRepository.findAll();
        DoubleSummaryStatistics estadisticasDescargas = libros.stream()
                .filter(libro -> libro.getNumeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));
        System.out.println("---------------------------");
        System.out.println("Análisis de las descargas de los libros:");
        System.out.println("Promedio de descargas: " + Math.round(estadisticasDescargas.getAverage() * 100.00)/100.00);
        System.out.println("Máximo de descargas: " + estadisticasDescargas.getMax());
        System.out.println("Mínimo de descargas: " + estadisticasDescargas.getMin());
        System.out.println("Número de registros evaluados: " + estadisticasDescargas.getCount());
        System.out.println("---------------------------");
    }

}
