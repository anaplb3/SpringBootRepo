package com.example.roteiro1.service;

import com.example.roteiro1.model.Disciplina;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DisciplinaService {
    List<Disciplina> disciplinas;

    public DisciplinaService() {
        this.disciplinas = new ArrayList<>();
    }

    public List<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    public Disciplina getDisciplinabyID(int id) {
        for (Disciplina disciplina: this.disciplinas) {
            if (disciplina.getId() == id) {
                return disciplina;
            }
        }
        return null;
    }

    public boolean createDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
        return true;
    }

    public boolean deleteDisciplina(int id) {
        Disciplina disciplina = null;
        for (Disciplina d: this.disciplinas) {
            if(d.getId() == id) {
                disciplina = d;
            }
        }

        if (disciplina != null) {
            this.disciplinas.remove(disciplina);
            return true;
        }
        return false;
    }

    public List<Disciplina> getOrdered() {
        Collections.sort(disciplinas, new Comparator<Disciplina>() {
            @Override
            public int compare(Disciplina disciplina, Disciplina t1) {
                if (t1.getNota() == disciplina.getNota()) {
                    return 0;
                }
                else if (t1.getNota() < disciplina.getNota()) {
                    return -1;
                }
                return 1;
            }
        });
        return disciplinas;
    }
}
