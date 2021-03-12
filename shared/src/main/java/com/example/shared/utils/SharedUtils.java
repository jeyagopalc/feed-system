package com.example.shared.utils;

import com.example.shared.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static com.example.shared.error.ErrorCodes.*;
import static org.apache.commons.codec.digest.DigestUtils.*;

public class SharedUtils {
    private static final Logger logger = LoggerFactory.getLogger(SharedUtils.class);

    public static String FIELD_REGEX = "\\s*,\\s*";

    public static Set<String> parseFields(final String fields) {
        return Set.of(fields.trim().split(FIELD_REGEX));
    }

    public static List<Query> parseQuery(final String query) {
        return Query.toList(query);
    }

    public static Set<String> parseTags(final String tags) {
        return Set.of(tags.trim().split(FIELD_REGEX));
    }

    public static String getFingerPrintMessage(String content) {
        return sha256Hex(content);
    }

    public static String getFingerPrintMessage(InputStream inputStream) throws IOException {
        return sha256Hex(inputStream);
    }

    public static byte[] compressBytes(byte[] data) throws BaseException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        byte[] buffer = new byte[1024];

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            logger.info("Compressed Image Byte Size - " + outputStream.toByteArray().length);
            return outputStream.toByteArray();

        } catch (IOException e) {
            logger.error(FEED_006.getDescription());
            throw new BaseException(FEED_006, e);
        }
    }

    public static byte[] decompressBytes(byte[] data) throws BaseException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        byte[] buffer = new byte[1024];

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        } catch (IOException | DataFormatException e) {
            logger.error(FEED_007.getDescription());
            throw new BaseException(FEED_007, e);
        }
    }
}
