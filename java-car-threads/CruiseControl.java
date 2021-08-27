import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CruiseControl extends Frame {
    CarSimulator car;
    CruiseDisplay disp;
    Controller control;
    Button engineOn;
    Button engineOff;
    Button accelerate;
    Button brake;
    Button on;
    Button off;
    Button resume;

    public CruiseControl() {
        setLayout(new BorderLayout());
        car = new CarSimulator();
        add("Center", car);
        disp = new CruiseDisplay();
        add("East", disp);
        control = new Controller(car, disp);
        engineOn = new Button("engineOn");
        engineOn.addActionListener(e -> {
            car.engineOn();
            control.engineOn();
        });
        engineOff = new Button("engineOff");
        engineOff.addActionListener(e -> {
            car.engineOff();
            control.engineOff();
        });
        accelerate = new Button("accelerate");
        accelerate.addActionListener(e -> {
            car.accelerate();
            control.accelerator();
        });

        brake = new Button("brake");
        brake.addActionListener(e -> {
            car.brake();
            control.brake();
        });

        on = new Button("on");
        on.addActionListener(e -> control.on());

        off = new Button("off");
        off.addActionListener(e -> control.off());

        resume = new Button("resume");
        resume.addActionListener(e -> control.resume());

        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout());
        p1.add(engineOn);
        p1.add(engineOff);
        p1.add(accelerate);
        p1.add(brake);
        p1.add(on);
        p1.add(off);
        p1.add(resume);
        add("South", p1);

        this.addWindowListener(new WindowClosing());
    }

    public void stop() {
        car.engineOff(); // kill engine thread
        control.engineOff();
    }

    static class WindowClosing extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent evt) {
            System.exit(0);
        }
    }
}