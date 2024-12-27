package principal;

public class Menu {
    private String menuDeOpciones;

    public Menu() {
        this.menuDeOpciones = """
                    1 - ARS - Peso argentino
                    2 - BOB - Boliviano boliviano
                    3 - BRL - Real brasileño
                    4 - CLP - Peso chileno
                    5 - COP - Peso colombiano
                    6 - USD - Dólar estadounidense
                    
                    0 - Salir del conversor""";
    }

    public String getMenuDeOpciones() {
        return menuDeOpciones;
    }

    public void setMenuDeOpciones(String menuDeOpciones) {
        this.menuDeOpciones = menuDeOpciones;
    }

    public String seleccionaMoneda(int opcionDeMenu) {
        switch (opcionDeMenu) {
            case 1:
                return "ARS";
            case 2:
                return "BOB";
            case 3:
                return "BRL";
            case 4:
                return "CLP";
            case 5:
                return "COP";
            case 6:
                return "USD";
            default:
                return "Opción ingresada incorrecta.";
        }
    }
}
