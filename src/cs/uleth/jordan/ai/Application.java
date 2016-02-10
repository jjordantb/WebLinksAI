package cs.uleth.jordan.ai;

import cs.uleth.jordan.ai.util.PathFinder;
import cs.uleth.jordan.ai.wrappers.WebNode;

/**
 * Created by Jordan on 2/8/2016.
 *
 * Class for main function
 */
public class Application {

    public static void main(String[] args) {
        final WebNode startNode = new WebNode("http://www.uleth.ca", null);
        /*startNode.populateChildNodes();
        for (WebNode wn : startNode.getChildren()) {
            System.out.println(wn.getPath());
        }*/

        final WebNode endNode = new WebNode("http://www.uleth.ca/bookstore", null);
        final PathFinder pathFinder = new PathFinder(startNode, endNode);

        WebNode finalNode = pathFinder.findPath();

        System.out.println("*******************************************************************");
        System.out.println("                        ERMEGERD IT FUND IT");
        System.out.println(finalNode.getPath());
        while (finalNode.getParent() != null) {
            System.out.println(finalNode.getParent().getPath());
            finalNode = finalNode.getParent();
        }
        System.out.println("*******************************************************************");
    }

}
