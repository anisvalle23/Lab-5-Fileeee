import java.io.File;

public class ProcesadorComandos {
    private File directorioActual;
    private GestorArchivos gestorArchivos;

    public ProcesadorComandos() {
        directorioActual = new File(System.getProperty("user.dir"));
        gestorArchivos = new GestorArchivos(directorioActual);
    }

    public String obtenerDirectorioActual() {
        return directorioActual.getAbsolutePath();
    }

    public String procesarComando(String comando) {
        String[] partesComando = comando.split(" ");
        String accion = partesComando[0].toLowerCase();

        switch (accion) {
            case "mkdir":
                return gestorArchivos.crearDirectorio(partesComando);
            case "mfile":
                return gestorArchivos.crearArchivo(partesComando);
            case "rm":
                return gestorArchivos.eliminar(partesComando);
            case "cd":
                return cambiarDirectorio(partesComando);
            case "dir":
                return gestorArchivos.listarDirectorio();
            case "date":
                return gestorArchivos.obtenerFechaActual();
            case "time":
                return gestorArchivos.obtenerHoraActual();
            case "escribir":
                return gestorArchivos.escribirArchivo(partesComando);
            case "leer":
                return gestorArchivos.leerArchivo(partesComando);
            case "..":
                return regresarDirectorio();
            default:
                return "Comando no reconocido: " + comando;
        }
    }

    private String cambiarDirectorio(String[] partesComando) {
        if (partesComando.length < 2) {
            return "Directorio no especificado.";
        }
        File dir = new File(directorioActual, partesComando[1]);
        if (dir.exists() && dir.isDirectory()) {
            directorioActual = dir;
            gestorArchivos.setDirectorioActual(directorioActual);
            return "Directorio actual: " + directorioActual.getAbsolutePath();
        } else {
            return "Directorio no encontrado: " + dir.getAbsolutePath();
        }
    }

    private String regresarDirectorio() {
        File directorioPadre = directorioActual.getParentFile();
        if (directorioPadre != null) {
            directorioActual = directorioPadre;
            gestorArchivos.setDirectorioActual(directorioActual);
            return "Directorio actual: " + directorioActual.getAbsolutePath();
        } else {
            return "No hay directorio superior.";
        }
    }
}
