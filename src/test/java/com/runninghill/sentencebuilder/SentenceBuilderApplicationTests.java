package com.runninghill.sentencebuilder;

import com.runninghill.sentencebuilder.entity.Sentence;
import com.runninghill.sentencebuilder.entity.WordType;
import com.runninghill.sentencebuilder.exception.WordNotFoundException;
import com.runninghill.sentencebuilder.repository.SentenceRepository;
import com.runninghill.sentencebuilder.repository.WordTypeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SentenceBuilderApplicationTests {

	@Mock
	private SentenceRepository sentenceRepository;

	@Mock
	private WordTypeRepository wordTypeRepository;

	private SentenceBuilderController service;

	@BeforeEach
	public void init() {
		service = new SentenceBuilderController(sentenceRepository, wordTypeRepository);
	}
	@AfterEach
	public void shutdown(){
		//
	}
	@Test
	void testRetrieveAllSentences() {
		SentenceBuilderController spy = spy(service);
		List<Sentence> sentences = mock(List.class);
		when(sentenceRepository.findAll()).thenReturn(sentences);
		Assertions.assertInstanceOf(List.class, spy.retrieveAllSentences());

		verify(sentenceRepository).findAll();
	}
	@Test
	void testRetrieveAllWordTypes() {
		SentenceBuilderController spy = spy(service);
		List<WordType> wordTypes = mock(List.class);
		when(wordTypeRepository.findAll()).thenReturn(wordTypes);
		Assertions.assertInstanceOf(List.class, spy.retrieveAllWordTypes()); //test

		verify(wordTypeRepository).findAll();
	}

	@Test
	void testRetrieveAllWordsForTypeWhenTheListIsNotFound() {
		SentenceBuilderController spy = spy(service);
		Optional<WordType> wordType = Optional.ofNullable(mock(WordType.class));
		String type = "type";
		when(wordTypeRepository.findById(type)).thenReturn(wordType);
		when(wordType.isEmpty()).thenReturn(true);

		doThrow(WordNotFoundException.class).when(wordType.isEmpty());

		Assertions.assertInstanceOf(WordNotFoundException.class, spy.retrieveAllWordsForType(type)); //test

		verify(wordTypeRepository).findById(type);
		verify(wordType).isEmpty();

		verifyNoMoreInteractions(wordType, spy);
	}
	@Test
	void testRetrieveAllWordsForTypeWhenTheListIsFound() {
		SentenceBuilderController spy = spy(service);
		Optional<WordType> wordType = Optional.ofNullable(mock(WordType.class));
		String type = "type";
		List words = mock(List.class);

		when(wordTypeRepository.findById(type)).thenReturn(wordType);
		when(wordType.isEmpty()).thenReturn(false);
		when(wordType.get().getWords()).thenReturn(words);

		Assertions.assertInstanceOf(List.class, spy.retrieveAllWordsForType(type)); //test

		verify(wordTypeRepository).findById(type);
		verify(wordType).isEmpty();
		verify(wordType).get().getWords();
	}

	@Test
	void testCreateNewSentence() {
		SentenceBuilderController spy = spy(service);
		Sentence sentence = mock(Sentence.class);
		Sentence newSentence = mock(Sentence.class);
		URI location = mock(URI.class);

		when(spy.getLocation(newSentence)).thenReturn(location);
		when(sentenceRepository.save(sentence)).thenReturn(newSentence);

		Assertions.assertInstanceOf(ResponseEntity.class, spy.createNewSentence(sentence));

		verify(sentenceRepository).save(sentence);
	}

}
