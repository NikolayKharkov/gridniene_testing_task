import com.gridnine.testing.fabrics.FlightBuilder;
import com.gridnine.testing.filters.FlightsFilterImpl;
import com.gridnine.testing.models.Flight;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Вылеты до текущего момента времени: ");
        new FlightsFilterImpl()
                .getFlightsDepartureBeforeCurrentTime(flights)
                .stream()
                .forEach(System.out::println);
        System.out.println("Имеются сегменты с датой прилёта раньше даты вылета: ");
        new FlightsFilterImpl()
                .getFlightsWhereSegmentsHasArrivalBeforeDeparture(flights)
                .stream()
                .forEach(System.out::println);
        System.out.println("Общее время, проведённое на земле превышает два часа: ");
        new FlightsFilterImpl()
                .getFlightsWithParkingMoreThan2Hours(flights)
                .stream()
                .forEach(System.out::println);
    }
}
