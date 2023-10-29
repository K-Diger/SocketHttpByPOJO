package server.core;

import server.util.Const;
import server.util.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static server.util.Const.*;


public class Server {

    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    listen(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listen(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream os = clientSocket.getOutputStream()) {

            String requestData = reader.readLine();
            if (requestData == null) {
                clientSocket.close();
                return;
            }

            String[] requests = requestData.split(" ");
            String method = requests[0];
            String uri = requests[1];

            Map<String, String> headers = new HashMap<>();
            String httpHeader;

            while ((httpHeader = reader.readLine()) != null && !httpHeader.isEmpty()) {
                String[] headerContents = httpHeader.split(": ");
                if (headerContents.length == 2) {
                    headers.put(headerContents[0], headerContents[1]);
                }
            }

            StringBuilder requestBody = new StringBuilder();
            if (POST_METHOD.equals(method) || PUT_METHOD.equals(method) || DELETE_METHOD.equals(method)) {
                int contentLength = Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH, "0"));
                for (int i = 0; i < contentLength; i++) {
                    requestBody.append((char) reader.read());
                }
            }

            String response = Const.DEFAULT_RESPONSE_FORM_TYPE;
            HttpHandler httpHandler = HttpHandler.INSTANCE;

            if (method.equals(GET_METHOD) || method.equals(POST_METHOD) || method.equals(PUT_METHOD) || method.equals(DELETE_METHOD)) {
                String handledResponse = httpHandler.handle(method, uri, requestBody);
                response += handledResponse;
            } else {
                response = Const.E501_FAILED_RESPONSE_FORM;
            }
            os.write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}