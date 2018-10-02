package review.servlet.utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class PhotoUtils {

    private static final Logger logger = Logger.getLogger(PhotoUtils.class);

    public static byte[] downloadPhoto(Part file) {
        logger.info("Start downloading photo...");
        byte[] fileContent = null;
        if (file != null) {
            try {
                InputStream inputStream = file.getInputStream();
                if (inputStream == null) {
                    logger.info("File inputstream is null");
                }
                fileContent = IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                logger.error("Error saving uploaded file");
            }
        }
        return fileContent;
    }

}
