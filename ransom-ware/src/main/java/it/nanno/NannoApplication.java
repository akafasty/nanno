package it.nanno;

import it.nanno.api.decryptor.IDecryptor;
import it.nanno.api.encrpytor.IEncryptor;
import it.nanno.decryptor.DefaultDecryptorImpl;
import it.nanno.encryptor.DefaultEncryptorImpl;
import it.nanno.util.FileUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class NannoApplication {

    private final IEncryptor encryptor;
    private final IDecryptor decryptor;

    public NannoApplication() {
        this.encryptor = new DefaultEncryptorImpl();
        this.decryptor = new DefaultDecryptorImpl();
    }

    public void doTheJob(String... arguments) throws InterruptedException {

        File targetFolder = Paths.get(System.getProperty("user.dir")).toFile();

        if (!targetFolder.exists()) {

            System.out.println("target folder doesn't exists.");
            System.exit(43);
            return;

        }

        Consumer<File> action = null;
        AtomicInteger current = new AtomicInteger(0);

        if (arguments[0].equals("--decrypt"))
            action = decryptor::decryptFile;

        if (arguments[0].equals("--encrypt"))
            action = encryptor::encryptFile;

        ExecutorService executor = Executors.newFixedThreadPool(400);
        Consumer<File> finalAction = action;
        List<File> fileCollection = FileUtils.listAllFiles(targetFolder);

        fileCollection.forEach(file -> executor.execute(() -> {

            finalAction.accept(file);
            current.getAndIncrement();

        }));

        executor.shutdown();

        while (!executor.isTerminated()) {}

        System.out.println("terminou");
        System.exit(0);

    }

}
