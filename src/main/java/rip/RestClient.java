package rip;

import java.net.MalformedURLException;
import java.net.URL;

public class RestClient {

    public Resource open(String url) {
        return new Resource(createUrl(url));
    }

    private URL createUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException exception) {
            throw new MalformedUrl(exception);
        }
    }

}
