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


/**
 * Color LCDImage demo.
 *
 * @author Igor Cordas
 * @since 27-01-2016
 */
public class LCDText {

    public static void main(String[] args) throws Exception {

        String text = null;
        int time = 2000;

        if (args.length > 0) {
            text = args[0];
        }
        if (args.length > 1) {
            time = Integer.parseInt(args[1]);
        }

        LcdConnection con = new LcdConnection("HelloWorldMono", false, AppletCapability.getCaps(AppletCapability.BW), null, null);
        try {
            LcdDevice device = con.openDevice(DeviceType.BW, null);
            try {
                LcdMonoBitmap bmp = device.createMonoBitmap(PixelColor.G15_REV_2);
                Graphics g = bmp.getGraphics();
                g.setColor(bmp.UNLIT);
                g.fillRect(0, 0, bmp.getImage().getWidth(), bmp.getImage().getHeight());
                g.setColor(bmp.LIT);
                g.drawString(text, 40, 20);
                g.dispose();
                bmp.updateScreen(Priority.ALERT, SyncType.SYNC);
                device.setForeground(true);
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