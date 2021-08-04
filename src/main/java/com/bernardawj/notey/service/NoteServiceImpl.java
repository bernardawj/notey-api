package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.NoteDTO;
import com.bernardawj.notey.exception.NoteServiceException;
import com.bernardawj.notey.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "noteService")
@Transactional
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        return new ArrayList<>();
    }

    @Override
    public NoteDTO getSingleNote(Integer id) throws NoteServiceException {
        return null;
    }

    @Override
    public NoteDTO addNote(NoteDTO noteDTO) throws NoteServiceException {
        return null;
    }

    @Override
    public NoteDTO updateNote(Integer id, NoteDTO noteDTO) throws NoteServiceException {
        return null;
    }

    @Override
    public Void deleteNote(Integer id) throws NoteServiceException {
        return null;
    }
}
