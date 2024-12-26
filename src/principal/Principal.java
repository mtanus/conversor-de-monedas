package principal;


import com.google.gson.Gson;
import conexiones.ConsultorMonedas;
import excepciones.ErrorAlConsultarPrecioException;
import modelos.Moneda;
import modelos.MonedaExchangeRate;

import java.io.IOException;

public class Principal {
    public static void main(String[] args) {
        ConsultorMonedas miConsultaDeMoneda = new ConsultorMonedas();
        Gson gson = new Gson();

        try {
            String codigoMonedaBase = "BRL";
            String monedaJson = miConsultaDeMoneda.consultaPrecioDeMoneda(codigoMonedaBase);
            MonedaExchangeRate monedaER = gson.
                    fromJson(monedaJson, MonedaExchangeRate.class);
            if (monedaER.result().equals("error")) {
                throw new ErrorAlConsultarPrecioException("Error al consultar " +
                        "la ExchangeRate API para la moneda " + codigoMonedaBase);
            }
//            System.out.println("El JSON de la moneda consultada es: " + precioDeMoneda);
            System.out.println("El registro obtenido para la moneda consultada es: " + monedaER);
            Moneda miMoneda = new Moneda(monedaER);
            System.out.println("La moneda consultada es: " + miMoneda);

            double montoAConvertir = 10.0;
            String codigoOtraMoneda = "ARS";
            System.out.println(montoAConvertir + " " + codigoMonedaBase + " = " +
                    miConsultaDeMoneda.
                            convierteMontoEntreMonedas(montoAConvertir, miMoneda, codigoOtraMoneda) +
                    " " + codigoOtraMoneda);

        } catch (ErrorAlConsultarPrecioException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error con la URL a consultar");
            System.out.println(e.getMessage());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error en la conexión con la API");
            System.out.println(e.getMessage());
        }
    }
}
