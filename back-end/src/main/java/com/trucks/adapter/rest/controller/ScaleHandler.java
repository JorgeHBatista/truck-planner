package com.trucks.adapter.rest.controller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.ull.utils.rest.UllRestUtils;
import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.exception.UllNotFoundException;

import com.trucks.domain.entity.Scale;
import com.trucks.application.service.ScaleService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import es.ull.utils.rest.pagination.UllPagination;
import es.ull.utils.rest.pagination.UllRequestParameterValidation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;

@CrossOrigin(origins = "*")
@RequestMapping(
    value = ScaleHandler.ENDPOINT, 
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
public class ScaleHandler {

    public static final String ENDPOINT = "/scales";
    private ScaleService service;

    public ScaleHandler(ScaleService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Scale> all(
            @RequestParam(name = UllPagination.PAGE, required = false) String page,
            @RequestParam(name = UllPagination.SIZE, required = false) String size) {
        final List<ApiSubError> errors = new ArrayList<>();
        final int pageValue = UllRequestParameterValidation.validateRequestParameterPage(page, errors);
        final int sizeValue = UllRequestParameterValidation.validateRequestParameterSize(size, errors);
        UllRestUtils.throwExceptionIfErrors(errors); // check so there are no errors
        PageRequest pageRequest = PageRequest.of(pageValue, sizeValue);
        Page<Scale> result = service.findAll(pageRequest);
        return result;
    }

    @PostMapping
    public Scale newScale(@RequestBody Scale newScale) {
        return service.save(newScale);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<Scale> optionalScale = this.service.find(id);
        if (!optionalScale.isPresent()) {
            throw new UllNotFoundException();
        }
        final Scale entity = optionalScale.get();
        return ResponseEntity.ok().body(entity);
    }

    @DeleteMapping
    List<Scale> deleteAll() {
        return service.deleteAll();
    }

    private HttpHeaders allowHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, DELETE");
        return headers;
    }

    private ResponseEntity<?> toResponseEntity(Page<Scale> page) {
        List<Scale> mappedResult = new ArrayList<>();
        page.getContent().forEach(resource -> {
            mappedResult.add(resource);
        });
        HttpHeaders headers = this.allowHeader();
        headers.addAll(UllPagination.toHeaders(page));
        return ResponseEntity.ok().headers(headers).body(mappedResult);
    }
}
