package conexiones;

import excepciones.ErrorAlConsultarPrecioException;
import modelos.Moneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultorMonedas {
    private HttpClient clienteHttp = HttpClient.newBuilder().build();

    public ConsultorMonedas() {
    }

    public String consultaPrecioDeMoneda(String codigoDeMoneda) throws IOException, InterruptedException {
        String uri = "https://v6.exchangerate-api.com/v6/0c2afea0978086b6400e1d7c/latest/" + codigoDeMoneda;
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
