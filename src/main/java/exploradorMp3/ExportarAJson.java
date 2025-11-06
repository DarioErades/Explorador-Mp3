package exploradorMp3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exploradorMp3.Model.Cancion;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class ExportarAJson {
    public static void exportarAJson(String rutaBinario, String rutaJson){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaBinario))) {
            List<Cancion> canciones = (List<Cancion>) ois.readObject();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(canciones);

            try (FileWriter fw = new FileWriter(rutaJson)) {
                fw.write(json);
                System.out.println("Exportación a JSON completada. Canciones exportadas: " + canciones.size());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
