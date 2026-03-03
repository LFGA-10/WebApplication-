package com.downloader.skillswitch.model;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 1234);
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                PrintWriter output = new PrintWriter(
                        socket.getOutputStream(), true
                );
        ) {
            output.println("Hello Server");

            String response = input.readLine();
            System.out.println("Server says: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
