package com.blinkflow.flowrun_listener.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

public class CachedBodyServletInputStream extends ServletInputStream {
	private final ByteArrayInputStream buffer;
	  
	public CachedBodyServletInputStream(byte[] body) {
        this.buffer = new ByteArrayInputStream(body);
    }
	
	@Override
	public boolean isFinished() {
		 return buffer.available() == 0;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void setReadListener(ReadListener arg0) {
	}

	@Override
	public int read() throws IOException {
		return buffer.read();
	}

}
