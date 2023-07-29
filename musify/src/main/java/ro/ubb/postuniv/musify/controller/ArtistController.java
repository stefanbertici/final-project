package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.ArtistDto;
import ro.ubb.postuniv.musify.dto.ArtistViewDto;
import ro.ubb.postuniv.musify.service.ArtistService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping()
    public ResponseEntity<List<ArtistViewDto>> readAll() {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(artistService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(artistService.readById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ArtistDto> create(@RequestBody @Valid ArtistDto artistDto) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(artistService.create(artistDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> update(@PathVariable Integer id, @RequestBody @Valid ArtistDto artistDto) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(artistService.update(id, artistDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArtistDto> delete(@PathVariable Integer id) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(artistService.delete(id), HttpStatus.OK);
    }
}


