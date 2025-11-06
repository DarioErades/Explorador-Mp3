# 🎵 Explorador MP3

Este proyecto permite explorar directorios en busca de archivos MP3, extraer su información mediante la cabecera **ID3v1**, y guardar los datos en un archivo binario con objetos serializados.  
También permite leer ese archivo binario y mostrar la información por consola.

---

## ⚙️ Cómo compilar y ejecutar en Eclipse

### 🧩 Compilación

1. Abre **Eclipse** y crea un nuevo **proyecto Java**.
2. Copia todos los archivos `.java` dentro de la carpeta `src` del proyecto.
3. Eclipse compilará automáticamente los archivos si no hay errores de sintaxis.

---

### ▶️ Ejecución

#### 🔹 Modo `-E` (Explorar y guardar)

1. Haz clic derecho sobre la clase `Main.java` → **Run As → Run Configurations...**
2. Crea una nueva configuración en **Java Application**.
3. En la pestaña **Arguments**, dentro del campo **Program arguments**, escribe:

       -E rutas.txt salida.bin

*(O los argumentos que desees usar).*

4. Asegúrate de que el archivo `rutas.txt` existe y contiene rutas de directorios, **una por línea**.
5. Pulsa **Run** para ejecutar el programa.

---

#### 🔹 Modo `-L` (Leer y mostrar)

1. Repite los pasos anteriores, pero en el campo **Program arguments** escribe:
 
        -L salida.bin

*(O el nombre del archivo binario que desees leer).*

2. Pulsa **Run** para ver la información de las canciones en la consola.

---

## 📦 Dependencias y versión de Java

- **Java:** versión 17
- **Librerías externas:** no se utilizan dependencias adicionales (solo clases estándar de Java).

---

## 🧠 Decisiones de diseño

- Se utiliza **`RandomAccessFile`** para leer los últimos 128 bytes de cada archivo MP3, donde se encuentra la cabecera **ID3v1**.
- La clase `Cancion` implementa `Serializable` para poder guardar y leer listas de canciones en archivos binarios.
- Si un archivo MP3 no contiene cabecera ID3v1, igualmente se guarda con los campos vacíos.
- Se usa **`System.lineSeparator()`** para asegurar compatibilidad con diferentes sistemas operativos.
- El archivo binario de salida se **sobrescribe** en cada ejecución del modo `-E`.
- El código está organizado por responsabilidad (lectura, exploración, serialización, etc.) para facilitar el mantenimiento.

---

## ⚠️ Limitaciones y trabajo futuro

### Limitaciones

- Solo se analiza la **cabecera ID3v1** (no se soporta ID3v2 ni otros formatos de metadatos).
- No se traduce el **código numérico del género musical** a su nombre textual.
- No se evita la duplicación de canciones si aparecen en varios directorios.

### Trabajo futuro

- Añadir soporte para **ID3v2** y otros estándares más recientes.
- Implementar un sistema de detección de duplicados (por título o checksum del archivo).
- Mejorar la interfaz de salida con una vista gráfica o web.
- Permitir exportar también en formato **JSON o CSV** desde la propia aplicación.

---

## 👨‍💻 Autor

**Darío Erades**



