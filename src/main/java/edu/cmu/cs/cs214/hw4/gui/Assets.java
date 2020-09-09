package edu.cmu.cs.cs214.hw4.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads all game assets into the GUI
 *
 */
public class Assets {
    private BufferedImage carcassonneTileImage;
    private static final int TILE_IMAGE_COLS = 6;
    private static final int TILE_PIXEL = 90;

    public Assets() {
    }
    /**
     * Loads the assets from file
     */
    public void load() throws IOException {
        carcassonneTileImage = ImageIO.read(new File("src/main/resources/Carcassonne.png"));
        Assets.Features.load();
    }

    /**
     * returns a tile image by the given index
     * @param index
     * @return
     */
    public BufferedImage getTileImage(int index) {
        int col = index % TILE_IMAGE_COLS;
        int row = index / (TILE_IMAGE_COLS);
        int x = col * TILE_PIXEL; int y = row * TILE_PIXEL;
        return carcassonneTileImage.getSubimage(x, y, TILE_PIXEL, TILE_PIXEL);
    }

    /**
     * Inner class Image provides all image related transformations
     */
    static class Image {
        /**
         * Cite: 214 Piazza Post
         * Returns a rotated instance of a BufferedImage
         *
         * @param src Buffered image to rotate
         * @param n   degree of rotation
         * @return dest  a new instance of the rotated Buffered image
         */
        public static BufferedImage rotateClockwise(BufferedImage src, int n) {
            int weight = src.getWidth();
            int height = src.getHeight();

            AffineTransform at = AffineTransform.getQuadrantRotateInstance(n/2, weight / 2.0, height / 2.0);
            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

            BufferedImage dest = new BufferedImage(weight, height, src.getType());
            op.filter(src, dest);
            return dest;
        }

        /**
         * Cite: 214 Piazza Post
         *
         * @param src    the Buffered image on which to draw circle
         * @param color  color of the circle
         * @param x      the x position of the circle
         * @param y      the y position of the circle
         * @param radius the radius of the circle
         * @return a new Buffered image with a circle drawn on top
         */
        public static BufferedImage withCircle(BufferedImage src, Color color, int x, int y, int radius) {
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
            Graphics2D g = (Graphics2D) dest.getGraphics();
            g.drawImage(src, 0, 0, null);
            g.setColor(color);
            g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
            g.dispose();
            return dest;
        }
    }
    public static class Features{
        /** a map of feature locations, with tile index as key,
         * array of Points as each feature's relative positions with basis of 100 x 100*/
        static Map<Integer, Point[]> locations = new HashMap<>();
        static void load(){
            locations.put(0, new Point[]{new Point(50, 50), new Point(55,85)});
            locations.put(1, new Point[]{new Point(50, 50)});
            locations.put(2, new Point[]{new Point(50, 50)});
            locations.put(3, new Point[]{new Point(50, 50), new Point(85, 50)});
            locations.put(4, new Point[]{new Point(50, 15)});
            locations.put(5, new Point[]{new Point(50, 50)});
            locations.put(6, new Point[]{new Point(50, 50)});
            locations.put(7, new Point[]{new Point(88, 50), new Point(10, 50)});
            locations.put(8, new Point[]{new Point(90, 50), new Point(50, 90)});
            locations.put(9, new Point[]{new Point(50, 10), new Point(60, 65)});
            locations.put(10, new Point[]{new Point(85, 50), new Point(40, 35)});
            locations.put(11, new Point[]{new Point(85, 60), new Point(45, 25), new Point(40, 80), new Point(10, 50)});
            locations.put(12, new Point[]{new Point(20, 30)});
            locations.put(13, new Point[]{new Point(20, 30)});
            locations.put(14, new Point[]{new Point(20, 30), new Point(70, 70)});
            locations.put(15, new Point[]{new Point(20, 30), new Point(70, 70)});
            locations.put(16, new Point[]{new Point(50, 50)});
            locations.put(17, new Point[]{new Point(50, 50)});
            locations.put(18, new Point[]{new Point(50, 50), new Point(50, 85)});
            locations.put(19, new Point[]{new Point(50, 50), new Point(50, 85)});
            locations.put(20, new Point[]{new Point(50, 50)});
            locations.put(21, new Point[]{new Point(50, 50)});
            locations.put(22, new Point[]{new Point(85, 50), new Point(50, 80), new Point(10, 50)});
            locations.put(23, new Point[]{new Point(60, 15), new Point(80, 55), new Point(50, 85), new Point(15, 50)});
        }
    }
}
