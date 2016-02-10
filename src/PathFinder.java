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
     * @return returns the end webnode
     */
    public WebNode findPath() {
        final LinkedList<WebNode> queue = new LinkedList<>();
        queue.add(startNode);
        startNode.setVisited(true);
        while (!queue.isEmpty()) {
            WebNode testNode = queue.remove();
            if (testNode.getPath().equals(this.endNode.getPath())) {
                return testNode;
            }
            testNode.populateChildNodes();
            queue.addAll(testNode.getChildren());
            System.out.println("Working.... Queue Size: " + queue.size());
        }
        return null;
    }

    public WebNode getEndNode() {
        return endNode;
    }

    public WebNode getStartNode() {
        return startNode;
    }
}
