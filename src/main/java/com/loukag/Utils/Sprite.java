package com.loukag.Utils;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Sprite {

    /**
     * Loads an image from a path
     * @param path path to the image
     * @return the image
     */
    public static BufferedImage load(String path){
        try {
            return ImageIO.read(Objects.requireNonNull(Sprite.class.getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads multiple images from a list of paths
     * @param paths list of paths to the images
     * @return the images
     */
    public static BufferedImage[] loadSheet(String[] paths){
        BufferedImage[] images = new BufferedImage[paths.length];
        for(int i = 0; i < paths.length; i++){
            images[i] = load(paths[i]);
        }
        return images;
    }

    /**
     * Flip an image horizontally
     * @param originalImage the image to flip
     * @return the flipped image
     */
    public static BufferedImage mirrorImageHorizontally(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage mirroredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-width, 0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        mirroredImage = op.filter(originalImage, mirroredImage);

        return mirroredImage;
    }

}
