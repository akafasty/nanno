package it.nanno.server.packet.spy;

import it.nanno.server.api.packet.Packet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PacketPlayInScreen implements Packet<BufferedImage> {

    private BufferedImage bufferedImage;
    private String username;

    @Override
    public void fillPacketWithData(ObjectInputStream objectInputStream) {

        try {
            this.username = objectInputStream.readUTF();
            this.bufferedImage = ImageIO.read(objectInputStream);
        }

        catch (Exception exception) { exception.printStackTrace(); }

    }

    @Override
    public void doSomething() {

        Path path = Paths.get(System.getProperty("user.dir"))
                .resolve(username);
        //File file = Paths.get(System.currentTimeMillis() + ".png").toFile();

        path.toFile().mkdirs();

        path = path.resolve(System.currentTimeMillis() + ".png");

        File file = path.toFile();

        try { file.createNewFile(); ImageIO.write(bufferedImage, "png", file); }
        catch (Exception exception) { exception.printStackTrace(); }

    }

}
