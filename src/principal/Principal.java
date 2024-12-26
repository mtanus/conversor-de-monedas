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
            String precioDeMoneda = miConsultaDeMoneda.consultaPrecioDeMoneda("ARS");
            if (precioDeMoneda.contains("error-type")) {
                throw new ErrorAlConsultarPrecioException("Error al consultar la exchangerate API");
            }
            MonedaExchangeRate miMonedaER = gson.fromJson(precioDeMoneda,
                    MonedaExchangeRate.class);
//            System.out.println("El JSON de la moneda consultada es: " + precioDeMoneda);
            System.out.println("El registro obtenido para la moneda consultada es: " + miMonedaER);
            Moneda miMoneda = new Moneda(miMonedaER);
            System.out.println("La moneda consultada es: " + miMoneda);

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
