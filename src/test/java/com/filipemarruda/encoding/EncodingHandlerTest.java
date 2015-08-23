package com.filipemarruda.encoding;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.filipemarruda.bundle.Properties;
import com.filipemarruda.http.HttpConnector;
import com.filipemarruda.http.IHttpConnector;
import com.filipemarruda.util.MockUtil;

public class EncodingHandlerTest {

	final EncodingHandler eHWrong = new EncodingHandler("", "", "");

	@Mock
	private IHttpConnector httpConnector;

	@Before
	public void setUp() {
		httpConnector = mock(HttpConnector.class);
	}

	@Test(expected = java.io.IOException.class)
	public void getMediaInfo_1() throws Exception {

		final String mediaId = "";
		eHWrong.getMediaInfo(mediaId);

	}

	@Test(expected=java.net.MalformedURLException.class)
	public void processMedia_1() throws Exception {

		final String mediaId = "";
		final String format = "";
		eHWrong.processMedia(mediaId, format);

	}

	@Test(expected = java.io.IOException.class)
	public void getStatus_1() throws Exception {

		final String mediaId = "";
		eHWrong.getStatus(mediaId);

	}

	@Test(expected = java.io.IOException.class)
	public void addMedia_1() throws Exception {

		final String source = "";
		final String destination = "";

		eHWrong.addMedia(source, destination);

	}

	@Test
	public void addMedia_3() throws Exception {

		// setup mocks
		when(httpConnector.simplePost(Properties.getString("EncodingEndpoint"),
				MockUtil.createMockPayload(Properties.getString("EncodingAddMediaXML")))).thenReturn("Success");
		final EncodingHandler eH = MockUtil.createEncodingHandlerMock();
		// injecting mock
		eH.setHttpConnector(httpConnector);

		final String source = MockUtil.createSouceMock();
		final String destination = MockUtil.createDestinationMock();
		final String response = eH.addMedia(source, destination);

		assertFalse(response.contains("error"));

	}

	@Test(expected = java.io.IOException.class)
	public void addMediaBenchmark_1() throws Exception {

		final String source = "";
		final String destination = "";

		eHWrong.addMediaBenchmark(source, destination);

	}

	@Test
	public void addMediaBenchmark_3() throws Exception {

		// setup mocks
		when(httpConnector.simplePost(Properties.getString("EncodingEndpoint"),
				MockUtil.createMockPayload(Properties.getString("EncodingAddMediaBenchmarkXML")))).thenReturn("Success");
		final EncodingHandler eH = MockUtil.createEncodingHandlerMock();
		// injecting mock
		eH.setHttpConnector(httpConnector);

		final String source = MockUtil.createSouceMock();
		final String destination = MockUtil.createDestinationMock();
		final String response = eH.addMediaBenchmark(source, destination);

		assertFalse(response.contains("error"));

	}

	

}
