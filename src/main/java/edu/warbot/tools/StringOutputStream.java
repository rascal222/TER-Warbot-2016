package edu.warbot.tools;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by beugnon on 02/10/15.
 *
 */
public class StringOutputStream extends OutputStream {

    StringBuilder mBuf=new StringBuilder();

    @Override
    public void write(int byt) throws IOException {
        mBuf.append((char) byt);
    }

    public String getString() {
        return mBuf.toString();
    }
}