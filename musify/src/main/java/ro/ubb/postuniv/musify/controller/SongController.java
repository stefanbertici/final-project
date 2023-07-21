package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.SongDto;
import ro.ubb.postuniv.musify.service.SongService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    @PostMapping()
    public ResponseEntity<SongDto> create(@RequestBody @Valid SongDto songDto) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(songService.create(songDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDto> update(@PathVariable Integer id, @RequestBody @Valid SongDto songDto) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(songService.update(id, songDto), HttpStatus.OK);
    }
}
