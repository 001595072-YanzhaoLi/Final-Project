package com.example.finalporject.M;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngProcessingException;
import com.drew.imaging.riff.RiffProcessingException;
import com.drew.imaging.tiff.TiffProcessingException;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

/**
 * mbuilder
 *
 * @author Yanzhao Li
 * @date 2021/11/19
 */
public abstract class MBuilder {
    public static final Set<String> readFormats = new HashSet<>(Arrays.asList(ImageIO.getReaderFormatNames()));
    public static final Set<String> writeFormats = new HashSet<>(Arrays.asList(ImageIO.getWriterFormatNames()));
    Set<String> fileNameList;
    Map<String, File> fileList;
    Map<String, String> conversionList;

    /**
     * builder for model
     *
     * @param Builder builder
     */
    public MBuilder(Builder Builder) {
        this.fileList = Builder.fileList;
        this.fileNameList = Builder.fileNameList;
        this.conversionList = Builder.conversionList;
    }

    /**
     * check input format
     *
     * @param outformat outformat
     * @return boolean
     */
    public abstract boolean checkFromat(String outformat);

    /**
     * convert image in app
     *
     * @param outformat outformat
     * @param fname     帧
     * @return int
     */
    public abstract int makeConversion(String outformat, String fname);

    /**
     * download the image to file
     *
     * @param out output URL
     * @return int flag
     * @throws IOException ioexception
     */
    public abstract int download(String out) throws IOException;

    /**
     * set file to model
     *
     * @param fileName file name
     * @return int flag
     */
    public abstract int setFile(String fileName);

    /**
     * get file from model
     *
     * @param fileName file name
     * @return {@link File}
     */
    public abstract File getFile(String fileName);

    /**
     *  remove file from model
     *
     * @param fileName file name
     * @return int
     */
    public abstract int remove(String fileName);

    /**
     * get the image's xtension
     *
     * @param fileName file name
     * @return {@link String}
     */
    String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
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
    public abstract String getTag(String fName) throws JpegProcessingException, IOException, PngProcessingException, TiffProcessingException, RiffProcessingException;


    /**
     * builder for model
     *
     * @author Yanzhao Li
     * @date 2021/11/19
     */
    public static class Builder {
        private Map<String, File> fileList = new HashMap<>();
        private Set<String> fileNameList = new HashSet<>();
        private Map<String, String> conversionList = new HashMap<>();

        /**
         * build the model
         *
         * @return {@link MBuilder}
         */
        public MBuilder build() {
            return new ModelImp(this);
        }
    }
}


