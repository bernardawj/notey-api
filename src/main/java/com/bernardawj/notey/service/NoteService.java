package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.NoteDTO;
import com.bernardawj.notey.exception.NoteServiceException;

import java.util.List;

public interface NoteService {

    List<NoteDTO> getAllNotes();

    NoteDTO getSingleNote(Integer id) throws NoteServiceException;

    NoteDTO addNote(NoteDTO noteDTO) throws NoteServiceException;

    NoteDTO updateNote(Integer id, NoteDTO noteDTO) throws NoteServiceException;

    void deleteNote(Integer id) throws NoteServiceException;
}
