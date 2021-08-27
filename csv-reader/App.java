void main() throws Exception {
    var datos = CsvFileReader.leerCSV("./data/data.csv");
    IO.print(datos);
}
