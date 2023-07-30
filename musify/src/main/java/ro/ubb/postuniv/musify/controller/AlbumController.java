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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.postuniv.musify.dto.AlbumDetailViewDto;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.AlbumViewDto;
import ro.ubb.postuniv.musify.service.AlbumService;

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

        return new ResponseEntity<>(albumService.create(albumDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDto> update(@PathVariable Integer id, @RequestBody @Valid AlbumDto albumDto) {
        return new ResponseEntity<>(albumService.update(id, albumDto), HttpStatus.OK);
    }

    @PostMapping("/{albumId}/add-song/{songId}")
    public ResponseEntity<AlbumDetailViewDto> addSong(@PathVariable Integer albumId, @PathVariable Integer songId) {
        return new ResponseEntity<>(albumService.addSong(albumId, songId), HttpStatus.OK);
    }

    @PostMapping("/{albumId}/remove-song/{songId}")
    public ResponseEntity<AlbumDetailViewDto> removeSong(@PathVariable Integer albumId, @PathVariable Integer songId) {
        return new ResponseEntity<>(albumService.removeSong(albumId, songId), HttpStatus.OK);
    }

    @PostMapping("/{albumId}/change-order")
    public ResponseEntity<AlbumDetailViewDto> changeSongOrder(@PathVariable Integer albumId, @RequestParam Integer songId, @RequestParam Integer oldPosition, @RequestParam Integer newPosition){
        return new ResponseEntity<>(albumService.changeSongOrder(albumId, songId, oldPosition, newPosition), HttpStatus.OK);
    }
}
