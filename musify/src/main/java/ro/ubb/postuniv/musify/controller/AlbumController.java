package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.AlbumDetailViewDto;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.AlbumViewDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.service.AlbumService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/{id}/songs")
    public ResponseEntity<List<SongViewDto>> readByAlbumId(@PathVariable Integer id) {
        return new ResponseEntity<>(albumService.readSongsByAlbumId(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<AlbumViewDto>> readAll() {
        return new ResponseEntity<>(albumService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDetailViewDto> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(albumService.readById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<AlbumDto> create(@RequestBody @Valid AlbumDto albumDto) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(albumService.create(albumDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDto> update(@PathVariable Integer id, @RequestBody @Valid AlbumDto albumDto) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(albumService.update(id, albumDto), HttpStatus.OK);
    }
}
