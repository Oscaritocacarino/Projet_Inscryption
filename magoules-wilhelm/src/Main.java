
import jeu.Run;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("BIENVENUE DANS INSCRYPTION MOUAHAHAHAH !");
        Scanner scanner = new Scanner(System.in);
        Run run = new Run(scanner);

        run.playRun();

        scanner.close();

    }
}
