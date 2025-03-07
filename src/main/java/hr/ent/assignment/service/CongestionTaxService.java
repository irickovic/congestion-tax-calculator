package hr.ent.assignment.service;

import hr.ent.assignment.entity.TaxAccumulator;
import hr.ent.assignment.entity.TaxInterval;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CongestionTaxService {

    // TODO load from external storage
    private static final List<String> EXEMPT_VEHICLES = List.of(
            "Emergency",
            "Bus",
            "Diplomat",
            "Motorcycle",
            "Military",
            "Foreign");
    private static final List<TaxInterval> TAX_INTERVALS = List.of(
            new TaxInterval(LocalTime.of(6, 0), LocalTime.of(6, 29), 8),
            new TaxInterval(LocalTime.of(6, 30), LocalTime.of(6, 59), 13),
            new TaxInterval(LocalTime.of(7, 0), LocalTime.of(7, 59), 18),
            new TaxInterval(LocalTime.of(8, 0), LocalTime.of(8, 29), 13),
            new TaxInterval(LocalTime.of(8, 30), LocalTime.of(14, 59), 8),
            new TaxInterval(LocalTime.of(15, 0), LocalTime.of(15, 29), 13),
            new TaxInterval(LocalTime.of(15, 30), LocalTime.of(16, 59), 18),
            new TaxInterval(LocalTime.of(17, 0), LocalTime.of(17, 59), 13),
            new TaxInterval(LocalTime.of(18, 0), LocalTime.of(18, 29), 8),
            new TaxInterval(LocalTime.of(18, 30), LocalTime.of(5, 59), 0)
    );

    public int getTax(String vehicleType, List<LocalDateTime> passages) {
        if (EXEMPT_VEHICLES.contains(vehicleType)) return 0;

        return passages.stream()
                .filter(this::isTaxable)
                .collect(Collectors.groupingBy(LocalDateTime::toLocalDate, Collectors.toList()))
                .values()
                .stream()
                .mapToInt(this::calculateDailyTax)
                .sum();
    }

    private boolean isTaxable(LocalDateTime dateTime) {
        // TODO retrieve public holidays somehow
        DayOfWeek day = dateTime.getDayOfWeek();
        return !(dateTime.getMonthValue() == 7 || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
    }

    private int getTaxRate(LocalTime time) {
        return TAX_INTERVALS.stream()
                .filter(taxInterval -> !time.isBefore(taxInterval.getStart()) && !time.isAfter(taxInterval.getEnd()))
                .map(TaxInterval::getTax)
                .findFirst()
                .orElse(0);
    }

    private int calculateDailyTax(List<LocalDateTime> dailyPassages) {
        return dailyPassages.stream()
                .sorted()
                .map(p -> Map.entry(p, getTaxRate(p.toLocalTime())))
                .reduce(new TaxAccumulator(),
                        TaxAccumulator::accumulate,
                        TaxAccumulator::combine)
                .getTotalTax();
    }

}
