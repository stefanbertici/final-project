package ro.ubb.postuniv.musify.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.AlbumDTO;
import ro.ubb.postuniv.musify.dto.SongViewDTO;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.service.AlbumService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/album")
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping("/{id}/songs")
    public ResponseEntity<List<SongViewDTO>> readSongsByAlbumId(@PathVariable Integer id) {
        return new ResponseEntity<>(albumService.readSongsByAlbumId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AlbumDTO> createAlbum(@RequestBody @Valid AlbumDTO albumDTO) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can create new albums");
        }

        return new ResponseEntity<>(albumService.createAlbum(albumDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(@PathVariable Integer id, @RequestBody @Valid AlbumDTO albumDTO) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can update albums");
        }

        return new ResponseEntity<>(albumService.updateAlbum(id, albumDTO), HttpStatus.OK);
    }
}
