package triangles.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import triangles.Computer;
import triangles.Polyangle;
import vectors.TheVector;

public class Test {

    private File location;
    private Polyangle A;
    private Polyangle B;
    private int solution;

    public Test() {
        solution = 1;
    }

    public File getLocation() {
        return location;
    }

    public void setLocation(File location) {
        this.location = location;
    }

    public void setPathOfFile(String pathOfFile) {
        setLocation(new File(pathOfFile));
    }

    public Polyangle getA() {
        return A;
    }

    public void setA(Polyangle A) {
        this.A = A;
    }

    public Polyangle getB() {
        return B;
    }

    public void setB(Polyangle B) {
        this.B = B;
    }

    public static Test openTest(File location) {
        Test test = new Test();
        test.setLocation(location);
        test.setA(Polyangle.read(new File(location, "Pols\\A.txt"), false));
        test.setB(Polyangle.read(new File(location, "Pols\\B.txt"), false));
        test.solution = new File(location, "Solutions").list().length + 1;
        return test;
    }

    public boolean run() throws FileNotFoundException {
        initFolders();
        File f = toPols();
        writePols(f, "");
        f = toSolution(solution);
        Log Main = new Log(toMain(f).getAbsolutePath());
        Long hausStart = Main.timeStamp();
        Main.post("ROUND_KOEF: " + Double.toString(Computer.ROUND_KOEF));
        Polyangle AA = A.clone();
        Polyangle BB = B.clone();
        Computer.optimize(A, B);
        Long hausFinish = Main.getTimeStamp();
        writePols(f, "haus");
        double Haus = Computer.middle(TheVector.getHDistance(A, B));
        Main.post("Haus: " + Double.toString(Haus));
        Log EvM = new Log(toEvM(f).getAbsolutePath());
        Long exSearchStart = Main.getTimeStamp();
        A = AA.clone();
        B = BB.clone();

        Computer.exSearchOptimize(A, B, EvM);
        Long exSearchFinish = Main.getTimeStamp();
        writePols(f, "exs");
        double Grid = Computer.middle(TheVector.getHDistance(A, B));
        Main.post("Grid: " + Double.toString(Grid));
        Main.post("Substraction: " + (Grid - Haus));
        boolean resultOfTest;
        if ((Math.abs(Haus - Grid) > Computer.ROUND_KOEF) && (Haus > Grid)) {
            Main.post("DENIED");
            resultOfTest = false;
        } else {
            Main.post("Accepted");
            resultOfTest = true;
        }
        Main.split();
        Main.post("TIMING:");
        Long substrOfTime = hausFinish - hausStart;
        Calendar Timing = new Calendar.Builder().setInstant(substrOfTime).build();
        Main.post("Haus: "
                + (Timing.get(Calendar.HOUR_OF_DAY) - 5) + "h "
                + Timing.get(Calendar.MINUTE) + "min "
                + Timing.get(Calendar.SECOND) + "sec "
                + Timing.get(Calendar.MILLISECOND) + "msec");
        substrOfTime = exSearchFinish - exSearchStart;
        Timing = new Calendar.Builder().setInstant(substrOfTime).build();
        Main.post("Grid: "
                + (Timing.get(Calendar.HOUR_OF_DAY) - 5) + "h "
                + Timing.get(Calendar.MINUTE) + "min "
                + Timing.get(Calendar.SECOND) + "sec "
                + Timing.get(Calendar.MILLISECOND) + "msec");
        EvM.die();
        Main.die();
        return resultOfTest;
    }

    private void initFolders() {
        location.mkdirs();
        if (location.list().length > 2) {
            throw new RuntimeException("Incorrect test content");
        }
        toPols();
        initSolutions();

    }

    private void initSolutions() {
        File f = toSolutions();
    }

    private File toPols() {
        File f = new File(location, "Pols");
        f.mkdir();
        return f;
    }

    private File toSolutions() {
        File f = new File(location, "solutions");
        f.mkdir();
        return f;
    }

    private File toSolution(int n) {
        File f = new File(toSolutions(), "Solution№" + Integer.toString(n));
        f.mkdir();
        return f;
    }

    private void writePols(File f, String prefix) throws FileNotFoundException {
        try (PrintStream out = new PrintStream(new FileOutputStream(new File(f, prefix + "A.txt")))) {
            out.println(A.toString());
        }
        try (PrintStream out = new PrintStream(new FileOutputStream(new File(f, prefix + "B.txt")))) {
            out.println(B.toString());
        }
    }

    private File toEvM(File f) {
        return new File(f, "EvM.txt");
    }

    private File toMain(File f) {
        return new File(f, "Main.txt");
    }
}
