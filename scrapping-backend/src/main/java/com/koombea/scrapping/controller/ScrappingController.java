package com.koombea.scrapping.controller;

import com.koombea.scrapping.model.Scrap;
import com.koombea.scrapping.model.ScrappedPage;
import com.koombea.scrapping.service.ScrappingService;
import com.koombea.scrapping.utils.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/scrapping")
public class ScrappingController {
    @Autowired
    private ScrappingService scrappingService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Scrap scrappedPage, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ScrappedPage createdScrappedPage = null;

        try {
            createdScrappedPage = scrappingService.create(scrappedPage);
            return ResponseEntity
                    .created(uriBuilder.path("/scrapping/{id}").buildAndExpand(createdScrappedPage.getId()).toUri())
                    .headers(headers)
                    .body(createdScrappedPage);
        } catch (IOException e) {
            // Handle the exception and return a JSON response with an error code and message
            String errorMessage = "An error occurred while creating the ScrappedPage.";
            ErrorDetails.ErrorCode errorCode = ErrorDetails.ErrorCode.INTERNAL_ERROR; // You can define your custom error codes

            ErrorDetails errorDetails = new ErrorDetails(errorCode.getCode(), errorMessage);
            return new ResponseEntity<>(errorDetails, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<ScrappedPage>> getAllScrappedPages() {
        List<ScrappedPage> scrappedPages = scrappingService.getAll();
        return new ResponseEntity<>(scrappedPages, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    public ResponseEntity<?> getScrappedPageById(@PathVariable long id) {
        Optional<ScrappedPage> scrappedPage = scrappingService.getById(id);
        if (scrappedPage.isPresent()) {
            return new ResponseEntity<>(scrappedPage, HttpStatus.OK);
        } else {
            String errorMessage = "ScrappedPage with id '" + id + "' not found.";
            ErrorDetails.ErrorCode errorCode = ErrorDetails.ErrorCode.NOT_FOUND;
            ErrorDetails errorDetails = new ErrorDetails(errorCode.getCode(), errorMessage);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(errorDetails, headers, HttpStatus.NOT_FOUND);
        }
    }


}
