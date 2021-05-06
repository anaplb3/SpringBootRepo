package com.example.roteiro1.repository;

import com.example.roteiro1.model.Disciplina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {

    List<Disciplina> findByOrderByNotaDesc();

    List<Disciplina> findByOrderByLikesDesc();

}
