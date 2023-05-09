package ro.ubb.postuniv.musify.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.AlbumDTO;
import ro.ubb.postuniv.musify.dto.BandDTO;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.service.BandService;
import ro.ubb.postuniv.musify.utils.UserChecker;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/band")
public class BandController {
    private final BandService bandService;

    @GetMapping("/{id}/albums")
    public ResponseEntity<List<AlbumDTO>> readAlbumsByBandId(@PathVariable Integer id) {
        return new ResponseEntity<>(bandService.readAlbumsByBandId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BandDTO> createBand(@RequestBody @Valid BandDTO bandDTO) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can create new bands");
        }

        return new ResponseEntity<>(bandService.createBand(bandDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BandDTO> updateBand(@PathVariable Integer id, @RequestBody @Valid BandDTO bandDTO) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can update bands");
        }

        return new ResponseEntity<>(bandService.updateBand(id, bandDTO), HttpStatus.OK);
    }
}
