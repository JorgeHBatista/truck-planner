package com.trucks.adapter.rest.controller;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import es.ull.utils.rest.UllRestUtils;
import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.exception.UllNotFoundException;

import com.trucks.application.service.TruckService;
import com.trucks.domain.entity.Truck;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import es.ull.utils.rest.pagination.UllPagination;
import es.ull.utils.rest.pagination.UllRequestParameterValidation;

@CrossOrigin(origins = "*")
@RequestMapping(
    value = TruckHandler.ENDPOINT, 
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
public class TruckHandler {

    public static final String ENDPOINT = "/trucks";
    private TruckService service;

    public TruckHandler(TruckService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Truck> all(
            @RequestParam(name = UllPagination.PAGE, required = false) String page,
            @RequestParam(name = UllPagination.SIZE, required = false) String size) {
        final List<ApiSubError> errors = new ArrayList<>();
        final int pageValue = UllRequestParameterValidation.validateRequestParameterPage(page, errors);
        final int sizeValue = UllRequestParameterValidation.validateRequestParameterSize(size, errors);
        UllRestUtils.throwExceptionIfErrors(errors); // check so there are no errors
        PageRequest pageRequest = PageRequest.of(pageValue, sizeValue);
        System.out.println("Llega al controlador");
        Page<Truck> result = service.findAll(pageRequest);
        return result;
    }

    @PostMapping
    public Truck newTruck(@RequestBody Truck newTruck) {
        return service.save(newTruck);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<Truck> optionalTruck = this.service.find(id);
        if (!optionalTruck.isPresent()) {
            throw new UllNotFoundException();
        }
        final Truck entity = optionalTruck.get();
        return ResponseEntity.ok().body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceTruck(@RequestBody Truck newTruck, @PathVariable UUID id) {
        Optional<Truck> optionalTruck = this.service.find(id);
        if (optionalTruck.isPresent()) {
            service.update(id, newTruck);
            return ResponseEntity.ok().body(newTruck);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Truck not found");
        }
    }

    @DeleteMapping("/{id}")
    Truck deleteTruck(@PathVariable UUID id) {
        return service.delete(id);
    }

    @DeleteMapping
    List<Truck> deleteAll() {
        return service.deleteAll();
    }

    private HttpHeaders allowHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, PUT, DELETE");
        return headers;
    }

    private ResponseEntity<?> toResponseEntity(Page<Truck> page) {
        List<Truck> mappedResult = new ArrayList<>();
        page.getContent().forEach(resource -> {
            mappedResult.add(resource);
        });
        HttpHeaders headers = this.allowHeader();
        headers.addAll(UllPagination.toHeaders(page));
        return ResponseEntity.ok().headers(headers).body(mappedResult);
    }
}
