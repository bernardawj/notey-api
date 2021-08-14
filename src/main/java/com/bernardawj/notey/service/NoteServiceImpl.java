package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.note.NoteDTO;
import com.bernardawj.notey.entity.Note;
import com.bernardawj.notey.exception.NoteServiceException;
import com.bernardawj.notey.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "noteService")
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final String NOTE_NOT_FOUND = "NoteService.NOTE_NOT_FOUND";

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        Iterable<Note> notes = noteRepository.findAll();

        List<NoteDTO> notesDTO = new ArrayList<>();
        notes.forEach(note -> {
            notesDTO.add(new NoteDTO(note.getId(), note.getTitle(), note.getWrittenContent(), note.getCreatedAt(),
                    note.getUpdatedAt()));
        });

        return notesDTO;
    }

    @Override
    public NoteDTO getSingleNote(Integer id) throws NoteServiceException {
        // Check if note exists within the database
        Optional<Note> optNote = noteRepository.findById(id);
        Note note = optNote.orElseThrow(() -> new NoteServiceException(NOTE_NOT_FOUND));

        return new NoteDTO(note.getId(), note.getTitle(), note.getWrittenContent(), note.getCreatedAt(),
                note.getUpdatedAt());
    }

    @Override
    public NoteDTO addNote(NoteDTO noteDTO) throws NoteServiceException {
        // Current timestamp
        LocalDateTime timeStamp = LocalDateTime.now(ZoneOffset.UTC);

        // Save note into database
        Note note = new Note(noteDTO.getTitle(), noteDTO.getWrittenContent());
        note.setCreatedAt(timeStamp);
        note.setUpdatedAt(timeStamp);
        noteRepository.save(note);

        return new NoteDTO(note.getId(), note.getTitle(), note.getWrittenContent(), note.getCreatedAt(),
                note.getUpdatedAt());
    }

    @Override
    public NoteDTO updateNote(Integer id, NoteDTO noteDTO) throws NoteServiceException {
        // Check if note exists within the database
        Optional<Note> optNote = noteRepository.findById(id);
        Note note = optNote.orElseThrow(() -> new NoteServiceException(NOTE_NOT_FOUND));

        // Update and save note
        note.setTitle(noteDTO.getTitle());
        note.setWrittenContent(noteDTO.getWrittenContent());
        note.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
        noteRepository.save(note);

        return new NoteDTO(note.getId(), note.getTitle(), note.getWrittenContent(), note.getCreatedAt(),
                note.getUpdatedAt());
    }

    @Override
    public void deleteNote(Integer id) throws NoteServiceException {
        // Check if note exists within the database
        Optional<Note> optNote = noteRepository.findById(id);
        optNote.orElseThrow(() -> new NoteServiceException(NOTE_NOT_FOUND));

        // Delete note
        noteRepository.deleteById(id);
    }
}
