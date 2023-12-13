package it.nanno.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static List<File> listAllFiles(File folder) {

        List<File> collection = new ArrayList<>();

        for (File file : folder.listFiles()) {

            if (file.isDirectory())
                listSubFiles(file, collection);

            else collection.add(file);

        }

        return collection;

    }

    protected static void listSubFiles(File folder, List<File> colletion) {

        for (File file : folder.listFiles()) {

            if (file.isDirectory())
                listSubFiles(file, colletion);

            else colletion.add(file);

        }

    }

}
