package com.filipemarruda.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class FileHandler {
	
	public static File inputStreamToFile(InputStream is) throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt"); //$NON-NLS-1$ //$NON-NLS-2$
        file.deleteOnExit();

        Reader reader = new InputStreamReader(is);
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));

        int read = 0;
		char[] bytes = new char[1024];

		while ((read = reader.read(bytes)) != -1) {
			writer.write(bytes, 0, read);
		}
		
        writer.close();

        return file;
    }
	
}
