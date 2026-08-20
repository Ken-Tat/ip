public class Oreo {
    private static final String NAME = "Oreo";

    public static void main(String[] args) {
        String banner = "  OOO   RRRR   EEEEE  OOO  \n"
                + " O   O  R   R  E     O   O \n"
                + " O   O  RRRR   EEEE  O   O \n"
                + " O   O  R R    E     O   O \n"
                + "  OOO   R  RR  EEEEE  OOO  \n";

        String Greeting = "____________________________________________ \n"
                + banner + "\n"
                + "Hello! I'm " + NAME + ". \n"
                + "What can I do for you? \n"
                + "____________________________________________";
        System.out.println(Greeting);

        String Goodbye = "____________________________________________ \n"
                + "Bye. Hope to see you again soon! \n"
                + "____________________________________________ \n";
        System.out.println(Goodbye);
    }
}
