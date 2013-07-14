package rip;

import org.junit.Test;
import rip.RestClient.MalformedUrl;

public class RestClientTest {

    private RestClient client = new RestClient();

    @Test
    public void canOpenResource() {
        client.open("http://example.com");
    }

    @Test(expected = MalformedUrl.class)
    public void throwsOnMalformedUrl() {
        client.open("malformed url");
    }
}
