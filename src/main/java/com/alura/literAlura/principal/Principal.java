package com.alura.literAlura.principal;

import com.alura.literAlura.model.*;
import com.alura.literAlura.repository.AutorRepository;
import com.alura.literAlura.repository.LibroRepository;
import com.alura.literAlura.service.ConsumoAPI;
import com.alura.literAlura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.repositorioLibro = libroRepository;
        this.repositorioAutor = autorRepository;
    }


    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    SELECIONA UNA OPCIÓN DEL MENÚ:
                    1 - Buscar libro por título
                    2 - Listar libros buscados
                    3 - Listar autores registrados
                    4 - Listar autores vivos hasta un determinado año
                    5 - Listar libros buscados por idiomas

                    0 - Salir
                    """;
            System.out.println(menu);


            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosWeb();
                    break;
                case 2:
                    listarLibrosBuscados();
                    break;

                case 3:
                    listarAutores();
                    break;

                case 4:
                    buscarPorFechaDeFallecimiento();
                    break;

                case 5:
                    filtrarPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void buscarLibrosWeb(){
        System.out.println("Ingresa el nombre del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosGenerales.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()){
            DatosLibro datosLibro = libroBuscado.get();
            System.out.println(libroBuscado.get());

            if (!verificarExistencia(datosLibro)){
                Libro libro = new Libro(datosLibro);
                DatosAutor datosAutor = datosLibro.autor().get(0);
                Optional<Autor> autorOptional = repositorioAutor.findByName(datosAutor.nombre());

                if (autorOptional.isPresent()){
                    Autor autorExistente = autorOptional.get();
                    libro.setAutor(autorExistente);
                    autorExistente.getLibrosEscritos().add(libro);
                    repositorioAutor.save(autorExistente);
                }else {
                    Autor nuevoAutor = new Autor(datosAutor);
                    libro.setAutor(nuevoAutor);
                    nuevoAutor.getLibrosEscritos().add(libro);
                    repositorioAutor.save(nuevoAutor);
                }

                repositorioLibro.save(libro);

            }else {
                System.out.println("El libro ya está registrado en la base de datos");
            }
        }else {
            System.out.println("Libro no encontrado");
        }
    }



    private void listarLibrosBuscados(){
        List<Libro> libros = repositorioLibro.findAll();
        if (!libros.isEmpty()){
            System.out.println("Libros encontrados: ");
            libros.forEach(System.out::println);
        }
    }

    private void listarAutores(){
        List<Autor> autores = repositorioAutor.findAll();
        if (!autores.isEmpty()){
            System.out.println("Autores registrados: ");
            autores.forEach(System.out::println);
        }

    }


    private void buscarPorFechaDeFallecimiento(){
        System.out.println("Ingresa un año para filtrar los autores: ");


        if (teclado.hasNextInt()) {
            int fechaUsuario = teclado.nextInt();
            List<Autor> autores = repositorioAutor.findAutoresVivos(fechaUsuario);

            if (!autores.isEmpty()){
                System.out.println("Los autores vivos hasta el año " + fechaUsuario + " son:");
                autores.forEach(System.out::println);
            }else {
                System.out.println("Ningun autor vivo hasta esa fecha");
            }
        } else {
            System.out.println("Entrada invalida");
            teclado.next();
        }
    }

    private void filtrarPorIdioma(){
        System.out.println("Ingresa el idioma por el que quieres filtrar: ");
        System.out.println("-ESPANOL \n-INGLES \n-FRANCES \n-PORTUGUES");
        String idioma = teclado.nextLine();

        List<Libro> librosPorIdioma = repositorioLibro.findByIdioma(idioma.toUpperCase());

        if (!librosPorIdioma.isEmpty()){
            System.out.println("Los libros en el idioma " + idioma + " son: ");
            System.out.println(librosPorIdioma);
        }else {
            System.out.println("No se encontró ningún libro en el idioma " + idioma);
        }
    }

    private boolean verificarExistencia(DatosLibro datosLibro){
        Libro libro = new Libro(datosLibro);
        return repositorioLibro.verificarExistencia(libro.getTitle());
    }

}
