package modelos;

import java.util.Map;

public record MonedaExchangeRate(String base_code,
                                 String time_last_update_utc,
                                 int time_last_update_unix,
                                 Map<String, Double> conversion_rates) {
}