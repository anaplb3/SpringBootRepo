package com.example.roteiro1.controller;

import com.example.roteiro1.model.Disciplina;
import com.example.roteiro1.service.DisciplinaService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/api/disciplinas")
public class DisciplinaController {
    @Autowired
    DisciplinaService disciplinaService;

    @GetMapping
    public ResponseEntity<?> getDisciplinas() {
        return new ResponseEntity<>(disciplinaService.getDisciplinas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDisciplinaById(@PathVariable int id) {
        try {
            Disciplina disciplina = disciplinaService.getDisciplinabyID(id);
            return new ResponseEntity<>(disciplina, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Id não foi encontrado.", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/ranking/notas")
    public ResponseEntity<?> getDisciplinaOrdernadaNota() {
        return new ResponseEntity<>(disciplinaService.getDisciplinaOrdernadaPorNota(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/nome", consumes = "application/json")
    public ResponseEntity<?> putDisciplinaNome(@PathVariable int id, @RequestBody String json) {
        JSONParser parser = new JSONParser();
        JSONObject json2;
         try {
             json2 = (JSONObject) parser.parse(json);
         } catch (ParseException e) {
             return new ResponseEntity<>("Json não pôde ser formatado.", HttpStatus.BAD_REQUEST);
         }

        Disciplina d = disciplinaService.getDisciplinabyID(id);
        if (d == null) {
            return new ResponseEntity<>("Id não foi encontrado.", HttpStatus.NOT_FOUND);
        } else {
            d.setNome(json2.get("nome").toString());
            return new ResponseEntity<>(d, HttpStatus.OK);
        }
    }

    @PatchMapping("/nota/{id}")
    public ResponseEntity<?> putDisciplinaNota(@PathVariable int id, @RequestBody String json) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json2 = (JSONObject) parser.parse(json);
            double notaJson = (double) json2.get("nota");
            Disciplina disciplina = disciplinaService.acrescentaNota(id, notaJson);
            return new ResponseEntity<>(disciplina, HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity<>("Json não pôde ser formatado.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Id não foi encontrado.", HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDisciplina(@PathVariable int id) {
        Disciplina d = disciplinaService.getDisciplinabyID(id);
        disciplinaService.deleteDisciplina(id);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @PatchMapping("/likes/{id}")
    public ResponseEntity<?> incrementaLike(@PathVariable int id, @RequestBody String email) {
        try {
            Disciplina disciplina = disciplinaService.acrescentaLike(id, email);
            return new ResponseEntity<>(disciplina, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ranking/likes")
    public ResponseEntity<?> getDisciplinaOrdernadaLike() {
        return new ResponseEntity<>(disciplinaService.getDisciplinaOrdernadaPorLike(), HttpStatus.OK);
    }

    @PostMapping("/comentarios/{id}")
    public ResponseEntity<?> adicionaComentario(@PathVariable int id, @RequestBody String json) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json2 = (JSONObject) parser.parse(json);
            String comentario = (String) json2.get("comentario");
            Disciplina disciplina = disciplinaService.acrescentaComentario(id, comentario);
            return new ResponseEntity<>(disciplina, HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity<>("Json não pôde ser formatado.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Id não foi encontrado.", HttpStatus.NOT_FOUND);
        }
    }

}
