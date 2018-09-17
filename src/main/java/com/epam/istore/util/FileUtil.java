package com.epam.istore.util;

import com.epam.istore.exception.GadgetShopException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static com.epam.istore.messages.Messages.*;

@Component
@Log4j
public class FileUtil {

    @Value("${upload.path}")
    private String uploadPath;

    private static final String DEFAULT_IMAGE = "/img/avdef.jpg";
    private final static String PATH_TO_TEMP_FOLDER = "/temp";

    public void uploadFile(MultipartFile multipartFile, String fileName) {
        try {
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            multipartFile.transferTo(new File(uploadPath + File.separator + fileName + PNG));
        } catch (IOException e) {
            log.error(e);
            throw new GadgetShopException("Can't upload file", e);
        }
    }

    public void removeTempAvatar(HttpServletRequest request, String filename) {
        File tempImageToRemove = new File(request.getServletContext().getRealPath(PATH_TO_TEMP_FOLDER) + File.separator + filename + PNG);
        tempImageToRemove.delete();
    }

    public void uploadAvatarToTempFolder(HttpServletRequest request, String fileName) {
        try {
            File file = new File(getFilePath(request, fileName).toUri());
            FileUtils.copyFile(file, getTempFileDirecotry(request, file));
            request.getSession().setAttribute("avatar", PATH_TO_TEMP_FOLDER);
        } catch (IOException e) {
            log.error(e);
            throw new GadgetShopException("Can't upload filt to temp folder", e);
        }
    }

    private File getTempFileDirecotry(HttpServletRequest request, File file) {
        return new File(request.getServletContext().getRealPath(PATH_TO_TEMP_FOLDER) + File.separator + file.getName());
    }

    public Path getFilePath(HttpServletRequest request, String fileName) {
        File imageFile = new File(uploadPath + File.separator + fileName + PNG);
        if (imageFile.exists()) {
            return imageFile.toPath();
        }
        return new File(request.getServletContext().getRealPath(DEFAULT_IMAGE)).toPath();
    }

}
