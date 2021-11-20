package com.example.finalporject.M;

import com.drew.imaging.bmp.BmpMetadataReader;
import com.drew.imaging.gif.GifMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.png.PngProcessingException;
import com.drew.imaging.riff.RiffProcessingException;
import com.drew.imaging.tiff.TiffMetadataReader;
import com.drew.imaging.tiff.TiffProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.regex.Pattern;


/**
 * model part
 *
 * @author Yanzhao Li
 * @date 2021/11/19
 */
public class ModelImp extends MBuilder {
    public ModelImp(MBuilder.Builder Builder) {
        super(Builder);
    }

    /**
     * check input format
     *
     * @param outformat outformat
     * @return boolean
     */
    @Override
    public boolean checkFromat(String outformat) {
        return writeFormats.contains(outformat);
    }


    /**
     * convert image in app
     *
     * @param outformat outformat
     * @param fname     帧
     * @return int
     */
    @Override
    public int makeConversion(String outformat, String fname) {
        if (writeFormats.contains(outformat)) {
            int Extension = fname.lastIndexOf(".");
            String outputFormat = fname.substring(0, Extension + 1) + outformat;
            conversionList.put(outputFormat, fname);
            return 1;
        }
        return 0;
    }

    /**
     * download the image to file
     *
     * @param out output URL
     * @return int flag
     * @throws IOException ioexception
     */
    @Override
    public int download(String out) throws IOException {
        String fname = conversionList.get(out);
        File file = fileList.get(fname);
        if (file == null) {
            return 0;
        }
        BufferedImage bim = ImageIO.read(file);
        String outputFormat = super.getExtension(out);
        if (writeFormats.contains(outputFormat)) {
            File output = new File(out);
            ImageIO.write(bim, outputFormat, output);
            return 1;
        }
        return 0;
    }

    /**
     * set file to model
     *
     * @param fileName file name
     * @return int flag
     */
    @Override
    public int setFile(String fileName) {
        File file = new File(fileName);
        String extension = super.getExtension(file.getName());
        if (readFormats.contains(extension) && file.exists()) {
            this.fileList.put(fileName, file);
            return 1;
        }
        return 0;
    }

    /**
     * get file from model
     *
     * @param fileName file name
     * @return {@link File}
     */
    @Override
    public File getFile(String fileName) {
        return fileList.get(fileName);
    }

    /**
     *  remove file from model
     *
     * @param fileName file name
     * @return int
     */
    @Override
    public int remove(String fileName) {
        fileNameList.remove(fileName);
        fileList.remove(fileName);
        return 1;

    }


    /**
     * get image information
     *
     * @param fName file name
     * @return {@link String}
     * @throws JpegProcessingException jpeg processing exception
     * @throws IOException             io exception
     * @throws PngProcessingException  png processing exception
     * @throws TiffProcessingException tiff processing exception
     * @throws RiffProcessingException riff processing exception
     */
    @Override
    public String getTag(String fName) throws JpegProcessingException, IOException, PngProcessingException, TiffProcessingException, RiffProcessingException {
        StringBuffer s = new StringBuffer();
        File file = fileList.get(fName);
        String ex = getExtension(fName).toUpperCase();
        Metadata metadata = new Metadata();
        if (ex.equals("JPG") || ex.equals("JPEG")) {
            metadata = JpegMetadataReader.readMetadata(file);
        } else if (ex.equals("GIF")) {
            metadata = GifMetadataReader.readMetadata(file);
        } else if (ex.equals("PNG")) {
            metadata = PngMetadataReader.readMetadata(file);
        } else if (ex.equals("TIFF") || ex.equals("TIF")) {
            metadata = TiffMetadataReader.readMetadata(file);
        } else if (ex.equals("WBMP") || ex.equals("BMP")) {
            metadata = BmpMetadataReader.readMetadata(file);
        } else {
            return null;
        }
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                if (Pattern.matches(".+((Make )|(Model )|(Image Height )|(Image Width )|(GPS)).+", tag.toString())) {
                    s.append(tag.toString());
                    s.append("\n");
                }
            }
        }
        return s.toString();
    }
}
