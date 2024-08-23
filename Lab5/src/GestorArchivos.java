import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestorArchivos {
    private File directorioActual;

    public GestorArchivos(File directorioActual) {
        this.directorioActual = directorioActual;
    }

    public void setDirectorioActual(File directorioActual) {
        this.directorioActual = directorioActual;
    }

    public String crearDirectorio(String[] partesComando) {
        if (partesComando.length < 2) {
            return "Nombre del directorio no especificado.";
        }
        File dir = new File(directorioActual, partesComando[1]);
        if (dir.mkdir()) {
            return "Directorio creado: " + dir.getName();
        } else {
            return "Error al crear el directorio.";
        }
    }

    public String crearArchivo(String[] partesComando) {
        if (partesComando.length < 2) {
            return "Nombre del archivo no especificado.";
        }
        File archivo = new File(directorioActual, partesComando[1]);
        try {
            if (archivo.createNewFile()) {
                return "Archivo creado: " + archivo.getName();
            } else {
                return "Error al crear el archivo.";
            }
        } catch (IOException e) {
            return "Error al crear el archivo: " + e.getMessage();
        }
    }
}