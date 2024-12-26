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
            if (monedaJson.contains("error-type")) {
                throw new ErrorAlConsultarPrecioException("Error al consultar la exchangerate API");
            }
            MonedaExchangeRate miMonedaER = gson.
                    fromJson(monedaJson, MonedaExchangeRate.class);
//            System.out.println("El JSON de la moneda consultada es: " + precioDeMoneda);
            System.out.println("El registro obtenido para la moneda consultada es: " + miMonedaER);
            Moneda miMoneda = new Moneda(miMonedaER);
            System.out.println("La moneda consultada es: " + miMoneda);

            double montoAConvertir = 1.0;
            String codigoOtraMoneda = "ARSgento";
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
