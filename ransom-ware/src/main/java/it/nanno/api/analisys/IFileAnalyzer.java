package it.nanno.api.analisys;

import java.io.File;
import java.io.IOException;

public interface IFileAnalyzer {

    boolean isIntegrity(File file) throws IOException;

}
