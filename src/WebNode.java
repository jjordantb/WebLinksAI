import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jordan on 2/8/2016.
 *
 * Wrapper class that provides loading and parsing functionality for web pages and html files
 */
public class WebNode {

    public static final Pattern hrefRegex = Pattern.compile("href=\"(.*?)\"");
    public static final Pattern urlRegex = Pattern.compile("(http://.*?)");

    private String path;
    private final String baseUrl;

    private boolean visited;

    private final LinkedList<WebNode> children;
    private WebNode parent;


    public WebNode(final String path, final WebNode parent) {
        this.visited = false;
        this.parent = parent;
        this.path = path;
        this.children = new LinkedList<>();
        final Matcher m = urlRegex.matcher(this.path);
        if (m.find()) {
            this.baseUrl = m.group(1);
        } else {
            this.baseUrl = null;
        }
    }

    /**
     * Used to remove urls that are images, videos, gifs etc...
     *
     * @return true if the url is a valid url
     */
    private boolean validateUrl(final String url) {
        return new UrlFilter().accept(url);
    }

    /**
     * For use in preventing adding duplicate child webnodes
     *
     * @param path
     * @return
     */
    private boolean childrenContains(final String path) {
        for (WebNode wn : this.children) {
            if (wn.getPath().equals(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses the file/website and populates children with links it finds
     */
    public void populateChildNodes() {
        final String data = this.getPageSource();
        if (data != null) {
            final Matcher matcher = hrefRegex.matcher(data);
            while (matcher.find()) {
                final String match = matcher.group(1);
                // For link extensions
                if (this.validateUrl(match) && match.startsWith("/") && !this.childrenContains(this.baseUrl + match)) {
                    this.pushChildNode(new WebNode(this.baseUrl + match, this));
                } else {
                    if (this.validateUrl(match) && !this.childrenContains(match)) {
                        this.pushChildNode(new WebNode(match, this));
                    }
                }
            }
            for (WebNode webNode : this.children) {
                if (webNode.getPath().endsWith("/")) {
                    webNode.setPath(webNode.getPath().substring(0, webNode.getPath().length() - 1));
                }
            }
        }
    }

    /**
     * Gets the page source of the file or website url defined
     *
     * @return page or file source/contents
     */
    public final String getPageSource() {
        try {
            URL url = new URL(this.path);
            URLConnection urlConnection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder a = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                a.append(inputLine);
            }
            reader.close();
            return a.toString();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Add a WebNode to the child array for further processing
     *
     * @param child
     */
    public final void pushChildNode(final WebNode child) {
        this.children.push(child);
    }


    /**
     * Gets the first element of the children
     *
     * @return first element of list
     */
    public final WebNode getUnivisitedNode() {
        for (WebNode webNode : this.children) {
            if (!webNode.isVisited()) {
                return webNode;
            }
        }
        return null;
    }

    /**
     *
     * Setters and Getters
     *
     */

    public WebNode getParent() {
        return parent;
    }

    public void setParent(WebNode parent) {
        this.parent = parent;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public LinkedList<WebNode> getChildren() {
        return children;
    }
}
