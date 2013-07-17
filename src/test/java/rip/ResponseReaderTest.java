package rip;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class ResponseReaderTest {

    ResponseReader responseReader = new ResponseReader();

    @Test
    public void readsInputStream() {
        InputStream stream = createStreamContaining("foo");
        assertEquals("foo", responseReader.read(stream));
    }

    @Test
    public void readsConnection() throws IOException {
        InputStream stream = createStreamContaining("foo");
        URLConnection connection = createConnectionReturning(stream);

        assertEquals("foo", responseReader.read(connection));
    }

    @Test
    public void closesStream() throws IOException {
        InputStream stream = spy(createStreamContaining("foo"));
        URLConnection connection = createConnectionReturning(stream);

        responseReader.read(connection);

        verify(stream).close();
    }

    @Test(expected = ConnectionFailure.class)
    public void throwsOnFailureWhileReading() throws IOException {
        InputStream stream = createFailingInputStream();
        responseReader.read(stream);
    }

    @Test(expected = ConnectionFailure.class)
    public void throwsOnFailureWhileConnecting() throws IOException {
        URLConnection connection = createFailingConnection();
        responseReader.read(connection);
    }

    private ByteArrayInputStream createStreamContaining(String contents) {
        return new ByteArrayInputStream(contents.getBytes());
    }

    private InputStream createFailingInputStream() throws IOException {
        InputStream stream = mock(InputStream.class);
        when(stream.read()).thenThrow(new IOException());
        when(stream.read(any(byte[].class))).thenThrow(new IOException());
        when(stream.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException());
        return stream;
    }

    private URLConnection createConnectionReturning(InputStream stream) throws IOException {
        URLConnection connection = mock(URLConnection.class);
        when(connection.getInputStream()).thenReturn(stream);
        return connection;
    }

    private URLConnection createFailingConnection() throws IOException {
        URLConnection connection = mock(URLConnection.class);
        when(connection.getInputStream()).thenThrow(new IOException());
        return connection;
    }
}
