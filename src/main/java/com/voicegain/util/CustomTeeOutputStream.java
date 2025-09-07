package com.voicegain.util;

import java.io.IOException;
import java.io.OutputStream;

// A custom OutputStream that writes to two underlying OutputStreams
class CustomTeeOutputStream extends OutputStream {

    private final OutputStream stream1;
    private final OutputStream stream2;

    public CustomTeeOutputStream(OutputStream stream1, OutputStream stream2) {
        this.stream1 = stream1;
        this.stream2 = stream2;
    }

    @Override
    public void write(int b) throws IOException {
        stream1.write(b);
        stream2.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        stream1.write(b);
        stream2.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        stream1.write(b, off, len);
        stream2.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        stream1.flush();
        stream2.flush();
    }

    @Override
    public void close() throws IOException {
        try {
            stream1.close();
        } finally {
            stream2.close();
        }
    }
}