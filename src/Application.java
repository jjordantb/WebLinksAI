import java.util.Scanner;

/**
 * Created by Jordan on 2/8/2016.
 *
 * Class for main function
 */
public class Application {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String startUrl = null;
        String endUrl = null;

        //Getting user input
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Url PathFinder v1.0 finds a series of Urls between the given Urls");
        System.out.print("Enter the start Url: ");
        startUrl = scanner.nextLine();
        System.out.print("Enter the end Url: ");
        endUrl = scanner.nextLine();

        // Pruning links
        startUrl = startUrl.endsWith("/") ? startUrl.substring(0, startUrl.length()) : startUrl;
        endUrl = endUrl.endsWith("/") ? endUrl.substring(0, endUrl.length()) : endUrl;

        final WebNode startNode = new WebNode(startUrl, null);

        final WebNode endNode = new WebNode(endUrl, null);
        final PathFinder pathFinder = new PathFinder(startNode, endNode);

        WebNode finalNode = pathFinder.findPath();

        System.out.println("*******************************************************************");
        System.out.println("Found Path: ");
        System.out.println(finalNode.getPath());
        while (finalNode.getParent() != null) {
            System.out.println(finalNode.getParent().getPath());
            finalNode = finalNode.getParent();
        }

        System.out.println("Time ran: " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds");
        System.out.println("*******************************************************************");
    }

}
