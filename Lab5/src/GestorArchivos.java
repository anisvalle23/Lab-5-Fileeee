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

    public String eliminar(String[] partesComando) {
        if (partesComando.length < 2) {
            return "Nombre del archivo o directorio no especificado.";
        }
        File archivo = new File(directorioActual, partesComando[1]);
        if (archivo.exists()) {
            if (archivo.delete()) {
                return archivo.getName() + " eliminado.";
            } else {
                return "Error al eliminar " + archivo.getName();
            }
        } else {
            return "Archivo o directorio no encontrado: " + archivo.getName();
        }
    }

    public String listarDirectorio() {
        StringBuilder salida = new StringBuilder();
        for (File archivo : directorioActual.listFiles()) {
            salida.append(archivo.isDirectory() ? "[DIR] " : "[FILE] ");
            salida.append(archivo.getName()).append("\n");
        }
        return salida.toString();
    }

    public String obtenerFechaActual() {
        return "Fecha actual: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public String obtenerHoraActual() {
        return "Hora actual: " + new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public String escribirArchivo(String[] partesComando) {
        if (partesComando.length < 3) {
            return "Uso: escribir <nombre_archivo> <texto>";
        }
        String nombreArchivo = partesComando[1];
        StringBuilder textoAEscribir = new StringBuilder();
        for (int i = 2; i < partesComando.length; i++) {
            textoAEscribir.append(partesComando[i]).append(" ");
        }
        File archivo = new File(directorioActual, nombreArchivo);
        try (FileWriter escritor = new FileWriter(archivo, true)) {
            escritor.write(textoAEscribir.toString().trim() + "\n");
            return "Texto agregado a " + nombreArchivo;
        } catch (IOException e) {
            return "Error al escribir en el archivo: " + e.getMessage();
        }
    }

    public String leerArchivo(String[] partesComando) {
        if (partesComando.length < 2) {
            return "Uso: leer <nombre_archivo>";
        }
        String nombreArchivo = partesComando[1];
        File archivo = new File(directorioActual, nombreArchivo);
        if (archivo.exists()) {
            try {
                return new String(Files.readAllBytes(Paths.get(archivo.getAbsolutePath())));
            } catch (IOException e) {
                return "Error al leer el archivo: " + e.getMessage();
            }
        } else {
            return "Archivo no encontrado: " + nombreArchivo;
        }
    }
}
