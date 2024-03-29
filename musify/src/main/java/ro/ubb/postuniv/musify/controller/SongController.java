package ro.ubb.postuniv.musify.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.postuniv.musify.dto.SongDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.service.SongService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    @GetMapping()
    public ResponseEntity<List<SongViewDto>> readAll() {
        return new ResponseEntity<>(songService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongViewDto> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(songService.readById(id), HttpStatus.OK);
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<List<SongViewDto>> readAllByArtistId(@PathVariable Integer id) {
        return new ResponseEntity<>(songService.readAllByArtistId(id), HttpStatus.OK);
    }

    @GetMapping("/album/{id}")
    public ResponseEntity<List<SongViewDto>> readAllByAlbumId(@PathVariable Integer id) {
        return new ResponseEntity<>(songService.readAllByAlbumId(id), HttpStatus.OK);
    }

    @GetMapping("/playlist/{id}")
    public ResponseEntity<List<SongViewDto>> readAllByPlaylistId(@PathVariable Integer id) {
        return new ResponseEntity<>(songService.readAllByPlaylistId(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<SongViewDto> create(@RequestBody @Valid SongDto songDto) {
        return new ResponseEntity<>(songService.create(songDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongViewDto> update(@PathVariable Integer id, @RequestBody @Valid SongDto songDto) {
        return new ResponseEntity<>(songService.update(id, songDto), HttpStatus.OK);
    }
}
