package ro.ubb.postuniv.musify.controller;

import ro.ubb.postuniv.musify.dto.PlaylistDTO;
import ro.ubb.postuniv.musify.dto.PlaylistViewDTO;
import ro.ubb.postuniv.musify.dto.SongViewDTO;
import ro.ubb.postuniv.musify.service.PlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    private final PlaylistService playlistService;

    @GetMapping("/")
    public ResponseEntity<List<PlaylistDTO>> readUserPlaylists() {
        return new ResponseEntity<>(playlistService.readUserPlaylists(), HttpStatus.OK);
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<List<SongViewDTO>> readSongsByPlaylistId(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.readSongsByPlaylistId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PlaylistDTO> createPlaylist(@RequestBody @Valid PlaylistDTO playlistDTO) {
        return new ResponseEntity<>(playlistService.createPlaylist(playlistDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDTO> updatePlaylist(@PathVariable Integer id, @RequestBody @Valid PlaylistDTO playlistDTO) {
        return new ResponseEntity<>(playlistService.updatePlaylist(id, playlistDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaylistDTO> deletePlaylist(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.deletePlaylist(id), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/add/song/{songId}")
    public ResponseEntity<PlaylistViewDTO> addSongToPlaylist(@PathVariable Integer playlistId, @PathVariable Integer songId) {
        return new ResponseEntity<>(playlistService.addSongToPlaylist(playlistId, songId), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/remove/song/{songId}")
    public ResponseEntity<PlaylistViewDTO> removeSongFromPlaylist(@PathVariable Integer playlistId, @PathVariable Integer songId) {
        return new ResponseEntity<>(playlistService.removeSongFromPlaylist(playlistId, songId), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/add/album/{albumId}")
    public ResponseEntity<PlaylistViewDTO> addAlbumToPlaylist(@PathVariable Integer playlistId, @PathVariable Integer albumId) {
        return new ResponseEntity<>(playlistService.addAlbumToPlaylist(playlistId, albumId), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/changeSongOrder")
    public ResponseEntity<PlaylistViewDTO> changeSongOrder(@PathVariable Integer playlistId, @RequestParam Integer songId, @RequestParam Integer oldPosition, @RequestParam Integer newPosition){
        return new ResponseEntity<>(playlistService.changeSongOrder(playlistId, songId, oldPosition, newPosition), HttpStatus.OK);
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<PlaylistDTO> followPlaylist(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.followPlaylist(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<PlaylistDTO> unfollowPlaylist(@PathVariable Integer id) {
        return new ResponseEntity<>(playlistService.unfollowPlaylist(id), HttpStatus.OK);
    }
}
