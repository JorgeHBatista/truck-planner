package com.trucks.adapter.rest.controller;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

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

import com.trucks.application.service.VesselService;
import com.trucks.domain.entity.Vessel;

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
    value = VesselHandler.ENDPOINT, 
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
public class VesselHandler {

    public static final String ENDPOINT = "/vessels";
    private VesselService service;

    public VesselHandler(VesselService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Vessel> all(
            @RequestParam(name = UllPagination.PAGE, required = false) String page,
            @RequestParam(name = UllPagination.SIZE, required = false) String size) {
        final List<ApiSubError> errors = new ArrayList<>();
        final int pageValue = UllRequestParameterValidation.validateRequestParameterPage(page, errors);
        final int sizeValue = UllRequestParameterValidation.validateRequestParameterSize(size, errors);
        UllRestUtils.throwExceptionIfErrors(errors); // check so there are no errors
        PageRequest pageRequest = PageRequest.of(pageValue, sizeValue);
        Page<Vessel> result = service.findAll(pageRequest);
        return result;
    }

    @PostMapping
    public Vessel newVessel(@RequestBody Vessel newVessel) {
        return service.save(newVessel);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<Vessel> optionalVessel = this.service.find(id);
        if (!optionalVessel.isPresent()) {
            throw new UllNotFoundException();
        }
        final Vessel entity = optionalVessel.get();
        return ResponseEntity.ok().body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceVessel(@RequestBody Vessel newVessel, @PathVariable UUID id) {
        Optional<Vessel> optionalVessel = this.service.find(id);
        if (optionalVessel.isPresent()) {
            service.update(id, newVessel);
            return ResponseEntity.ok().body(newVessel);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vessel not found");
        }
    }

    @DeleteMapping("/{id}")
    Vessel deleteVessel(@PathVariable UUID id) {
        return service.delete(id);
    }

    @DeleteMapping
    List<Vessel> deleteAll() {
        return service.deleteAll();
    }

    private HttpHeaders allowHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, PUT, DELETE");
        return headers;
    }

    private ResponseEntity<?> toResponseEntity(Page<Vessel> page) {
        List<Vessel> mappedResult = new ArrayList<>();
        page.getContent().forEach(resource -> {
            mappedResult.add(resource);
        });
        HttpHeaders headers = this.allowHeader();
        headers.addAll(UllPagination.toHeaders(page));
        return ResponseEntity.ok().headers(headers).body(mappedResult);
    }
}
