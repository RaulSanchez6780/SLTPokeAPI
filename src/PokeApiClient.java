import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Scanner;

public class PokeApiClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Search a Pokemon: ");
        String pokemon = scanner.nextLine().trim().toLowerCase();

        String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemon;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(pokemonUrl)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONObject fullResponse = new JSONObject(response.body());
        
            String name = fullResponse.getString("name");
            System.out.println("Pokemon: " + name);
            int id = fullResponse.getInt("id");
            System.out.println("Pokedex Entry #" + id);

            JSONArray types = fullResponse.getJSONArray("types");

            for (int i = 0; i < types.length(); i++) {
                JSONObject typeInfo = types.getJSONObject(i);
                JSONObject type = typeInfo.getJSONObject("type");

                String pokemonType = type.getString("name");
                System.out.println("Type: " + pokemonType);
            }

            System.out.println();
            JSONArray stats = fullResponse.getJSONArray("stats");

            for (int i = 0; i < stats.length(); i++) {
                JSONObject statInfo = stats.getJSONObject(i);
                JSONObject pokemonStat = statInfo.getJSONObject("stat");

                String pokemonStats = pokemonStat.getString("name");
                int baseStat = statInfo.getInt("base_stat");

                System.out.println(pokemonStats + ": " + baseStat);
            }

        } else {
            System.out.println("That one must not be registered in the Pokedex yet. Please try another pokemon.");
        }

        scanner.close();

    }  
}
