import java.util.function.Function;

public class PlayWithIdentity {

    public static void main(String[] args) {
        var identity = Function.identity();
        System.out.println(identity.apply("Return this"));
    }
}