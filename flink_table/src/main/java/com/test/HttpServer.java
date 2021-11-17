package com.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("server is running");

        while (true) {
            Socket socket = ss.accept();
            System.out.println("connected from " + socket.getRemoteSocketAddress());
            Thread t = new Handler(socket);
        }

    }

    private static class Handler extends Thread {
        Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (InputStream inputStream = socket.getInputStream()) {
                try (OutputStream outputStream = socket.getOutputStream()) {
                    handler(inputStream, outputStream);
                }
            } catch (Exception e) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println("client disconnected");
            }
        }

        public void handler(InputStream input, OutputStream outputStream) throws IOException {
            System.out.println("Process new http requext ...");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            Boolean requestOk = false;
            String first = reader.readLine();
            if (first.startsWith("GET / HTTP/1.")) {
                requestOk = true;
            }
            while (true) {
                String header = reader.readLine();
                if (header.isEmpty()) {
                    break;
                }
                System.out.println(header);
            }
            System.out.println(requestOk ? "Response OK" : "Response Error");
            if (!requestOk) {
                writer.write("HTTP/1.0 404 Not Found\r\n");
                writer.write("Content-Length:0\r\n");
                writer.write("\r\n");
                writer.flush();
            } else {
                String data = "<html><body><h1>Hello, world!</h1></body></html>";
                int length = data.getBytes(StandardCharsets.UTF_8).length;
                writer.write("HTTP/1.0 200 OK\r\n");
                writer.write("Connection: close\r\n");
                writer.write("Content-Type: text/html\r\n");
                writer.write("Content-Length: " + length + "\r\n");
                writer.write("\r\n"); // 空行标识Header和Body的分隔
                writer.write(data);
                writer.flush();

            }

        }
    }
}
