package rip;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponseReaderTest {

    ResponseReader responseReader = new ResponseReader();

    @Test(expected = ConnectionFailure.class)
    public void throwsOnFailureWhileReading() throws IOException {
        URLConnection connection = mock(URLConnection.class);
        InputStream stream = createFailingInputStream();
        when(connection.getInputStream()).thenReturn(stream);

        responseReader.read(connection);
    }

    @Test(expected = ConnectionFailure.class)
    public void throwsOnFailureWhileConnecting() throws IOException {
        URLConnection connection = createFailingConnection();
        responseReader.read(connection);
    }

    private InputStream createFailingInputStream() throws IOException {
        InputStream stream = mock(InputStream.class);
        when(stream.read()).thenThrow(new IOException());
        when(stream.read(any(byte[].class))).thenThrow(new IOException());
        when(stream.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException());
        return stream;
    }

    private URLConnection createFailingConnection() throws IOException {
        URLConnection connection = mock(URLConnection.class);
        when(connection.getInputStream()).thenThrow(new IOException());
        return connection;
    }
}
