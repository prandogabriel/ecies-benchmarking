package com.prando;

import com.prando.ecies.Ecies;
import com.prando.ecies.common.ECKeyPair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args)  {
        System.out.println(args[0]);

        try (InputStream fileStream = readTestFile()) {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(fileStream, StandardCharsets.UTF_8));

            String fileContent = reader.lines().collect(Collectors.joining());

            ECKeyPair ecKeyPair = Ecies.generateEcKeyPair();

            System.out.println("fileContent ->> " +  fileContent);

            String encrypted = Ecies.encrypt(ecKeyPair.getPublicHex(true), fileContent);
            System.out.println("encrypted ->> " +  encrypted);

            String decrypted = Ecies.decrypt(ecKeyPair.getPrivateHex(), encrypted);
            System.out.println("decrypted ->> " + decrypted);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static InputStream readTestFile()  {
        InputStream stream;
        try {
            File file = new File("/home/prando/facul/ecies-benchmarking/dataset/1kb.txt");

            stream = new FileInputStream(file);
            return stream;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}