package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShipService {

    @Autowired
    ShipRepository repository;

    public Ship createShip (Ship ship) {
        ShipParamValidator.nullValidate(ship);
        ShipParamValidator.validate(ship.getName());
        ShipParamValidator.validate(ship.getPlanet());
        ShipParamValidator.validate(ship.getSpeed());
        ShipParamValidator.validate(ship.getCrewSize());
        ShipParamValidator.validate(ship.getProdDate());
        if (ship.isUsed() == null) ship.setUsed(false);
        if (ship.getId() != null) ship.setId(null);
        System.out.println(ship);
        ShipParamValidator.updateRating(ship);
        return repository.saveAndFlush(ship);
    }

    public Ship updateShip (Long id,Ship ship) {
        ShipParamValidator.validate(id);
        if (!repository.findById(id).isPresent())  ShipParamValidator.Error404("Not Exist ID");

        if (ShipParamValidator.isEmptyBody(ship)) return repository.findById(id).get();

        Ship DBship = repository.findById(id).get();

        if (ship.getId() != null) {
            if (!ship.getId().equals(DBship.getId())) ship.setId(DBship.getId());
        } else ship.setId(DBship.getId());

        if (ship.getName() != null) {
            ShipParamValidator.validate(ship.getName());
        } else ship.setName(DBship.getName());

        if (ship.getPlanet() != null) {
            ShipParamValidator.validate(ship.getPlanet());
        } else ship.setPlanet(DBship.getPlanet());

        if (ship.getSpeed() != null) {
            ShipParamValidator.validate(ship.getSpeed());
        } else ship.setSpeed(DBship.getSpeed());

        if (ship.getCrewSize() != null) {
            ShipParamValidator.validate(ship.getCrewSize());
        } else ship.setCrewSize(DBship.getCrewSize());

        if (ship.getShipType() == null) ship.setShipType(DBship.getShipType());

        if (ship.isUsed() == null) ship.setUsed(DBship.isUsed());

        if (ship.getProdDate() != null) {
            ShipParamValidator.validate(ship.getProdDate());
        } else ship.setProdDate(DBship.getProdDate());

        ShipParamValidator.updateRating(ship);

        return repository.save(ship);
    }

    public Page<Ship> getAll(Specification<Ship> specification, Pageable pageable) {
        return repository.findAll(specification,pageable);
    }

    public long getShipsCount(Specification<Ship> specification) {
        return repository.count(specification);
    }

    public Ship getById(Long id) {
        ShipParamValidator.validate(id);

        if (repository.findById(id).isPresent())
            return repository.findById(id).get();
        else
            ShipParamValidator.Error404("Not Exist ID");
        return null;

    }

    public void deleteById(Long id){
        ShipParamValidator.validate(id);
        if (repository.findById(id).isPresent()) repository.deleteById(id);
        else ShipParamValidator.Error404("Not Exist ID");
    }
}