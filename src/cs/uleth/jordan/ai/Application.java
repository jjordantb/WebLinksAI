package cs.uleth.jordan.ai;

import cs.uleth.jordan.ai.wrappers.WebNode;

/**
 * Created by Jordan on 2/8/2016.
 *
 * Class for main function
 */
public class Application {

    public static void main(String[] args) {
        final WebNode webNode = new WebNode("http://us.battle.net/wow/en/");
        webNode.populateChildNodes();
        for (WebNode wn : webNode.getChildren()) {
            System.out.println(wn.getPath());
        }

    }

}
