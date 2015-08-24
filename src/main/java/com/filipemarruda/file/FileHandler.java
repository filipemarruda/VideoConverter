/*****************************************************************
 * Copyright (c) 2015, Filipe Mendes
 *
 * Video Converter
 * 
 * This project consists in a simple video converter made in order 
 * to the people from SambaTech evaluate my programming skills
 * 
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * 1. Redistributions of source code must retain the above 
 * copyright notice, this list of conditions and the following 
 * disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above 
 * copyright notice, this list of conditions and the following 
 * disclaimer in the documentation and/or other materials provided 
 * with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of 
 * its contributors may be used to endorse or promote products 
 * derived from this software without specific prior written 
 * permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************/
package com.filipemarruda.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * The Class FileHandler.
 */
public class FileHandler {

	/**
	 * Input stream to file.
	 *
	 * @param is
	 *            the is
	 * @return the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
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
