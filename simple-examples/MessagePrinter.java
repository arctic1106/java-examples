void main() {
    GreetingMessage greetingMessage = (name) -> {
        System.out.println("Hello " + name);
        return 1;
    };
    MessagePrinter messagePrinter = () -> System.out.println(greetingMessage.greet("John"));
    messagePrinter.printMessage();
}

@FunctionalInterface
public interface GreetingMessage {
    public abstract int greet(String name);
}

@FunctionalInterface
public interface MessagePrinter {
    public abstract void printMessage();
}
