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

import java.util.List;

@RestController
@RequestMapping("/v1/api/disciplinas")
public class DisciplinaController {
    @Autowired
    DisciplinaService disciplinaService;

    @GetMapping
    public ResponseEntity<?> getDisciplinas() {
        return new ResponseEntity<>(disciplinaService.getDisciplinas(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postDisciplinas(@RequestBody Disciplina disciplina) {
        disciplina.setId(disciplinaService.getDisciplinas().size());
        disciplinaService.createDisciplina(disciplina);
        return new ResponseEntity<>(disciplina, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDisciplinaById(@PathVariable int id) {
        Disciplina d = disciplinaService.getDisciplinabyID(id);
        if(d == null) {
            return new ResponseEntity<>(d, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(d, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/ranking")
    public ResponseEntity<?> getRankedDisciplina() {
        return new ResponseEntity<>(disciplinaService.getOrdered(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/nome", consumes = "application/json")
    public ResponseEntity<?> putDisciplinaNome(@PathVariable int id, @RequestBody String json) {
        JSONParser parser = new JSONParser();
        JSONObject json2 = null;
         try {
             json2 = (JSONObject) parser.parse(json);
         } catch (ParseException e) {
             return new ResponseEntity<>("Json não pôde ser formatado.", HttpStatus.BAD_REQUEST);
         }

        Disciplina d = disciplinaService.getDisciplinabyID(id);
        if (d == null) {
            return new ResponseEntity<>(d, HttpStatus.NOT_FOUND);
        } else {
            d.setNome(json2.get("nome").toString());
            return new ResponseEntity<>(d, HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}/nota")
    public ResponseEntity<?> putDisciplinaNota(@PathVariable int id, @RequestBody String json) {
        JSONParser parser = new JSONParser();
        JSONObject json2 = null;
        try {
            json2 = (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            return new ResponseEntity<>("Json não pôde ser formatado.", HttpStatus.BAD_REQUEST);
        }
        Disciplina d = disciplinaService.getDisciplinabyID(id);
        if (d == null) {
            return new ResponseEntity<>(d, HttpStatus.NOT_FOUND);
        } else {
            d.setNota((double) json2.get("nota"));
            return new ResponseEntity<>(d, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDisciplina(@PathVariable int id) {
        Disciplina d = disciplinaService.getDisciplinabyID(id);
        disciplinaService.deleteDisciplina(id);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

}
