package com.gridnine.testing.filters;

import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightsFilterImpl implements FlightFilters {

    @Override
    public List<Flight> getFlightsDepartureBeforeCurrentTime(List<Flight> flights) {
        return flights
                .stream()
                .filter(
                        flight -> flight.getSegments()
                                .stream()
                                .anyMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> getFlightsWhereSegmentsHasArrivalBeforeDeparture(List<Flight> flights) {
        return flights
                .stream()
                .filter(
                        flight -> flight.getSegments()
                                .stream()
                                .anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> getFlightsWithParkingMoreThan2Hours(List<Flight> flights) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            int segmentsSize = flight.getSegments().size();
            if (segmentsSize > 1) {
                Duration timeDifference = Duration.ZERO;
                List<Segment> segments = flight.getSegments();
                for (int i = 0; i < segmentsSize - 1; i++) {
                    timeDifference = timeDifference
                            .plus(Duration.between(
                                    segments.get(i).getArrivalDate(),
                                    segments.get(i + 1).getDepartureDate()));
                }
                if (timeDifference.toHours() >= 2) {
                    result.add(flight);
                }
            }
        }
        return result;
    }
}
