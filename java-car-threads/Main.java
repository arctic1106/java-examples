class Main {

    public static void main(String[] args) {
        var frame = new CruiseControl();
        frame.setTitle("Tutorial de Java, AWT");
        frame.setSize(500, 500);
        var miObjCanvas = new CarSimulator();
        frame.add(miObjCanvas, "Center");
        frame.setVisible(true);
        miObjCanvas.run();
    }
}

