package com.trucks.adapter.rest.controller;

import org.springframework.http.HttpStatus;
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

import es.ull.utils.rest.exception.UllBadRequestException;
import es.ull.utils.rest.exception.UllNotFoundException;
import com.trucks.adapter.rest.RestApiConfiguration;
import com.trucks.application.service.PortService;
import com.trucks.domain.entity.Port;
import com.trucks.utils.PortsUtils;

import java.util.UUID;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import es.ull.utils.rest.UllRestUtils;
import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.pagination.UllPagination;
import es.ull.utils.rest.pagination.UllRequestParameterValidation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;

@CrossOrigin(origins = "*")
@RequestMapping(
    value = PortHandler.ENDPOINT, 
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
public class PortHandler {

    public static final String ENDPOINT = "/ports";
    private PortService service;

    public PortHandler(PortService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Port> all(
            @RequestParam(name = UllPagination.PAGE, required = false) String page,
            @RequestParam(name = UllPagination.SIZE, required = false) String size) {
        final List<ApiSubError> errors = new ArrayList<>();
        final int pageValue = UllRequestParameterValidation.validateRequestParameterPage(page, errors);
        final int sizeValue = UllRequestParameterValidation.validateRequestParameterSize(size, errors);
        UllRestUtils.throwExceptionIfErrors(errors); // check so there are no errors
        PageRequest pageRequest = PageRequest.of(pageValue, sizeValue);
        Page<Port> result = service.findAll(pageRequest);
        return result;
    }

    @PostMapping
    public Port newPort(@RequestBody Port newPort) {
        return service.save(newPort);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<Port> optionalPort = this.service.find(id);
        if (!optionalPort.isPresent()) {
            throw new UllNotFoundException();
        }
        final Port entity = optionalPort.get();
        return ResponseEntity.ok().body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replacePort(@RequestBody Port newPort, @PathVariable UUID id) {
        Optional<Port> optionalPort = this.service.find(id);
        if (optionalPort.isPresent()) {
            service.update(id, newPort);
            return ResponseEntity.ok().body(newPort);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Port not found");
        }
    }

    @DeleteMapping("/{id}")
    Port deletePort(@PathVariable UUID id) {
        return service.delete(id);
    }

    @DeleteMapping
    List<Port> deleteAll() {
        return service.deleteAll();
    }

    private HttpHeaders allowHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, PUT, DELETE");
        return headers;
    }

    private ResponseEntity<?> toResponseEntity(Page<Port> page) {
        List<Port> mappedResult = new ArrayList<>();
        page.getContent().forEach(resource -> {
            mappedResult.add(resource);
        });
        HttpHeaders headers = this.allowHeader();
        headers.addAll(UllPagination.toHeaders(page));
        return ResponseEntity.ok().headers(headers).body(mappedResult);
    }
}