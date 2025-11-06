package exploradorMp3;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;


import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClienteiTunes {
	public static String obtenerCancion(String term) {
		HttpClient client = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.connectTimeout(Duration.ofSeconds(5))
				.build();

		String url = "https://itunes.apple.com/search?entity=song&limit=1&term="
				+ URLEncoder.encode(term, StandardCharsets.UTF_8);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.timeout(Duration.ofSeconds(10))
				.header("Accept", "application/json")
				.GET()
				.build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int status = response.statusCode();
		registrarPeticionEnLog(status,url);
		if (status == 200) {
			return response.body();
		} else {
			return "";
		}
	}

	private static void registrarPeticionEnLog(int codDevuelto, String url){
		String registro="";
		registro+= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"));
		registro+=";"+url;;
		registro+=";"+codDevuelto;

		try (FileWriter fw = new FileWriter("registroItunes.txt", true);
			 BufferedWriter bw = new BufferedWriter(fw);
			 PrintWriter out = new PrintWriter(bw)) {
			out.println(registro);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ResultadoItunes obtenerResultadoItunes(String term){
		String json = obtenerCancion(term);
		Gson gson = new Gson();


		Type tipoLista = new TypeToken<List<ResultadoItunes>>(){}.getType();
		List<ResultadoItunes> resultados = gson.fromJson(
				gson.fromJson(json, JsonObject.class).get("results"), tipoLista
		);

		if (resultados != null && !resultados.isEmpty()) {
			return resultados.get(0);  // Solo el primero aunque ya se que la lista va a ser solo de uno por el limit que he puesto arriba
		} else {
			return null;
		}

	}

}
