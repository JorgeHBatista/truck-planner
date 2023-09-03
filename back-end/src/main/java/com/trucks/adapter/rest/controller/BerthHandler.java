package com.trucks.adapter.rest.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import es.ull.utils.rest.UllRestUtils;
import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.exception.UllNotFoundException;

import com.trucks.application.service.BerthService;
import com.trucks.domain.entity.Berth;

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
    value = BerthHandler.ENDPOINT, 
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
public class BerthHandler {

    public static final String ENDPOINT = "/berths";
    private BerthService service;

    public BerthHandler(BerthService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Berth> all(
            @RequestParam(name = UllPagination.PAGE, required = false) String page,
            @RequestParam(name = UllPagination.SIZE, required = false) String size) {
        final List<ApiSubError> errors = new ArrayList<>();
        final int pageValue = UllRequestParameterValidation.validateRequestParameterPage(page, errors);
        final int sizeValue = UllRequestParameterValidation.validateRequestParameterSize(size, errors);
        UllRestUtils.throwExceptionIfErrors(errors); // check so there are no errors
        PageRequest pageRequest = PageRequest.of(pageValue, sizeValue);
        Page<Berth> result = service.findAll(pageRequest);
        return result;
    }

    @PostMapping
    public Berth newBerth(@RequestBody Berth newBerth) {
        return service.save(newBerth);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<Berth> optionalBerth = this.service.find(id);
        if (!optionalBerth.isPresent()) {
            throw new UllNotFoundException();
        }
        final Berth entity = optionalBerth.get();
        return ResponseEntity.ok().body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceBerth(@RequestBody Berth newBerth, @PathVariable UUID id) {
        Optional<Berth> optionalBerth = this.service.find(id);
        if (optionalBerth.isPresent()) {
            service.update(id, newBerth);
            return ResponseEntity.ok().body(newBerth);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Berth not found");
        }
    }

    @DeleteMapping("/{id}")
    Berth deleteBerth(@PathVariable UUID id) {
        return service.delete(id);
    }

    @DeleteMapping()
    List<Berth> deleteAll() {
        return service.deleteAll();
    }

    private HttpHeaders allowHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, PUT, DELETE");
        return headers;
    }

    private ResponseEntity<?> toResponseEntity(Page<Berth> page) {
        List<Berth> mappedResult = new ArrayList<>();
        page.getContent().forEach(resource -> {
            mappedResult.add(resource);
        });
        HttpHeaders headers = this.allowHeader();
        headers.addAll(UllPagination.toHeaders(page));
        return ResponseEntity.ok().headers(headers).body(mappedResult);
    }
}
