package com.runninghill.sentencebuilder;

import com.runninghill.sentencebuilder.entity.Sentence;
import com.runninghill.sentencebuilder.entity.Word;
import com.runninghill.sentencebuilder.entity.WordType;
import com.runninghill.sentencebuilder.exception.WordNotFoundException;
import com.runninghill.sentencebuilder.repository.SentenceRepository;
import com.runninghill.sentencebuilder.repository.WordTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class SentenceBuilderController {

    private final SentenceRepository sentenceRepository;
    private final WordTypeRepository wordTypeRepository;

    public SentenceBuilderController(SentenceRepository sentenceRepository,
                                     WordTypeRepository wordTypeRepository) {
        this.sentenceRepository = sentenceRepository;
        this.wordTypeRepository = wordTypeRepository;
    }

    @GetMapping(path = "/v1/sentences")
    public List<Sentence> retrieveAllSentences() {
        return sentenceRepository.findAll();
    }

    @GetMapping(path = "/v1/word-type")
    public List<WordType> retrieveAllWordTypes() {
        return wordTypeRepository.findAll();
    }

    @GetMapping(path = "/v1/words/{type}")
    public List<Word> retrieveAllWordsForType(@PathVariable String type) {
        Optional<WordType> wordType = wordTypeRepository.findById(type);

        if (wordType.isEmpty()) {
            throw new WordNotFoundException("There are no words associated with this word-type: "+ wordType);
        }
        return wordType.get().getWords();
    }
    @PostMapping(path = "/v1/sentences")
    public ResponseEntity<Sentence> createNewSentence(@RequestBody Sentence sentence) {
        Sentence newSentence = sentenceRepository.save(sentence);
        URI location = getLocation(newSentence);
        return ResponseEntity.created(location).build();
    }

     URI getLocation(Sentence sentence) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(sentence.getId())
                .toUri();
    }
}
