package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.ArtistDto;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.service.ArtistService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{id}/albums")
    public ResponseEntity<List<AlbumDto>> readAlbumsByArtistId(@PathVariable Integer id) {
        return new ResponseEntity<>(artistService.readAlbumsByArtistId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ArtistDto> createArtist(@RequestBody @Valid ArtistDto artistDto) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can create new artists");
        }

        return new ResponseEntity<>(artistService.createArtist(artistDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> updateArtist(@PathVariable Integer id, @RequestBody @Valid ArtistDto artistDto) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can update artists");
        }

        return new ResponseEntity<>(artistService.updateArtist(id, artistDto), HttpStatus.OK);
    }
}


