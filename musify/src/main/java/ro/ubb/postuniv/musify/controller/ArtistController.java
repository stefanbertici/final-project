package ro.ubb.postuniv.musify.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.postuniv.musify.dto.ArtistDto;
import ro.ubb.postuniv.musify.dto.ArtistViewDto;
import ro.ubb.postuniv.musify.service.ArtistService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping()
    public ResponseEntity<List<ArtistViewDto>> readAll() {
        return new ResponseEntity<>(artistService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> readById(@PathVariable Integer id) {
        return new ResponseEntity<>(artistService.readById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ArtistDto> create(@RequestBody @Valid ArtistDto artistDto) {
        return new ResponseEntity<>(artistService.create(artistDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> update(@PathVariable Integer id, @RequestBody @Valid ArtistDto artistDto) {
        return new ResponseEntity<>(artistService.update(id, artistDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArtistDto> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(artistService.delete(id), HttpStatus.OK);
    }
}


