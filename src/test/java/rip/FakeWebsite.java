package rip;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Mocks HTTP connections to a website.
 * Tests that use this class should be annotated to use PowerMock. Any classes
 * that handle URLs and HttpURLConnections should be prepared by PowerMock,
 * including this class.
 *
 * For example:
 * <pre>
 * {@literal @}RunWith(PowerMockRunner.class)
 * {@literal @}PrepareForTest({ SomeClassThatCreatesURL.class, FakeWebsite.class })
 * public class SomeTest {
 * </pre>
 */
public class FakeWebsite {

    public URL url;
    public HttpURLConnection connection;
    public InputStream stream;

    public FakeWebsite(String url) {
        this.url = mock(URL.class);
        try {
            whenNew(URL.class).withArguments(url).thenReturn(this.url);
        } catch (Exception ignored) {
        }
        setContents("");
    }

    public FakeWebsite(String url, String relativePath) {
        URL baseUrl = mock(URL.class);
        this.url = mock(URL.class);
        try {
            whenNew(URL.class).withArguments(url).thenReturn(baseUrl);
            whenNew(URL.class).withArguments(baseUrl, relativePath).thenReturn(this.url);
        } catch (Exception ignored) {
        }
        setContents("");
    }

    public void setContents(String contents) {
        setContents(new ByteArrayInputStream(contents.getBytes()));
    }

    private void setContents(InputStream contents) {
        connection = mock(HttpURLConnection.class);
        stream = contents;

        try {
            when(this.url.openConnection()).thenReturn(connection);
            when(connection.getInputStream()).thenReturn(stream);
        } catch (Exception ignored) {
        }
    }
}
