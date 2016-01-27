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
 * Color LCDImage demo.
 *
 * @author Igor Cordas
 * @since 27-01-2016
 */
public class LCDImage {

    public static void main(String[] args) throws Exception {
        //img 160 x 43
        String url = null;
        int time = 2000;

        if (args.length > 0) {
            url = args[0];
        }
        if (args.length > 1) {
            time = Integer.parseInt(args[1]);
        }

        LcdConnection con = new LcdConnection("LCDImage", false, AppletCapability.getCaps(AppletCapability.BW), null, null);
        try {
            LcdDevice device = con.openDevice(DeviceType.BW, null);
            try {
                LcdMonoBitmap bmp = device.createMonoBitmap(PixelColor.G15_REV_1);
                BufferedImage img = ImageIO.read(new File(url));
                device.setForeground(true);

                Graphics2D g = (Graphics2D) bmp.getGraphics();
                g.setColor(bmp.UNLIT);
                g.fillRect(0, 0, bmp.getImage().getWidth(), bmp.getImage().getHeight());
                g.setColor(bmp.LIT);
                g.drawImage(img, 80-img.getWidth()/2, 22-img.getHeight()/2, null);
                g.dispose();

                bmp.updateScreen(Priority.ALERT, SyncType.SYNC);

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