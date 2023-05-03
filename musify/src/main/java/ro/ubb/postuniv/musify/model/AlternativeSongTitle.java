package ro.ubb.postuniv.musify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "alternative_song_titles")
public class AlternativeSongTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "alternative_title")
    private String alternativeTitle;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;
}