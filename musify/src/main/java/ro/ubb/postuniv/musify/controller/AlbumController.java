package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.service.AlbumService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/{id}/songs")
    public ResponseEntity<List<SongViewDto>> readSongsByAlbumId(@PathVariable Integer id) {
        return new ResponseEntity<>(albumService.readSongsByAlbumId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody @Valid AlbumDto albumDto) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can create new albums");
        }

        return new ResponseEntity<>(albumService.createAlbum(albumDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable Integer id, @RequestBody @Valid AlbumDto albumDto) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can update albums");
        }

        return new ResponseEntity<>(albumService.updateAlbum(id, albumDto), HttpStatus.OK);
    }
}
