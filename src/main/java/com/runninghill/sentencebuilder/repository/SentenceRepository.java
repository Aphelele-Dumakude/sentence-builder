package com.runninghill.sentencebuilder.repository;

import com.runninghill.sentencebuilder.entity.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
public interface SentenceRepository extends JpaRepository<Sentence, Long> {
}
