package rip;

import org.junit.Before;
import org.junit.Test;
import rip.url.Url;
import rip.url.UrlCreator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ResourceTest {

    private static final String BASE_URL = "http://example.com";
    private static final String CONTENTS = "foo";

    private RestClient client = new RestClient();

    private UrlCreator urlCreator = mock(UrlCreator.class);
    private Url url = mock(Url.class);
    private HttpURLConnection connection = mock(HttpURLConnection.class);

    @Before
    public void setUp() throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(CONTENTS.getBytes());
        when(urlCreator.create(BASE_URL)).thenReturn(url);
        when(url.openConnection()).thenReturn(connection);
        when(connection.getInputStream()).thenReturn(stream);
        when(url.getProtocol()).thenReturn("http");

        client.urlCreator = urlCreator;
    }

    @Test
    public void retrievesResource() {
        assertEquals(CONTENTS, client.open(BASE_URL).get());
    }

    @Test
    public void sendsJsonHeader() {
        client.open(BASE_URL).get();

        verify(connection).setRequestProperty("Accept", "application/json");
    }

    @Test
    public void handlesRelativePaths() throws IOException {
        Url baseUrl = mock(Url.class);
        when(baseUrl.getProtocol()).thenReturn("http");
        when(urlCreator.create(BASE_URL)).thenReturn(baseUrl);
        when(urlCreator.create(baseUrl, "bar")).thenReturn(url);

        assertEquals(CONTENTS, client.open(BASE_URL).path("bar").get());
    }

    @Test(expected = InvalidUrl.class)
    public void throwsOnInvalidRelativePath() throws Exception {
        when(urlCreator.create(url, "foo"))
                .thenThrow(new MalformedURLException());

        client.open(BASE_URL).path("foo");
    }
}
