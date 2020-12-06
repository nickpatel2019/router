package com.city.routeDetector;

import org.assertj.core.api.Assertions;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class RouteFinderTest {

    static RouteFinder routeFinder;
    static Logger logger;

    @BeforeClass
    public static void prepareForTests(){
        routeFinder = Mockito.mock(RouteFinder.class);
        logger = Mockito.mock(Logger.class);

        Mockito.when(routeFinder.isConnected(Mockito.anyString(),Mockito.anyString())).thenCallRealMethod();
        ReflectionTestUtils.setField(routeFinder, "logger", logger);
    }
    @Test
    public void testForRouteExists(){
        Map<String, List<String>> routeMap  = new HashMap<>();
        routeMap.put("Newyork", Arrays.asList("Boston", "LasVegas"));
        routeMap.put("Boston", Arrays.asList("Philadelphia", "SanJose"));
        ReflectionTestUtils.setField(routeFinder, "routeMap", routeMap);

        Assertions.assertThat(routeFinder.isConnected("Newyork","SanJose")).isEqualTo("yes");
        Assertions.assertThat(routeFinder.isConnected("Newyork","LasVegas")).isEqualTo("yes");
        Assertions.assertThat(routeFinder.isConnected("SanJose","LasVegas")).isEqualTo("no");

   }
    @Test
    public void testForNoRouteExists(){
        Map<String, List<String>> routeMap  = new HashMap<>();
        routeMap.put("Newyork", Arrays.asList("Boston", "LasVegas"));
        routeMap.put("Boston", Arrays.asList("SanJose"));
        ReflectionTestUtils.setField(routeFinder, "routeMap", routeMap);
        Assertions.assertThat(routeFinder.isConnected("SanJose","LasVegas")).isEqualTo("no");

    }
}
