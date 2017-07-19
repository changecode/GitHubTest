package com.java.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.java.utils.validate.StringUtil;

public abstract class IOUtil {

	private static final String CHARSET = "utf-8";

	public static byte[] readBytes(InputStream inputStream, int bufSize) throws IOException {
		byte[] buf = new byte[bufSize];
		byte[] data = new byte[0];
		int length = 0;
		while ((length = inputStream.read(buf)) > 0) {
			int oldLength = data.length;
			byte[] temp = new byte[oldLength];
			System.arraycopy(data, 0, temp, 0, oldLength);
			data = new byte[length + oldLength];
			System.arraycopy(temp, 0, data, 0, oldLength);
			System.arraycopy(buf, 0, data, oldLength, length);
		}
		return data;
	}

	public static String readString(InputStream inputStream, int bufSize) throws IOException {
		return new String(readBytes(inputStream, bufSize), CHARSET);
	}

	public static void write(OutputStream outputStream, String message)
			throws UnsupportedEncodingException, IOException {
		outputStream.write(message.getBytes(CHARSET));
		outputStream.flush();
	}

	public static Writer NULL_WRITER = new NullWriter();

	public static void copy(Reader reader, Writer writer) throws IOException {
		char[] buf = new char[8192];
		int n = 0;
		while ((n = reader.read(buf)) != -1) {
			writer.write(buf, 0, n);
		}
	}

	public static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[8192];
		int n = 0;
		while ((n = in.read(buf)) != -1) {
			out.write(buf, 0, n);
		}
	}

	public static List<String> readLines(Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		List<String> list = new ArrayList<String>();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}
		return list;
	}

	public static String readFile(File file) throws IOException {
		Reader in = new FileReader(file);
		StringWriter out = new StringWriter();
		copy(in, out);
		in.close();
		return out.toString();
	}

	public static String readFile(File file, String encoding) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		try {
			return toString(encoding, inputStream);
		} finally {
			inputStream.close();
		}
	}

	public static String toString(InputStream inputStream) throws UnsupportedEncodingException, IOException {
		Reader reader = new InputStreamReader(inputStream);
		StringWriter writer = new StringWriter();
		copy(reader, writer);
		return writer.toString();
	}

	public static String toString(String encoding, InputStream inputStream)
			throws UnsupportedEncodingException, IOException {
		Reader reader = new InputStreamReader(inputStream, encoding);
		StringWriter writer = new StringWriter();
		copy(reader, writer);
		return writer.toString();
	}

	public static void saveFile(File file, String content) {
		saveFile(file, content, null, false);
	}

	public static void saveFile(File file, String content, boolean append) {
		saveFile(file, content, null, append);
	}

	public static void saveFile(File file, String content, String encoding) {
		saveFile(file, content, encoding, false);
	}

	public static void saveFile(File file, String content, String encoding, boolean append) {
		try {
			FileOutputStream output = new FileOutputStream(file, append);
			Writer writer = StringUtil.isBlank(encoding) ? new OutputStreamWriter(output)
					: new OutputStreamWriter(output, encoding);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static class NullWriter extends Writer {
		public void close() throws IOException {
		}

		public void flush() throws IOException {
		}

		public void write(char[] cbuf, int off, int len) throws IOException {
		}
	}

	public static void copyAndClose(InputStream in, OutputStream out) throws IOException {
		try {
			copy(in, out);
		} finally {
			close(in, out);
		}
	}

	public static void close(InputStream in, OutputStream out) {
		try {
			if (in != null)
				in.close();
		} catch (Exception e) {
		}
		;
		try {
			if (out != null)
				out.close();
		} catch (Exception e) {
		}
		;
	}

}
