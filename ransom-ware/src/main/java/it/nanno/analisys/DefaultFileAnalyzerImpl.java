package it.nanno.analisys;

import it.nanno.Constants;
import it.nanno.api.analisys.IFileAnalyzer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class DefaultFileAnalyzerImpl implements IFileAnalyzer {
    @Override
    public boolean isIntegrity(File file) throws IOException {

        if (!file.canRead() || !file.canWrite())
            return false;

        boolean mustSave = false;

        try (InputStream inputStream = Files.newInputStream(file.toPath())) {

            int byteRead;
            int byteIndex = 0;
            int nullCount = 0;

            byte[] buffer = new byte[inputStream.available()];

            while ((byteRead = inputStream.read()) != -1) {

                if (byteRead == 0)
                    nullCount++;

                buffer[byteIndex++] = (byte) byteRead;

            }

            if (nullCount >= buffer.length)
                return false;

            File destination = new File("output", file.getName());

            destination.createNewFile();

            try (OutputStream outputStream = Files.newOutputStream(destination.toPath())) {

                outputStream.write(buffer);

            }
        }

        return mustSave;

    }
}
