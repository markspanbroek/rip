package rip;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class RequestWriterTest {

    RequestWriter requestWriter = new RequestWriter();

    @Test(expected = ConnectionFailure.class)
    public void throwsOnFailureWhileWriting() throws IOException {
        URLConnection connection = mock(URLConnection.class);
        OutputStream stream = createFailingOutputStream();
        when(connection.getOutputStream()).thenReturn(stream);

        requestWriter.write(connection, "");
    }

    @Test(expected = ConnectionFailure.class)
    public void throwsOnFailureWhileConnecting() throws IOException {
        URLConnection connection = createFailingConnection();
        requestWriter.write(connection, "");
    }

    private OutputStream createFailingOutputStream() throws IOException {
        OutputStream stream = mock(OutputStream.class);
        doThrow(new IOException()).when(stream).write(anyByte());
        doThrow(new IOException()).when(stream).write(any(byte[].class));
        doThrow(new IOException()).when(stream).write(any(byte[].class), anyInt(), anyInt());
        return stream;
    }

    private URLConnection createFailingConnection() throws IOException {
        URLConnection connection = mock(URLConnection.class);
        when(connection.getOutputStream()).thenThrow(new IOException());
        return connection;
    }
}
