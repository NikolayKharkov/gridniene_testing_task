package com.gridnine.testing.filters;

import com.gridnine.testing.models.Flight;

import java.util.List;

public interface FlightFilters {

    List<Flight> getFlightsDepartureBeforeCurrentTime(List<Flight> flights);

    List<Flight> getFlightsWhereSegmentsHasArrivalBeforeDeparture(List<Flight> flights);

    List<Flight> getFlightsWithParkingMoreThan2Hours(List<Flight> flights);
}
