package simple_http_server;

import java.io.*;
import java.net.*;
import java.nio.file.*;

public class SimpleHTTPServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server listening on port 8080...");

        while (true) {
            Socket socket = serverSocket.accept();
            handleRequest(socket);
        }
    }

    private static void handleRequest(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream()
        ) {
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            System.out.println("Request: " + requestLine);

            // Parse path from request
            String[] parts = requestLine.split(" ");
            if (parts.length < 2) return;

            String path = parts[1];

            // Basic routing
switch (path) {
    case "/":
        path = "/simple_http_server/index.html";
        break;
    case "/about":
        path = "/simple_http_server/about.html";
        break;
    case "/contact":
        path = "/simple_http_server/contact.html";
        break;
    default:
        if (!path.startsWith("/simple_http_server/")) {
            path = "/simple_http_server" + path;
        }
        break;
}

            // Remove leading "/" for File constructor
            File file = new File(path.substring(1));

            if (file.exists() && !file.isDirectory()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String mimeType = getMimeType(path);

                out.write(("HTTP/1.1 200 OK\r\n" +
                           "Content-Length: " + bytes.length + "\r\n" +
                           "Content-Type: " + mimeType + "\r\n" +
                           "\r\n").getBytes());
                out.write(bytes);
            } else {
                String notFound = "<h1>404 Not Found</h1>";
                out.write(("HTTP/1.1 404 Not Found\r\n" +
                           "Content-Length: " + notFound.length() + "\r\n" +
                           "Content-Type: text/html\r\n" +
                           "\r\n" + notFound).getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    private static String getMimeType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".json")) return "application/json";
        return "application/octet-stream";
    }
}
