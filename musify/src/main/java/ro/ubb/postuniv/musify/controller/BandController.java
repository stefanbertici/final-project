package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.BandDto;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.service.BandService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/band")
public class BandController {

    private final BandService bandService;

    @GetMapping("/{id}/albums")
    public ResponseEntity<List<AlbumDto>> readAlbumsByBandId(@PathVariable Integer id) {
        return new ResponseEntity<>(bandService.readAlbumsByBandId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BandDto> createBand(@RequestBody @Valid BandDto bandDto) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can create new bands");
        }

        return new ResponseEntity<>(bandService.createBand(bandDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BandDto> updateBand(@PathVariable Integer id, @RequestBody @Valid BandDto bandDto) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can update bands");
        }

        return new ResponseEntity<>(bandService.updateBand(id, bandDto), HttpStatus.OK);
    }
}
