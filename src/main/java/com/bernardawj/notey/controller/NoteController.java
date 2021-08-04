package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.NoteDTO;
import com.bernardawj.notey.exception.NoteServiceException;
import com.bernardawj.notey.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/note")
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping()
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<NoteDTO> notesDTO = this.noteService.getAllNotes();
        return new ResponseEntity<>(notesDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<NoteDTO> getSingleNote(@PathVariable("id") Integer id) throws NoteServiceException {
        NoteDTO notesDTO = this.noteService.getSingleNote(id);
        return new ResponseEntity<>(notesDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<NoteDTO> addNote(NoteDTO noteDTO) throws NoteServiceException {
        NoteDTO notesDTO = this.noteService.addNote(noteDTO);
        return new ResponseEntity<>(notesDTO, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable("id") Integer id, NoteDTO noteDTO) throws NoteServiceException {
        NoteDTO notesDTO = this.noteService.updateNote(id, noteDTO);
        return new ResponseEntity<>(notesDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") Integer id) throws NoteServiceException {
        this.noteService.deleteNote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
