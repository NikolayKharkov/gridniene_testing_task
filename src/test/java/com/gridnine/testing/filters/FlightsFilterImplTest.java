package com.gridnine.testing.filters;

import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

public class FlightsFilterImplTest {

    LocalDateTime now = LocalDateTime.now();
    List<Flight> flights = new ArrayList<>();
    List<Flight> expected = new ArrayList<>();

    @Test
    public void whenGetDepartureBeforeNow() {
        List<Segment> segmentsBeforeNow =
                new ArrayList<>(Arrays.asList(
                                new Segment(now.minusDays(3), now.minusDays(2))));

        List<Segment> segmentsAfterNow =
                new ArrayList<>(Arrays.asList(
                                new Segment(now.plusDays(2), now.plusDays(3))));
        Flight flightBeforeNow = new Flight(segmentsBeforeNow);
        Flight flightAfterNow = new Flight(segmentsAfterNow);
        flights.add(flightBeforeNow);
        flights.add(flightAfterNow);
        expected.add(flightBeforeNow);
        Assert.assertEquals(expected, new FlightsFilterImpl().getFlightsDepartureBeforeCurrentTime(flights));
    }

    @Test
    public void whenGetFlightsWithSegmentsArrivalBeforeDeparture() {
        List<Segment> segmentsArrivalBeforeDeparture =
                new ArrayList<>(Arrays.asList(
                        new Segment(now.minusDays(2), now.minusDays(3))));

        List<Segment> segmentsDepartureBeforeArrival =
                new ArrayList<>(Arrays.asList(
                        new Segment(now.plusDays(2), now.plusDays(3))));
        Flight flight1 = new Flight(segmentsArrivalBeforeDeparture);
        Flight flight2 = new Flight(segmentsDepartureBeforeArrival);
        flights.add(flight1);
        flights.add(flight2);
        expected.add(flight1);
        Assert.assertEquals(expected, new FlightsFilterImpl().getFlightsWhereSegmentsHasArrivalBeforeDeparture(flights));
    }

    @Test
    public void whenGetFlightsWithSegmentsArrivalBeforeDepartureAndExpectEmpty() {
        List<Segment> segmentsArrivalBeforeDeparture =
                new ArrayList<>(Arrays.asList(
                        new Segment(now.plusDays(2), now.plusDays(3))));

        List<Segment> segmentsDepartureBeforeArrival =
                new ArrayList<>(Arrays.asList(
                        new Segment(now.plusDays(2), now.plusDays(3))));
        Flight flight1 = new Flight(segmentsArrivalBeforeDeparture);
        Flight flight2 = new Flight(segmentsDepartureBeforeArrival);
        flights.add(flight1);
        flights.add(flight2);
        expected = new FlightsFilterImpl().getFlightsWhereSegmentsHasArrivalBeforeDeparture(flights);
        Assert.assertTrue(expected.isEmpty());
    }

    @Test
    public void whenGetFlightsWithParkingMoreThan2Hours() {
        List<Segment> segmentsSingle =
                new ArrayList<>(Arrays.asList(
                        new Segment(now.plusDays(2), now.plusDays(3))));

        List<Segment> segmentsWhenLess2Hours =
                new ArrayList<>(Arrays.asList(
                        new Segment(now.plusDays(2), now.plusDays(3))
                        , new Segment(now.plusDays(3).plusHours(1), now.plusDays(3).plusHours(5))));

        List<Segment> segmentsWhenMoreThan2Hours =
                new ArrayList<>(Arrays.asList(
                        new Segment(now.plusDays(3).minusHours(4), now.plusDays(3))
                        , new Segment(now.plusDays(3).plusHours(1), now.plusDays(3).plusHours(5))
                        , new Segment(now.plusDays(3).plusHours(8), now.plusDays(3).plusHours(9))));

        Flight flightWithSingleSegment = new Flight(segmentsSingle);
        Flight flightWithFewSegmentsButWithout2Hours = new Flight(segmentsWhenLess2Hours);
        Flight flightWithFewSegmentsButWith2Hours = new Flight(segmentsWhenMoreThan2Hours);
        flights.add(flightWithSingleSegment);
        flights.add(flightWithFewSegmentsButWithout2Hours);
        flights.add(flightWithFewSegmentsButWith2Hours);
        expected.add(flightWithFewSegmentsButWith2Hours);
        Assert.assertEquals(expected, new FlightsFilterImpl().getFlightsWithParkingMoreThan2Hours(flights));
    }

}