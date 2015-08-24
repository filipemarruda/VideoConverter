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
package com.filipemarruda.http.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * JUnit test for Convert.
 */
public class ConvertTest {

	/**
	 * Test case for constructor.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void convert()
		throws Exception {

		Convert result = new Convert();

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getServletConfig());
		assertEquals("", result.getServletInfo());
	}

	/**
	 * Test case for method doGet.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected=java.lang.IllegalArgumentException.class)
	public void doGet()
		throws Exception {
		Convert fixture = new Convert();
		HttpServletRequest request = new HttpServletRequestWrapper((HttpServletRequest) null);
		HttpServletResponse response = new HttpServletResponseWrapper((HttpServletResponse) null);

		fixture.doGet(request, response);

	}
	
	/**
	 * Test case for method doPost.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected=java.lang.IllegalArgumentException.class)
	public void doPost()
		throws Exception {
		Convert fixture = new Convert();
		HttpServletRequest request = new HttpServletRequestWrapper((HttpServletRequest) null);
		HttpServletResponse response = new HttpServletResponseWrapper((HttpServletResponse) null);

		fixture.doPost(request, response);

	}
	
}