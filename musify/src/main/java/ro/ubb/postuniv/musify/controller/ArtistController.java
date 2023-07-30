package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.ArtistDto;
import ro.ubb.postuniv.musify.dto.ArtistViewDto;
import ro.ubb.postuniv.musify.service.ArtistService;

import javax.validation.Valid;
import java.util.List;

import static ro.ubb.postuniv.musify.utils.checkers.UserChecker.validateAdminRole;

@RequiredArgsConstructor
@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping()
    public ResponseEntity<List<ArtistViewDto>> readAll() {
        validateAdminRole();
        return new ResponseEntity<>(artistService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(artistService.readById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ArtistDto> create(@RequestBody @Valid ArtistDto artistDto) {
        validateAdminRole();
        return new ResponseEntity<>(artistService.create(artistDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> update(@PathVariable Integer id, @RequestBody @Valid ArtistDto artistDto) {
        validateAdminRole();
        return new ResponseEntity<>(artistService.update(id, artistDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArtistDto> delete(@PathVariable Integer id) {
        validateAdminRole();
        return new ResponseEntity<>(artistService.delete(id), HttpStatus.OK);
    }
}


