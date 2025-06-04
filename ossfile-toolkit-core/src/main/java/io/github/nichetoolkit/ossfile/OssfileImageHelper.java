package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.image.OssImageReadException;
import io.github.nichetoolkit.ossfile.image.OssImageTransferException;
import io.github.nichetoolkit.ossfile.image.OssImageWriteException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * <code>OssfileImageHelper</code>
 * <p>The ossfile image helper class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class OssfileImageHelper {

    /**
     * <code>write</code>
     * <p>The write method.</p>
     * @param bufferedImage {@link java.awt.image.BufferedImage} <p>The buffered image parameter is <code>BufferedImage</code> type.</p>
     * @param outputStream  {@link java.io.OutputStream} <p>The output stream parameter is <code>OutputStream</code> type.</p>
     * @throws OssImageWriteException {@link io.github.nichetoolkit.ossfile.image.OssImageWriteException} <p>The oss image write exception is <code>OssImageWriteException</code> type.</p>
     * @see java.awt.image.BufferedImage
     * @see java.io.OutputStream
     * @see io.github.nichetoolkit.ossfile.image.OssImageWriteException
     */
    public static void write(BufferedImage bufferedImage, OutputStream outputStream) throws OssImageWriteException {
        try {
            ImageIO.write(bufferedImage, OssfileConstants.DEFAULT_IMAGE_SUFFIX, outputStream);
        } catch (IOException exception) {
            throw new OssImageWriteException(exception.getMessage());
        }
    }

    /**
     * <code>write</code>
     * <p>The write method.</p>
     * @param bufferedImage {@link java.awt.image.BufferedImage} <p>The buffered image parameter is <code>BufferedImage</code> type.</p>
     * @param imagePath     {@link java.lang.String} <p>The image path parameter is <code>String</code> type.</p>
     * @throws OssImageWriteException {@link io.github.nichetoolkit.ossfile.image.OssImageWriteException} <p>The oss image write exception is <code>OssImageWriteException</code> type.</p>
     * @see java.awt.image.BufferedImage
     * @see java.lang.String
     * @see io.github.nichetoolkit.ossfile.image.OssImageWriteException
     */
    public static void write(BufferedImage bufferedImage, String imagePath) throws OssImageWriteException {
        try {
            ImageIO.write(bufferedImage, OssfileConstants.DEFAULT_IMAGE_SUFFIX, new File(imagePath));
        } catch (IOException exception) {
            throw new OssImageWriteException(exception.getMessage());
        }
    }

    /**
     * <code>write</code>
     * <p>The write method.</p>
     * @param bufferedImage {@link java.awt.image.BufferedImage} <p>The buffered image parameter is <code>BufferedImage</code> type.</p>
     * @param file          {@link java.io.File} <p>The file parameter is <code>File</code> type.</p>
     * @throws OssImageWriteException {@link io.github.nichetoolkit.ossfile.image.OssImageWriteException} <p>The oss image write exception is <code>OssImageWriteException</code> type.</p>
     * @see java.awt.image.BufferedImage
     * @see java.io.File
     * @see io.github.nichetoolkit.ossfile.image.OssImageWriteException
     */
    public static void write(BufferedImage bufferedImage, File file) throws OssImageWriteException {
        try {
            ImageIO.write(bufferedImage, OssfileConstants.DEFAULT_IMAGE_SUFFIX, file);
        } catch (IOException exception) {
            throw new OssImageWriteException(exception.getMessage());
        }
    }


    /**
     * <code>write</code>
     * <p>The write method.</p>
     * @param bufferedImage {@link java.awt.image.BufferedImage} <p>The buffered image parameter is <code>BufferedImage</code> type.</p>
     * @param formatName    {@link java.lang.String} <p>The format name parameter is <code>String</code> type.</p>
     * @param outputStream  {@link java.io.OutputStream} <p>The output stream parameter is <code>OutputStream</code> type.</p>
     * @throws OssImageWriteException {@link io.github.nichetoolkit.ossfile.image.OssImageWriteException} <p>The oss image write exception is <code>OssImageWriteException</code> type.</p>
     * @see java.awt.image.BufferedImage
     * @see java.lang.String
     * @see java.io.OutputStream
     * @see io.github.nichetoolkit.ossfile.image.OssImageWriteException
     */
    public static void write(BufferedImage bufferedImage, String formatName, OutputStream outputStream) throws OssImageWriteException {
        try {
            ImageIO.write(bufferedImage, formatName, outputStream);
        } catch (IOException exception) {
            throw new OssImageWriteException(exception.getMessage());
        }
    }

    /**
     * <code>write</code>
     * <p>The write method.</p>
     * @param bufferedImage {@link java.awt.image.BufferedImage} <p>The buffered image parameter is <code>BufferedImage</code> type.</p>
     * @param formatName    {@link java.lang.String} <p>The format name parameter is <code>String</code> type.</p>
     * @param imagePath     {@link java.lang.String} <p>The image path parameter is <code>String</code> type.</p>
     * @throws OssImageWriteException {@link io.github.nichetoolkit.ossfile.image.OssImageWriteException} <p>The oss image write exception is <code>OssImageWriteException</code> type.</p>
     * @see java.awt.image.BufferedImage
     * @see java.lang.String
     * @see io.github.nichetoolkit.ossfile.image.OssImageWriteException
     */
    public static void write(BufferedImage bufferedImage, String formatName, String imagePath) throws OssImageWriteException {
        try {
            ImageIO.write(bufferedImage, formatName, new File(imagePath));
        } catch (IOException exception) {
            throw new OssImageWriteException(exception.getMessage());
        }
    }

    /**
     * <code>write</code>
     * <p>The write method.</p>
     * @param bufferedImage {@link java.awt.image.BufferedImage} <p>The buffered image parameter is <code>BufferedImage</code> type.</p>
     * @param formatName    {@link java.lang.String} <p>The format name parameter is <code>String</code> type.</p>
     * @param file          {@link java.io.File} <p>The file parameter is <code>File</code> type.</p>
     * @throws OssImageWriteException {@link io.github.nichetoolkit.ossfile.image.OssImageWriteException} <p>The oss image write exception is <code>OssImageWriteException</code> type.</p>
     * @see java.awt.image.BufferedImage
     * @see java.lang.String
     * @see java.io.File
     * @see io.github.nichetoolkit.ossfile.image.OssImageWriteException
     */
    public static void write(BufferedImage bufferedImage, String formatName, File file) throws OssImageWriteException {
        try {
            ImageIO.write(bufferedImage, formatName, file);
        } catch (IOException exception) {
            throw new OssImageWriteException(exception.getMessage());
        }
    }

    /**
     * <code>read</code>
     * <p>The read method.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link java.awt.image.BufferedImage} <p>The read return object is <code>BufferedImage</code> type.</p>
     * @throws OssImageReadException {@link io.github.nichetoolkit.ossfile.image.OssImageReadException} <p>The oss image read exception is <code>OssImageReadException</code> type.</p>
     * @see java.io.InputStream
     * @see java.awt.image.BufferedImage
     * @see io.github.nichetoolkit.ossfile.image.OssImageReadException
     */
    public static BufferedImage read(InputStream inputStream) throws OssImageReadException {
        try {
            return ImageIO.read(inputStream);
        } catch (IOException exception) {
            throw new OssImageReadException(exception.getMessage());
        }
    }

    /**
     * <code>read</code>
     * <p>The read method.</p>
     * @param imagePath {@link java.lang.String} <p>The image path parameter is <code>String</code> type.</p>
     * @return {@link java.awt.image.BufferedImage} <p>The read return object is <code>BufferedImage</code> type.</p>
     * @throws OssImageReadException {@link io.github.nichetoolkit.ossfile.image.OssImageReadException} <p>The oss image read exception is <code>OssImageReadException</code> type.</p>
     * @see java.lang.String
     * @see java.awt.image.BufferedImage
     * @see io.github.nichetoolkit.ossfile.image.OssImageReadException
     */
    public static BufferedImage read(String imagePath) throws OssImageReadException {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException exception) {
            throw new OssImageReadException(exception.getMessage());
        }
    }


    /**
     * <code>read</code>
     * <p>The read method.</p>
     * @param file {@link java.io.File} <p>The file parameter is <code>File</code> type.</p>
     * @return {@link java.awt.image.BufferedImage} <p>The read return object is <code>BufferedImage</code> type.</p>
     * @throws OssImageReadException {@link io.github.nichetoolkit.ossfile.image.OssImageReadException} <p>The oss image read exception is <code>OssImageReadException</code> type.</p>
     * @see java.io.File
     * @see java.awt.image.BufferedImage
     * @see io.github.nichetoolkit.ossfile.image.OssImageReadException
     */
    public static BufferedImage read(File file) throws OssImageReadException {
        try {
            return ImageIO.read(file);
        } catch (IOException exception) {
            throw new OssImageReadException(exception.getMessage());
        }
    }


    /**
     * <code>read</code>
     * <p>The read method.</p>
     * @param bufferedImage {@link java.awt.image.BufferedImage} <p>The buffered image parameter is <code>BufferedImage</code> type.</p>
     * @return {@link java.io.InputStream} <p>The read return object is <code>InputStream</code> type.</p>
     * @throws OssImageTransferException {@link io.github.nichetoolkit.ossfile.image.OssImageTransferException} <p>The oss image transfer exception is <code>OssImageTransferException</code> type.</p>
     * @see java.awt.image.BufferedImage
     * @see java.io.InputStream
     * @see io.github.nichetoolkit.ossfile.image.OssImageTransferException
     */
    public static InputStream read(BufferedImage bufferedImage) throws OssImageTransferException {
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream;
        try {
            imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);
            ImageIO.write(bufferedImage, OssfileConstants.DEFAULT_IMAGE_SUFFIX, imageOutputStream);
            inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException exception) {
            throw new OssImageTransferException(exception.getMessage());
        }
        return inputStream;
    }

    /**
     * <code>bytes</code>
     * <p>The bytes method.</p>
     * @param bufferedImage {@link java.awt.image.BufferedImage} <p>The buffered image parameter is <code>BufferedImage</code> type.</p>
     * @return byte <p>The bytes return object is <code>byte</code> type.</p>
     * @throws OssImageTransferException {@link io.github.nichetoolkit.ossfile.image.OssImageTransferException} <p>The oss image transfer exception is <code>OssImageTransferException</code> type.</p>
     * @see java.awt.image.BufferedImage
     * @see io.github.nichetoolkit.ossfile.image.OssImageTransferException
     */
    public static byte[] bytes(BufferedImage bufferedImage) throws OssImageTransferException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream;
        try {
            imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);
            ImageIO.write(bufferedImage, OssfileConstants.DEFAULT_IMAGE_SUFFIX, imageOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException exception) {
            throw new OssImageTransferException(exception.getMessage());
        }
    }

}
