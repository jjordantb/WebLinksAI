package cs.uleth.jordan.ai.util;

import cs.uleth.jordan.ai.wrappers.WebNode;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Jordan on 2/9/2016.
 *
 * Provides the implementation of a depth first search for local files
 * and a breadth first search for websites
 */
public class PathFinder {

    private final WebNode startNode, endNode;

    public PathFinder(final WebNode startNode, final WebNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    /**
     * Finds a path of WebNodes between the start and end WebNodes
     *
     * @return List of WebNodes that provide a path between two web nodes
     */
    public ArrayList<WebNode> findPath() {
        if (startNode.isURL()) {
            final LinkedList<WebNode> queue = new LinkedList<>();
            queue.add(startNode);

        } else {

        }
        return null;
    }

}
