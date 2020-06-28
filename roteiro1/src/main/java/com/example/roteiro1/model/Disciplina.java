package com.example.roteiro1.model;

public class Disciplina {

    private int id;
    private String nome;
    private double nota;

    public Disciplina(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

//    @Override
//    public int compareTo(Disciplina disciplina) {
//        return this.getNota() > disciplina.getNota();
//    }
}
