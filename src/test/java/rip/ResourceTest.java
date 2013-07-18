package rip;

import com.google.mockwebserver.MockResponse;
import com.google.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ResourceTest {

    MockWebServer server;
    Resource resource;

    @Before
    public void setUp() throws IOException {
        server = new MockWebServer();
        server.play();
        String serverUrl = server.getUrl("/").toExternalForm();
        resource = new RestClient().open(serverUrl);
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void getsResource() throws InterruptedException, IOException {
        server.enqueue(new MockResponse().setBody("foo"));

        assertEquals("foo", resource.get());
        assertEquals("/", server.takeRequest().getPath());
    }

    @Test
    public void sendsJsonHeader() throws InterruptedException {
        server.enqueue(new MockResponse());

        resource.get();

        assertEquals("application/json", server.takeRequest().getHeader("Accept"));
    }

    @Test
    public void handlesRelativePaths() throws IOException, InterruptedException {
        server.enqueue(new MockResponse());

        resource.path("bar").get();

        assertEquals("/bar", server.takeRequest().getPath());
    }
}
