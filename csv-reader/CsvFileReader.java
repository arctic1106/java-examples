import java.io.*;
import java.util.*;

class CsvFileReader {
    private static final String COMMA_DELIMITER = ",";

    static List<Lectura> leerCSV(String fileName) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        var records = new ArrayList<Lectura>();
        for (int i = 0; i < 7; i++)
            reader.readLine();
        var line = reader.readLine();
        var finished = false;
        while (!finished && line != null) {
            if (!line.isEmpty()) {
                var lineTokens = line.split(COMMA_DELIMITER);
                if (lineTokens.length == 5)
                    records.add(LecturaAdapter.fromTokens(lineTokens));
                else
                    finished = true;
            }
            line = reader.readLine();
        }
        return records;
    }
}
