package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ShipSpecFilter {

    private ShipSpecFilter(){}

    public static Specification<Ship> filterStringLike(String value, String param) {
        return (root, query, cb) -> value == null ? null : cb.like(root.get(param), "%" + value + "%");
    }

    public static Specification<Ship> filterBetween(Integer min, Integer max, String param) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get(param), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get(param), min);
            return cb.between(root.get(param), min, max);
        };
    }

    public static Specification<Ship> filterBetween(Double min, Double max, String param ) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get(param), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get(param), min);
            return cb.between(root.get(param), min, max);
        };
    }

    public static Specification<Ship> filterBetween(Long min, Long max, String param) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get(param), new Date(max));
            if (max == null) return cb.greaterThanOrEqualTo(root.get(param), new Date(min));
            return cb.between(root.get(param), new Date(min), new Date(max));
        };
    }

    public static Specification<Ship> filterEqual(ShipType shipType, String param) {
        return (root, query, cb) -> shipType == null ? null : cb.equal(root.get(param), shipType);
    }

    public static Specification<Ship> filterBool(Boolean isUsed, String param) {
        return (root, query, cb) -> {
            if (isUsed == null) return null;
            if (isUsed) return cb.isTrue(root.get(param));
            return cb.isFalse(root.get(param));
        };
    }
}