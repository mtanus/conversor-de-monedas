package conexiones;

import excepciones.ErrorAlConsultarPrecioException;
import modelos.Moneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConsultorMonedas {
    private HttpClient clienteHttp = HttpClient.newBuilder().build();

    public ConsultorMonedas() {
    }

    public String consultaPrecioDeMoneda(String codigoDeMoneda) throws IOException, InterruptedException {
        // Obtengo la Api Key
        var propiedades = new Properties();
        var archivoDeConfiguracion = Paths.get("miConfiguracion.env");
        try (var inputStream = Files.newInputStream(archivoDeConfiguracion)) {
            propiedades.load(inputStream);
        }
        String apiKey = propiedades.get("EXCHANGE_RATE_API_KEY").toString();
        String uri = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + codigoDeMoneda;

        // Realizo la solicitud
        HttpRequest solicitudHttp = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> respuestaHttp = clienteHttp
                .send(solicitudHttp, HttpResponse.BodyHandlers.ofString());

        return respuestaHttp.body();
    }

    public double convierteMontoEntreMonedas (double montoMonedaBase,
                                              Moneda monedaBase, String codigoOtraMoneda) {
        if (monedaBase.getPrecioEnOtrasMonedas().containsKey(codigoOtraMoneda)) {
            return montoMonedaBase * monedaBase.getPrecioEnOtrasMonedas().get(codigoOtraMoneda);
        } else {
            throw new ErrorAlConsultarPrecioException("Moneda " +
                    codigoOtraMoneda + " no hallada en la API.");
        }
    }
}
