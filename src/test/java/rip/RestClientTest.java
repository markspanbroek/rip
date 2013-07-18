package rip;

import org.junit.Test;

public class RestClientTest {

    private RestClient client = new RestClient();

    @Test
    public void canOpenResource() {
        client.open("http://example.com");
    }

    @Test(expected = InvalidUrl.class)
    public void throwsOnMalformedUrl() {
        client.open("malformed url");
    }

    @Test(expected = InvalidUrl.class)
    public void throwsOnNonHttpUrl() {
        client.open("ftp://example.com");
    }

    @Test
    public void acceptsHttpsUrl() {
        client.open("https://example.com");
    }

}
