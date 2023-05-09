package ro.ubb.postuniv.musify.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.SongDTO;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.service.SongService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/song")
public class SongController {
    private final SongService songService;

    @PostMapping("/")
    public ResponseEntity<SongDTO> createSong(@RequestBody @Valid SongDTO songDTO) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can create new songs");
        }

        return new ResponseEntity<>(songService.createSong(songDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(@PathVariable Integer id, @RequestBody @Valid SongDTO songDTO) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can update songs");
        }

        return new ResponseEntity<>(songService.updateSong(id, songDTO), HttpStatus.OK);
    }
}
