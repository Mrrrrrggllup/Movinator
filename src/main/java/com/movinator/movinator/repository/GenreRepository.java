package com.movinator.movinator.repository;

import com.movinator.movinator.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {

    Genre findByGenreName(String name);
    List<Genre> findByOrderByGenreName();
}