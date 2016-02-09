package cs.uleth.jordan.ai.wrappers;

import cs.uleth.jordan.ai.util.filter.UrlFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public static final Pattern urlRegex = Pattern.compile("(http://.*?)/");

    private final String path;
    private final boolean isURL;
    private final String baseUrl;

    private final LinkedList<WebNode> children;

    public WebNode(final String url) {
        this(url, true);
    }

    public WebNode(final String path, final boolean isURL) {
        this.path = path;
        this.isURL = isURL;
        this.children = new LinkedList<>();
        if (this.isURL) {
            final Matcher m = urlRegex.matcher(this.path);
            if (m.find()) {
                this.baseUrl = m.group(1);
            } else {
                this.baseUrl = null;
            }
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
     * Parses the file/website and populates children with links it finds
     */
    public void populateChildNodes() {
        final String data = this.getPageSource();
        if (data != null) {
            final Matcher matcher = hrefRegex.matcher(data);
            while (matcher.find()) {
                final String match = matcher.group(1);
                // For link extensions
                if (match.startsWith("/") && match.endsWith("/")) {
                    this.pushChildNode(new WebNode(this.baseUrl + match, this.isURL));
                } else {
                    if (this.validateUrl(match)) {
                        this.pushChildNode(new WebNode(match, this.isURL));
                    }
                }
            }
        } else {
            System.out.println("Unable to populate child nodes");
        }
    }

    /**
     * Gets the page source of the file or website url defined
     *
     * @return page or file source/contents
     */
    public final String getPageSource() {
        try {
            if (this.isURL) {
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
            } else {
                return new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not grab page source");
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
    public final WebNode popNode() {
        if (!this.children.isEmpty()) {
            return this.children.pop();
        }
        return null;
    }

    public boolean isURL() {
        return isURL;
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
