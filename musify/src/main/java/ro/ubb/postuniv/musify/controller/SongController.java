package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.SongDto;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.service.SongService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    @PostMapping("/")
    public ResponseEntity<SongDto> createSong(@RequestBody @Valid SongDto songDto) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can create new songs");
        }

        return new ResponseEntity<>(songService.createSong(songDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDto> updateSong(@PathVariable Integer id, @RequestBody @Valid SongDto songDto) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can update songs");
        }

        return new ResponseEntity<>(songService.updateSong(id, songDto), HttpStatus.OK);
    }
}
