package com.sheldon.basic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	public static void main(String[] args) throws IOException {
		ServerSocket server = null;
		Socket socket = null;
		InputStream inputStream = null;
		OutputStream out = null;
		try {
			int port = 55533;
			server = new ServerSocket(port);
			System.out.println("server is always waiting for connection !!");

			socket = server.accept();
			inputStream = socket.getInputStream();

			byte[] bytes = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();

			while ((len = inputStream.read(bytes)) != -1) {
				sb.append(new String(bytes, 0, len, "UTF-8"));
			}

			System.out.println("Get message from client : " + sb);
			
			out = socket.getOutputStream();
			out.write("Hey, I got you message".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			inputStream.close();
			socket.close();
			server.close();
		}
	}
}
