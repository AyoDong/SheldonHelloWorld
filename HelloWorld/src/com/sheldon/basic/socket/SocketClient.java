package com.sheldon.basic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

	public static void main(String[] args) throws IOException {

		Socket socket = null;
		InputStream input = null;
		String host = "127.0.0.1";
		int port = 55533;

		try {
			socket = new Socket(host, port);
			String message = "Hello Dude";
			socket.getOutputStream().write(message.getBytes("UTF-8"));
			//It needs a shutdown.
			socket.shutdownOutput();

			input = socket.getInputStream();
			byte[] bytes = new byte[1024];
			int len;
			StringBuffer sb = new StringBuffer();

			while ((len = input.read(bytes)) != -1) {
				sb.append(new String(bytes, 0, len, "UTF-8"));
			}
			
			System.out.println("Get message from server : " + sb);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			input.close();
			socket.close();
		}
	}
}
