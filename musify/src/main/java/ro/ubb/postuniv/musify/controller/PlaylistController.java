package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.PlaylistDto;
import ro.ubb.postuniv.musify.dto.PlaylistViewDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.service.PlaylistService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/")
    public ResponseEntity<List<PlaylistDto>> readUserPlaylists() {
        return new ResponseEntity<>(playlistService.readUserPlaylists(), HttpStatus.OK);
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<List<SongViewDto>> readSongsByPlaylistId(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.readSongsByPlaylistId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PlaylistDto> createPlaylist(@RequestBody @Valid PlaylistDto playlistDto) {
        return new ResponseEntity<>(playlistService.createPlaylist(playlistDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDto> updatePlaylist(@PathVariable Integer id, @RequestBody @Valid PlaylistDto playlistDto) {
        return new ResponseEntity<>(playlistService.updatePlaylist(id, playlistDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaylistDto> deletePlaylist(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.deletePlaylist(id), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/add/song/{songId}")
    public ResponseEntity<PlaylistViewDto> addSongToPlaylist(@PathVariable Integer playlistId, @PathVariable Integer songId) {
        return new ResponseEntity<>(playlistService.addSongToPlaylist(playlistId, songId), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/remove/song/{songId}")
    public ResponseEntity<PlaylistViewDto> removeSongFromPlaylist(@PathVariable Integer playlistId, @PathVariable Integer songId) {
        return new ResponseEntity<>(playlistService.removeSongFromPlaylist(playlistId, songId), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/add/album/{albumId}")
    public ResponseEntity<PlaylistViewDto> addAlbumToPlaylist(@PathVariable Integer playlistId, @PathVariable Integer albumId) {
        return new ResponseEntity<>(playlistService.addAlbumToPlaylist(playlistId, albumId), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/changeSongOrder")
    public ResponseEntity<PlaylistViewDto> changeSongOrder(@PathVariable Integer playlistId, @RequestParam Integer songId, @RequestParam Integer oldPosition, @RequestParam Integer newPosition){
        return new ResponseEntity<>(playlistService.changeSongOrder(playlistId, songId, oldPosition, newPosition), HttpStatus.OK);
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<PlaylistDto> followPlaylist(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.followPlaylist(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<PlaylistDto> unfollowPlaylist(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.unfollowPlaylist(id), HttpStatus.OK);
    }
}
