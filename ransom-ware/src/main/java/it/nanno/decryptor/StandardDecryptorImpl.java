package it.nanno.decryptor;

import it.nanno.Constants;
import it.nanno.api.decryptor.IDecryptor;
import it.nanno.util.XOR;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

public class StandardDecryptorImpl implements IDecryptor {

    @Override
    public void decryptFile(File file) {

        System.out.println(file.getAbsolutePath());

        try (InputStream inputStream = Files.newInputStream(file.toPath())) {

            String[] split = file.getName().split("\\.");

            String extension = split[file.getName().split("\\.").length - 1];
            String finalName = file.getName().replace(".-encrypt", "");

            boolean isImage = extension.equals("png") || extension.equals("jpg") || extension.equals("jar");

            int byteIndex = 0;
            int byteRead;

            byte[] buffer = new byte[inputStream.available()];

            while ((byteRead = inputStream.read()) != -1) {

                if (!isImage && byteRead == 0)
                    continue;

                buffer[byteIndex++] = (byte) byteRead;

            }

            byte[] bufferCopy = Base64.getDecoder().decode(XOR.dexor(new String(buffer), Constants.TEST_KEY).getBytes(StandardCharsets.UTF_8));

            if (!Arrays.equals(Arrays.copyOf(bufferCopy, Constants.WATERMARK.length), Constants.WATERMARK)) {
                return;
            }

            try (OutputStream outputStream = Files.newOutputStream(file.toPath().getParent().resolve(finalName))) {

                bufferCopy = Arrays.copyOfRange(bufferCopy, Constants.WATERMARK.length, bufferCopy.length);

                outputStream.write(bufferCopy);

            }

            file.delete();

        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
