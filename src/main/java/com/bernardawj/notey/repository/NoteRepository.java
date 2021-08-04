package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Integer> {
}
