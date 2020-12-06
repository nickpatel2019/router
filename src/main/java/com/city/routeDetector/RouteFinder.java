package com.city.routeDetector;

import com.city.controller.RouteController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class RouteFinder {

    private Logger logger = LoggerFactory.getLogger(RouteFinder.class);

    private Map<String, List<String>> routeMap = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        InputStream is = getResourceFileAsInputStream("static/routes.txt");
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    String[] paths = line.split(",");
                    String origin = paths[0];
                    String destination = paths[1];

                    if(StringUtils.isBlank(origin) || StringUtils.isBlank(destination)){
                        logger.error("Origin or Destination cannot be null in the route config");
                        throw new RuntimeException("Origin or Destination cannot be null in the route config");
                    }

                    if (!routeMap.containsKey(origin)) {
                        List<String> neigbours = new ArrayList<>();
                        neigbours.add(paths[1]);
                        routeMap.put(origin, neigbours);
                    } else {
                        routeMap.get(origin).add(paths[1]);
                    }
                    System.out.println("Paths are " + routeMap.toString());
                    logger.info("Paths are {}", routeMap.toString());

                }
            } catch (Exception e) {
                logger.error("Route info not found");
                throw new RuntimeException("Route info not found");
            }
        } else {
            logger.error("Route info not found");
            throw new RuntimeException("Route resource not found");
        }
    }
    private InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = RouteController.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    /**
     * Determine if there is a route between source and destination
     * @param origin
     * @param destination
     * @return
     */
    public String isConnected(String origin, String destination) {
        if (origin == null || destination == null) {
            return "no";
        }
        if (origin == destination) {
            return "yes";
        }
        Queue<String> queue = new LinkedList();
        Map<String, Boolean> visited = new HashMap<>();
        queue.add(origin);
        visited.put(origin, true);
        while (!queue.isEmpty()) {
            String source = queue.remove();
            if (source.equals(destination)) {
                logger.info("Route found");
                return "yes";
            }
            List<String> connectedCities = routeMap.get(source);
            if (connectedCities != null && connectedCities.size() > 0) {
                for (String temp : connectedCities) {
                    if (visited.get(temp) == null || !visited.get(temp)) {
                        queue.add(temp);
                        visited.put(temp, true);
                    }
                }
            }
        }
        logger.info("Route Not found");
        return "no";
    }
}
