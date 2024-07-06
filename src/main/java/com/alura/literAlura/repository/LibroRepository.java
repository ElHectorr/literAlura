package com.alura.literAlura.repository;

import com.alura.literAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT COUNT(b) > 0 FROM Libro b WHERE b.title LIKE %:title%")
    Boolean verificarExistencia(@Param("title") String title);

    @Query(value = "SELECT * FROM libros WHERE idiomas LIKE %:language%", nativeQuery = true)
    List<Libro> findByIdioma(@Param("language") String language);
}
