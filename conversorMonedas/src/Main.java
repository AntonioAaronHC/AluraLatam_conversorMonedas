import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "api_key";

    public static void main(String[] args) {
        mostrarMenu();
    }

    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Conversor de Monedas ===");
            System.out.println("1. Dólar a Peso argentino");
            System.out.println("2. Peso argentino a Dólar");
            System.out.println("3. Dólar a Peso mexicano");
            System.out.println("4. Peso mexicano a Dólar");
            System.out.println("5. Dólar a Euro");
            System.out.println("6. Euro a Dólar");
            System.out.println("7. Dólar a Peso colombiano");
            System.out.println("8. Peso colombiano a Dólar");
            System.out.println("9. Dólar a Real brasileño");
            System.out.println("10. Real brasileño a Dólar");
            System.out.println("11. Euro a Peso mexicano");
            System.out.println("12. Peso mexicano a Euro");
            System.out.println("13. Dólar a Peso chileno");
            System.out.println("14. Peso chileno a Dólar");
            System.out.println("15. Euro a Peso argentino");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> convertir("USD", "ARS");
                case 2 -> convertir("ARS", "USD");
                case 3 -> convertir("USD", "MXN");
                case 4 -> convertir("MXN", "USD");
                case 5 -> convertir("USD", "EUR");
                case 6 -> convertir("EUR", "USD");
                case 7 -> convertir("USD", "COP");
                case 8 -> convertir("COP", "USD");
                case 9 -> convertir("USD", "BRL");
                case 10 -> convertir("BRL", "USD");
                case 11 -> convertir("EUR", "MXN");
                case 12 -> convertir("MXN", "EUR");
                case 13 -> convertir("USD", "CLP");
                case 14 -> convertir("CLP", "USD");
                case 15 -> convertir("EUR", "ARS");
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }


    public static void convertir(String from, String to) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Ingresa la cantidad de %s a convertir: ", from);
        double amount = scanner.nextDouble();

        try {
            String url = String.format(
                    "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s",
                    API_KEY, from, to
            );

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject json = gson.fromJson(response.body(), JsonObject.class);

            if (json.has("result") && json.get("result").getAsString().equals("success")) {
                double rate = json.get("conversion_rate").getAsDouble();
                double result = amount * rate;

                System.out.printf("➡ %.2f %s = %.2f %s%n", amount, from, result, to);
            } else {
                System.out.println("Error en la conversión. Verifica las monedas o la API key.");
            }

        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}
