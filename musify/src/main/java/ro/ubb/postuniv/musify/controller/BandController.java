package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.BandDto;
import ro.ubb.postuniv.musify.service.BandService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;
import java.util.List;

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
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(bandService.create(bandDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BandDto> update(@PathVariable Integer id, @RequestBody @Valid BandDto bandDto) {
        UserChecker.validateAdminRole();
        return new ResponseEntity<>(bandService.update(id, bandDto), HttpStatus.OK);
    }
}
