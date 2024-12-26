package principal;


import conexiones.ConsultorMonedas;
import excepciones.ErrorAlConsultarPrecioException;

import java.io.IOException;

public class Principal {
    public static void main(String[] args) {
        ConsultorMonedas miConsulta = new ConsultorMonedas();

        try {
            String precioDeMoneda = miConsulta.consultaPrecioDeMoneda("ARGENTO");
            if (precioDeMoneda.contains("error-type")) {
                throw new ErrorAlConsultarPrecioException("Error al consultar la exchangerate API");
            }
            System.out.println(precioDeMoneda);

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
