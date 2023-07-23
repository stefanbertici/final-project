package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.SongDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.service.SongService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/artist={id}")
    public ResponseEntity<List<SongViewDto>> readSongsByArtistId(@PathVariable Integer id) {
        return new ResponseEntity<>(songService.readAllByArtistId(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<SongViewDto> create(@RequestBody @Valid SongDto songDto) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(songService.create(songDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongViewDto> update(@PathVariable Integer id, @RequestBody @Valid SongDto songDto) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(songService.update(id, songDto), HttpStatus.OK);
    }
}
