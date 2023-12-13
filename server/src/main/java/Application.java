import it.nanno.server.NannoSocketServer;

import java.io.IOException;

public class Application {

    public static void main(String... arguments) throws IOException {

        NannoSocketServer nannoSocketServer = new NannoSocketServer();

        nannoSocketServer.setupRegistry();
        nannoSocketServer.setupSocketServer();
        nannoSocketServer.listen();

    }

}
