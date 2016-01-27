package net.djpowell.lcdjni.examples;

import net.djpowell.lcdjni.AppletCapability;
import net.djpowell.lcdjni.DeviceType;
import net.djpowell.lcdjni.LcdConnection;
import net.djpowell.lcdjni.LcdDevice;
import net.djpowell.lcdjni.LcdMonoBitmap;
import net.djpowell.lcdjni.PixelColor;
import net.djpowell.lcdjni.Priority;
import net.djpowell.lcdjni.SyncType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


/**
 * Color HelloWorld demo.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public class HelloWorld {

    public static void main(String[] args) throws Exception {
        String url = null;
        int time = 2000;

        if (args.length > 0) {
            url = args[0];
        }
        if (args.length > 1) {
            time = Integer.parseInt(args[1]);
        }

        LcdConnection con = new LcdConnection("HelloWorld", false, AppletCapability.getCaps(AppletCapability.BW), null, null);
        try {
            LcdDevice device = con.openDevice(DeviceType.BW, null);
            try {
                LcdMonoBitmap bmp = device.createMonoBitmap(PixelColor.G15_REV_1);
                /*
                BufferedImage img = ImageIO.read(new File("ubuntu.png"));
                int w = img.getWidth(null);
                int h = img.getHeight(null);
                System.out.println("Width img " + bmp.getImage().getWidth() + " x  " + bmp.getImage().getHeight() );
                */
                //BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

                //Image img = Toolkit.getDefaultToolkit().getImage("ubuntu.png");
                BufferedImage img = ImageIO.read(new File(url));
                //System.out.println("Width img " + bmp.getImage().getWidth() + " x  " + bmp.getImage().getHeight() );
                /*for (int i = 0; i < 30; i++) {
                    //img.setRGB(0,0,i);
                    System.out.println(img.getRGB(0, i));
                }*/

                device.setForeground(true);

                //for (int i = 0; i < 30; i++) {
                Graphics2D g = (Graphics2D) bmp.getGraphics();
                g.setColor(bmp.UNLIT);
                g.fillRect(0, 0, bmp.getImage().getWidth(), bmp.getImage().getHeight());
                g.setColor(bmp.LIT);
                //g.fillRect(50, 20, 15,15);
                //g.fillOval(i, 10, 10, 10);
                //System.out.println("Move to " + i * 10);
                g.drawImage(img, 0, 0, null);
                //g.drawImage(img, 10, 10, null);
                //g.drawString("Hello World!", 40, 20);
                g.dispose();
                bmp.updateScreen(Priority.ALERT, SyncType.SYNC);

                //    Thread.sleep(16);
                //}
                Thread.sleep(time);
            } finally {
                device.close();
            }
        } finally {
            con.close();
        }
        LcdConnection.deInit();
    }

}
