import java.io.File;

/**
 * Created by Enzo on 08.11.15.
 */
public class Query {

    private int queryNum;
    private int tope;
    private int cantActores;
    private File dataFilePath;

    public void setQueryParam(String paramName, String paramValue) {
        if (paramName.equals("query")) {
            try {
                queryNum = Integer.valueOf(paramValue);
                if (queryNum < 1 || queryNum > 4) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor de query incorrecto, debe ser un número en {1, 2, 3, 4}.");
                throw new IllegalArgumentException();
            }

        } else if (paramName.equals("tope")) {
            try {
                tope = Integer.valueOf(paramValue);
                if (tope < 1800 || tope > 2030) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Tope debe ser un valor entre 1800 y 2030");
                throw new IllegalArgumentException();
            }
        } else if (paramName.equals("N")) {
            try {
                cantActores = Integer.valueOf(paramValue);
                if (cantActores > 0 && cantActores < 100000) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("N debe ser un número entre 1 y 100.000");
                throw new IllegalArgumentException();
            }
        } else if (paramName.equals("path")) {
            dataFilePath = new File(paramValue);
            if (dataFilePath.isFile()) {
                System.out.println(String.format("El archivo provisto es inválido: %s", dataFilePath.getPath()));
                throw new IllegalArgumentException();
            }
        } else {
            System.out.println(
                    String.format("El parámetro encontrado '%s' es inválido. Sólo se acepta {query, tope, N, path}",
                            paramName));
            throw new IllegalArgumentException();
        }
    }
}
