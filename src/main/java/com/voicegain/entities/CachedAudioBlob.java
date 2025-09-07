package com.voicegain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity // Marks this class as a JPA entity
@Table(name = "cached_audio_blob") // Specifies the database table name
public class CachedAudioBlob {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies auto-generation strategy for the primary key
    private Long id;

    @Column(name = "text", nullable = false, length = 300) // Maps to 'text' column, not null, max length 100
    private String text;

    @Column(name = "voice", nullable = false, length = 100) // Maps to 'voice' column, not null, max length 100
    private String voice;

    @Column(name = "audio_blob", nullable = false) // Maps to 'audio_blob' column, not null
    private byte[] audioBlob;

}
