package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.AlbumDetailViewDto;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.AlbumViewDto;
import ro.ubb.postuniv.musify.service.AlbumService;

import javax.validation.Valid;
import java.util.List;

import static ro.ubb.postuniv.musify.utils.checkers.UserChecker.validateAdminRole;

@RequiredArgsConstructor
@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping()
    public ResponseEntity<List<AlbumViewDto>> readAll() {
        return new ResponseEntity<>(albumService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDetailViewDto> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(albumService.readById(id), HttpStatus.OK);
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<List<AlbumDto>> readAllByArtistId(@PathVariable Integer id) {
        return new ResponseEntity<>(albumService.readAllByArtistId(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<AlbumDto> create(@RequestBody @Valid AlbumDto albumDto) {
        validateAdminRole();
        return new ResponseEntity<>(albumService.create(albumDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDto> update(@PathVariable Integer id, @RequestBody @Valid AlbumDto albumDto) {
        validateAdminRole();
        return new ResponseEntity<>(albumService.update(id, albumDto), HttpStatus.OK);
    }

    @PostMapping("/{albumId}/add-song/{songId}")
    public ResponseEntity<AlbumDetailViewDto> addSong(@PathVariable Integer albumId, @PathVariable Integer songId) {
        validateAdminRole();
        return new ResponseEntity<>(albumService.addSong(albumId, songId), HttpStatus.OK);
    }

    @PostMapping("/{albumId}/remove-song/{songId}")
    public ResponseEntity<AlbumDetailViewDto> removeSong(@PathVariable Integer albumId, @PathVariable Integer songId) {
        validateAdminRole();
        return new ResponseEntity<>(albumService.removeSong(albumId, songId), HttpStatus.OK);
    }

    @PostMapping("/{albumId}/change-order")
    public ResponseEntity<AlbumDetailViewDto> changeSongOrder(@PathVariable Integer albumId, @RequestParam Integer songId, @RequestParam Integer oldPosition, @RequestParam Integer newPosition){
        validateAdminRole();
        return new ResponseEntity<>(albumService.changeSongOrder(albumId, songId, oldPosition, newPosition), HttpStatus.OK);
    }
}
