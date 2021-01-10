package com.space.service;

import com.space.model.Ship;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ShipParamValidator {

    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final int STRING_LENGTH_LIMIT = 50;
    private static final int MIN_YEAR = 2800;
    private static final int MAX_YEAR = 3019;
    private static final double MIN_SPEED = 0.01d;
    private static final double MAX_SPEED = 0.99d;
    private static final int MIN_CREW_SIZE = 1;
    private static final int MAX_CREW_SIZE = 9999;
    private static final double USED_SHIP_COEFFICIENT = 0.5d;
    private static final double NEW_SHIP_COEFFICIENT = 1d;


    public static void nullValidate (Ship ship) {
        if (ship.getName() == null)  Error400("Invalid name");
        if (ship.getPlanet() == null)  Error400("Invalid planet name");
        if (ship.getShipType() == null)  Error400("Invalid shipType");
        if (ship.getSpeed() == null)  Error400("Invalid speed");
        if (ship.getProdDate() == null)  Error400("Invalid date");
        if (ship.getCrewSize() == null)  Error400("Invalid crew size");
    }

    public static void validate(Long id) {
        if (id <= 0)
            Error400(String.format("Requested bad id = %d", id));
    }

    public static void validate(String name) {
        if (name.isEmpty() || name.length() > STRING_LENGTH_LIMIT)
            Error400(String.format("Requested bad String = %s", name));
    }

    public static void validate(Date prodDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prodDate);
        if (calendar.get(Calendar.YEAR) < MIN_YEAR || calendar.get(Calendar.YEAR) >MAX_YEAR)
            Error400(String.format("Requested product is invalid date = %s MinYear = %s and MaxYear = %s", prodDate, MIN_YEAR, MAX_YEAR));
    }

    public static void validate(Double speed) {
        if (speed < MIN_SPEED || speed > MAX_SPEED)
            Error400(String.format("Requested speed is invalid speed = %.2f MinSpeed = %.2f and MaxSpeed = %.2f", speed, MIN_SPEED, MAX_SPEED));
    }

    public static void validate(Integer crewSize) {
        if (crewSize < MIN_CREW_SIZE || crewSize > MAX_CREW_SIZE)
            Error400(String.format("Requested CrewSize is invalid crewSize = %d MinCrewSize = %d and MaxCrewSize = %d", crewSize, MIN_CREW_SIZE, MAX_CREW_SIZE));
    }

    public static boolean isEmptyBody(Ship ship) {
        return (ship.getName() == null && ship.getPlanet() == null && ship.getShipType() == null && ship.getSpeed() == null
                && ship.getProdDate() == null && ship.getCrewSize() == null && ship.isUsed() == null);
    }

    public static void updateRating(Ship ship) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(ship.getProdDate());
        double k = ship.isUsed() ? USED_SHIP_COEFFICIENT : NEW_SHIP_COEFFICIENT;
        double v = ship.getSpeed();
        double result = (80d * v * k)  / (MAX_YEAR - calendar.get(Calendar.YEAR) + 1);
        result = Math.round(result * 100d) / 100d;
        ship.setRating(result);
    }

    public static void Error404 (String err) {
        throw new ResponseStatusException(NOT_FOUND, err);
    }

    private static void Error400 (String err) {
        throw new ResponseStatusException(BAD_REQUEST, err);
    }



}