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
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.BandDto;
import ro.ubb.postuniv.musify.service.BandService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bands")
public class BandController {

    private final BandService bandService;

    @GetMapping("/{id}/albums")
    public ResponseEntity<List<AlbumDto>> readAlbumsByBandId(@PathVariable Integer id) {
        return new ResponseEntity<>(bandService.readAlbumsByBandId(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BandDto> create(@RequestBody @Valid BandDto bandDto) {
        return new ResponseEntity<>(bandService.create(bandDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BandDto> update(@PathVariable Integer id, @RequestBody @Valid BandDto bandDto) {
        return new ResponseEntity<>(bandService.update(id, bandDto), HttpStatus.OK);
    }
}
