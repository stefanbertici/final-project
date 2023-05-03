package ro.ubb.postuniv.musify.controller;

import ro.ubb.postuniv.musify.dto.SearchViewDTO;
import ro.ubb.postuniv.musify.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/{searchTerm}")
    public ResponseEntity<SearchViewDTO> search(@PathVariable String searchTerm) {
        return new ResponseEntity<>(searchService.search(searchTerm), HttpStatus.OK);
    }
}
