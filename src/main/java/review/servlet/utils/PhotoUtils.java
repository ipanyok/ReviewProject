package review.servlet.utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.*;

public class PhotoUtils {

    private static final Logger logger = Logger.getLogger(PhotoUtils.class);

    public static byte[] downloadPhoto(Part file) {
        logger.info("Start downloading photo...");
        byte[] fileContent = null;
        if (file != null) {
            try {
                InputStream inputStream = file.getInputStream();
                if (file.getSize() == 0) {
                    logger.info("Load default image");
                    inputStream = new BufferedInputStream(new FileInputStream(new File(PhotoUtils.class.getResource("/No-image-available.jpg").getFile())));
                }
                fileContent = IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                logger.error("Error saving uploaded file");
            }
        }
        return fileContent;
    }

}
