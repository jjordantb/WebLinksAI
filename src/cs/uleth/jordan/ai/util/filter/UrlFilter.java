package cs.uleth.jordan.ai.util.filter;


/**
 * Created by Jordan on 2/9/2016.
 *
 * Implementation of A String filter to remove strings that contain file extensions
 * or are stylesheets/javascript files
 */
public class UrlFilter implements Filter<String> {

    @Override
    public boolean accept(String webNode) {
        return !webNode.endsWith(".jpg") && !webNode.endsWith(".png")
                && !webNode.endsWith(".gif") && !webNode.endsWith(".mp4")
                && !webNode.endsWith(".pdf") && !webNode.endsWith(".xml")
                && !webNode.contains(".css")
                && (webNode.startsWith("https://") || webNode.startsWith("http://"));
    }

}
