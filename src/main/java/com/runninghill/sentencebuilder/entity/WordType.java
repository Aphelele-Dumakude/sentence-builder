package com.runninghill.sentencebuilder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class WordType {

    @Id
    @Column(name = "id")
    private String wordType;

    @OneToMany(mappedBy = "wordType")
    private List<Word> words;
}
