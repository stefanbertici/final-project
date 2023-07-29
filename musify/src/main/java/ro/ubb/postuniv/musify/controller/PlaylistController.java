package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.PlaylistDto;
import ro.ubb.postuniv.musify.dto.PlaylistViewDto;
import ro.ubb.postuniv.musify.service.PlaylistService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping()
    public ResponseEntity<List<PlaylistViewDto>> readAll() {
        return new ResponseEntity<>(playlistService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistViewDto> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.readById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PlaylistViewDto> create(@RequestBody @Valid PlaylistDto playlistDto) {
        return new ResponseEntity<>(playlistService.create(playlistDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistViewDto> update(@PathVariable Integer id, @RequestBody @Valid PlaylistDto playlistDto) {
        return new ResponseEntity<>(playlistService.update(id, playlistDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaylistViewDto> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/add-song/{songId}")
    public ResponseEntity<PlaylistViewDto> addSongToPlaylist(@PathVariable Integer playlistId, @PathVariable Integer songId) {
        return new ResponseEntity<>(playlistService.addSongToPlaylist(playlistId, songId), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/remove-song/{songId}")
    public ResponseEntity<PlaylistViewDto> removeSongFromPlaylist(@PathVariable Integer playlistId, @PathVariable Integer songId) {
        return new ResponseEntity<>(playlistService.removeSongFromPlaylist(playlistId, songId), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/add-album/{albumId}")
    public ResponseEntity<PlaylistViewDto> addAlbumToPlaylist(@PathVariable Integer playlistId, @PathVariable Integer albumId) {
        return new ResponseEntity<>(playlistService.addAlbumToPlaylist(playlistId, albumId), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/change-order")
    public ResponseEntity<PlaylistViewDto> changeSongOrder(@PathVariable Integer playlistId, @RequestParam Integer songId, @RequestParam Integer oldPosition, @RequestParam Integer newPosition){
        return new ResponseEntity<>(playlistService.changeSongOrder(playlistId, songId, oldPosition, newPosition), HttpStatus.OK);
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<PlaylistViewDto> follow(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.follow(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<PlaylistViewDto> unfollow(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.unfollow(id), HttpStatus.OK);
    }
}
