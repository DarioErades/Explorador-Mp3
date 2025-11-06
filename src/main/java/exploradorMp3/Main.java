package exploradorMp3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Main {
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Debes introducir como mínimo dos argumentos");
			return; 
		}
		
		if(args[0].equalsIgnoreCase("-E")) {
			/*
			 * En este modo la aplicación recibirá como segundo parámetro la
				ruta de un archivo de texto.
				▪ En cada línea de este archivo de texto tendremos una ruta a un
				Directorio (puede haber más de una línea).
				▪ La aplicación escaneará recursivamente cada uno de estos
				directorios en busca de archivos MP3.
				▪ Para cada archivo mp3 leerá su cabecera ID3v1.
				▪ De cada archivo se guardará en un archivo binario con objetos
				serializados la siguiente información: ruta y nombre del archivo
				junto con los datos existentes en la cabecera ID3. El nombre y
				ruta del archivo binario se recibirá como tercer parámetro.
				▪ Para simplificar, en el caso de que la aplicación se ejecute 2
				veces y ya exista un archivo serializado de una ejecución
				anterior, lo que haremos será eliminar el archivo existente y
				generar uno nuevo a partir de la exploración de los directorios de
				la nueva ejecución.
			 */
			String rutaArchivos = args[1];
			if (args.length < 3) {
                System.out.println("Falta el parámetro para el archivo binario de salida");
                return;
            }
			String archivoSalida = args[2];

			List<String> rutasDirectorio = new ArrayList<>();
			try {
                rutasDirectorio = Files.readAllLines(Paths.get(rutaArchivos), StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.err.println("Error leyendo las rutas del archivo");
                e.printStackTrace();
                return;
            }
			
			List<Cancion> canciones = new ArrayList<>();
			
			 for (String linea : rutasDirectorio) {
	                if (linea == null) continue; // Para que no de error si hay lineas vacias
	                String ruta = linea.trim();
	                if (ruta.isEmpty()) continue;
	                File dir = new File(ruta);
	                if (!dir.exists() || !dir.isDirectory()) {
	                    System.err.println("No es un directorio válido: " + ruta);
	                    continue;
	                }
	                // Aqui solo llega la ejecucuión si la linea es realmente una carpeta
	                ExploradorMP3.buscarMp3Recursivo(dir, canciones); // Ejecuto método para buscar mp3 y meterlo en la lista de canciones
	            }
			 
			 
			 // Serializo la lista entera
			 
			 try (FileOutputStream fs = new FileOutputStream(archivoSalida);
	                 ObjectOutputStream os = new ObjectOutputStream(fs)) {
	                os.writeObject(canciones);
	                System.out.println("Serialización completada. Canciones guardadas: " + canciones.size());
	            } catch (IOException e) {
	                System.err.println("Error serializando en: " + archivoSalida);
	                e.printStackTrace();
	            }

		}else if(args[0].equalsIgnoreCase("-L")) {
			 String ficheroBinario = args[1];
			    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroBinario))) {
			        List<Cancion> canciones = (List<Cancion>) ois.readObject();
			        for (Cancion c : canciones) {
			            System.out.println(c);
			        }
			    } catch (IOException | ClassNotFoundException e) {
			        System.err.println("Error leyendo el fichero binario: " + ficheroBinario);
			        e.printStackTrace();
			    }

		}else if (args[0].equalsIgnoreCase("-J")) {
			if (args.length < 3) {
				System.out.println("Faltan parámetros: ruta del binario y ruta de salida JSON");
				return;
			}
			ExportarAJson.exportarAJson(args[1],args[2]);
		}else {
			System.out.println("El modo introducido no es válido");
		}
	}
	

    




}
