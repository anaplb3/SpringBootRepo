package com.example.roteiro1.service;

import com.example.roteiro1.model.Comentario;
import com.example.roteiro1.model.Disciplina;
import com.example.roteiro1.model.Like;
import com.example.roteiro1.repository.ComentarioRepository;
import com.example.roteiro1.repository.DisciplinaRepository;
import com.example.roteiro1.repository.UsuarioRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;


    @PostConstruct
    public void initDisciplinas() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Disciplina>> typeReference = new TypeReference<List<Disciplina>>() {
        };
        InputStream inputStream = ObjectMapper.class.getResourceAsStream("/json/disciplinas.json");
        try {
            List<Disciplina> disciplinas = objectMapper.readValue(inputStream, typeReference);
            disciplinaRepository.saveAll(disciplinas);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Disciplina acrescentaLike(long disciplinaId, String emailUsuario) {

        Disciplina disciplina = disciplinaRepository.findById(disciplinaId).orElseThrow(NoSuchElementException::new);
        List<Like> likes = disciplina.getLikes();

        Like like = new Like();
        like.setDisciplina(disciplina);
        like.setUsuario(usuarioRepository.findByEmail(emailUsuario));
        likes.add(like);

        disciplina.setLikes(likes);
        disciplinaRepository.save(disciplina);
        return disciplina;
    }

    public Disciplina acrescentaNota(long id, double notaJson) {
        Disciplina disciplina = disciplinaRepository.findById(id).orElseThrow(NoSuchElementException::new);
        double notaAntiga = disciplina.getNota();
        double novaNota = notaAntiga == 0.0 ? notaJson : (notaJson + disciplina.getNota()) / 2;
        disciplina.setNota(novaNota);
        disciplinaRepository.save(disciplina);
        return disciplina;
    }

    public List<Disciplina> getDisciplinas() {
        return (List<Disciplina>) this.disciplinaRepository.findAll();
    }

    public Disciplina getDisciplinabyID(long id) {
        return disciplinaRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void deleteDisciplina(long id) {
        disciplinaRepository.deleteById(id);
    }

    public List<Disciplina> getDisciplinaOrdernadaPorNota() {
        return disciplinaRepository.findByOrderByNotaDesc();
    }

    public List<Disciplina> getDisciplinaOrdernadaPorLike() {
        return disciplinaRepository.findByOrderByLikesDesc();
    }

    public Disciplina acrescentaComentario(long id, String texto) {
        Disciplina disciplina = disciplinaRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Comentario comentario = new Comentario();
        comentario.setTexto(texto);

        comentarioRepository.save(comentario);

        List<Comentario> comentarios = disciplina.getComentarios();

        if (comentarios != null) {
            comentarios.add(comentario);
            disciplina.setComentarios(comentarios);
            disciplinaRepository.save(disciplina);
        }

        return disciplina;
    }
}
