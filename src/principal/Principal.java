package principal;


import com.google.gson.Gson;
import conexiones.ConsultorMonedas;
import excepciones.ErrorAlConsultarPrecioException;
import modelos.Moneda;
import modelos.MonedaExchangeRate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        ConsultorMonedas miConsultaDeMoneda = new ConsultorMonedas();
        Gson gson = new Gson();
        Scanner lectorDeTeclado = new Scanner(System.in);
        Menu menu = new Menu();

        System.out.println("Bienvenid@ a tu conversor de monedas de confianza!");

        while (true) {
            try {
                // Obtengo código de moneda base
                int opcionMonedaBase;
                System.out.println("Ingresá el número correspondiente a tu moneda de referencia:");
                System.out.println(menu.getMenuDeOpciones());
                opcionMonedaBase = lectorDeTeclado.nextInt();
                if (opcionMonedaBase == 0) {
                    break;
                }
                String codigoMonedaBase = menu.seleccionaMoneda(opcionMonedaBase);

                // Obtengo código de moneda destino
                int opcionMonedaDestino;
                System.out.println("Ingresá el número correspondiente a tu moneda de destino:");
                System.out.println(menu.getMenuDeOpciones());
                opcionMonedaDestino = lectorDeTeclado.nextInt();
                if (opcionMonedaDestino == 0) {
                    break;
                }
                String codigoMonedaDestino = menu.seleccionaMoneda(opcionMonedaDestino);

                // Obtengo el monto a convertir entre las monedas
                double montoAConvertir;
                System.out.println("Ingresá el valor a convertir entre monedas:");
                montoAConvertir = lectorDeTeclado.nextDouble();

                // Consulto la API para obtener los datos
                String monedaJson = miConsultaDeMoneda.consultaPrecioDeMoneda(codigoMonedaBase);
                MonedaExchangeRate monedaER = gson.fromJson(monedaJson, MonedaExchangeRate.class);
                if (monedaER.result().equals("error")) {
                    throw new ErrorAlConsultarPrecioException("Error al consultar " +
                            "la ExchangeRate API para la moneda " + codigoMonedaBase);
                }
                Moneda miMoneda = new Moneda(monedaER);

                // Muestro el resultado de la conversión entre monedas
                System.out.println("####################################");
                System.out.println(
                        montoAConvertir + " " + codigoMonedaBase + " = " +
                                BigDecimal.valueOf(miConsultaDeMoneda.convierteMontoEntreMonedas(
                                        montoAConvertir, miMoneda, codigoMonedaDestino)) +
                                " " + codigoMonedaDestino);
                System.out.println("####################################" + "\n" );

            } catch (ErrorAlConsultarPrecioException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error con la URL a consultar");
                System.out.println(e.getMessage());
            }  catch (InputMismatchException e) {
                System.out.println("Error al ingresar los datos");
                System.out.println(e.getMessage());
                lectorDeTeclado.next();
                break;
            } catch (IOException | InterruptedException e) {
                System.out.println("Error en la conexión con la API");
                System.out.println(e.getMessage());
            }

        }

        System.out.println("\nSaliendo de la app.");
        System.out.println("Gracias por utilizar nuestro conversor de monedas.");
    }
}
