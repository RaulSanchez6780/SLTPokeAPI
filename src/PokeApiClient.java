import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.json.JSONArray;

public class PokeApiClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/tepig";

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
        } else {
            System.out.println("Data not recieved. Status code: " + response.statusCode());
        }
    }  
}
