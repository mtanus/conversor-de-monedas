package modelos;

import java.util.Map;

public class Moneda {
    private String codigoIso;
    private String fechaDeActualizacionDelPrecio;
    private int fechaUnixDeActualizacionDelPrecio;
    private Map<String, Double> precioEnOtrasMonedas;

    public Moneda(MonedaExchangeRate monedaER) {
        this.codigoIso = monedaER.base_code();
        this.fechaDeActualizacionDelPrecio = monedaER.time_last_update_utc();
        this.fechaUnixDeActualizacionDelPrecio = monedaER.time_last_update_unix();
        this.precioEnOtrasMonedas = monedaER.conversion_rates();
    }

    public String getCodigoIso() {
        return this.codigoIso;
    }

    public String getFechaDeActualizacionDelPrecio() {
        return this.fechaDeActualizacionDelPrecio;
    }

    public Map<String, Double> getPrecioEnOtrasMonedas() {
        return this.precioEnOtrasMonedas;
    }

    @Override
    public String toString() {
        return "Moneda: " + this.codigoIso;
    }
}
