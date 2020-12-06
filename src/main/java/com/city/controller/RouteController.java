package com.city.controller;

import com.city.routeDetector.RouteFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {
    Logger logger = LoggerFactory.getLogger(RouteController.class);
    @Autowired
    RouteFinder routeFinder;

    @GetMapping("/connected")
    public String isConnected(@RequestParam(value = "origin") String origin, @RequestParam(value = "destination") String destination){
        logger.info("Finding if there is a route between {} and {}" , origin, destination);
        return this.routeFinder.isConnected(origin,destination);
    }

}
