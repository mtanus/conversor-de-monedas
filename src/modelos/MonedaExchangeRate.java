package modelos;

import java.util.HashMap;
import java.util.Map;

public record MonedaExchangeRate(String base_code,
                                 String time_last_update_utc,
                                 int time_last_update_unix,
                                 HashMap<String, Double> conversion_rates,
                                 String result) {
}
