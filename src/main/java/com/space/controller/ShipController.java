package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Service
public class ShipController {

    @Autowired
    private ShipService shipService;


    @GetMapping("/rest/ships")
    public List<Ship> getShipsList(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "planet", required = false) String planet,
                                   @RequestParam(value = "shipType", required = false) ShipType shipType,
                                   @RequestParam(value = "after", required = false) Long after,
                                   @RequestParam(value = "before", required = false) Long before,
                                   @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                   @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                   @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                   @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                   @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                   @RequestParam(value = "minRating", required = false) Double minRating,
                                   @RequestParam(value = "maxRating", required = false) Double maxRating,
                                   @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                   @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                   @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        return shipService.getAll(Specification
                        .where(ShipSpecFilter.filterStringLike(name,"name")
                                .and(ShipSpecFilter.filterStringLike(planet,"planet")))
                        .and(ShipSpecFilter.filterEqual(shipType,"shipType"))
                        .and(ShipSpecFilter.filterBetween(after, before,"prodDate"))
                        .and(ShipSpecFilter.filterBool(isUsed,"isUsed"))
                        .and(ShipSpecFilter.filterBetween(minSpeed, maxSpeed,"speed"))
                        .and(ShipSpecFilter.filterBetween(minCrewSize, maxCrewSize,"crewSize"))
                        .and(ShipSpecFilter.filterBetween(minRating, maxRating,"rating")),
                pageable).getContent();
    }



    @GetMapping("/rest/ships/count")
    public long getShipsCount(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "planet", required = false) String planet,
                              @RequestParam(value = "shipType", required = false) ShipType shipType,
                              @RequestParam(value = "after", required = false) Long after,
                              @RequestParam(value = "before", required = false) Long before,
                              @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                              @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                              @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                              @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                              @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                              @RequestParam(value = "minRating", required = false) Double minRating,
                              @RequestParam(value = "maxRating", required = false) Double maxRating) {

        return shipService.getShipsCount(Specification
                .where(ShipSpecFilter.filterStringLike(name,"name")
                        .and(ShipSpecFilter.filterStringLike(planet,"planet")))
                .and(ShipSpecFilter.filterEqual(shipType,"shipType"))
                .and(ShipSpecFilter.filterBetween(after, before,"prodDate"))
                .and(ShipSpecFilter.filterBool(isUsed,"isUsed"))
                .and(ShipSpecFilter.filterBetween(minSpeed, maxSpeed,"speed"))
                .and(ShipSpecFilter.filterBetween(minCrewSize, maxCrewSize,"crewSize"))
                .and(ShipSpecFilter.filterBetween(minRating, maxRating,"rating")));
    }



    @PostMapping("/rest/ships/{id}")
    public Ship updateShipById(@PathVariable Long id, @RequestBody Ship ship) {
        return shipService.updateShip(id, ship);
    }

    @PostMapping("/rest/ships")
    public Ship createShip(@RequestBody Ship ship) {
        return shipService.createShip(ship);
    }

    @DeleteMapping("/rest/ships/{id}")
    public void deleteShipById(@PathVariable Long id)  {
        shipService.deleteById(id);
    }

    @GetMapping("/rest/ships/{id}")
    public Ship getShipById(@PathVariable Long id) {
        return shipService.getById(id);
    }
}