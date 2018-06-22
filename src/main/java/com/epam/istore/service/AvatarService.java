package com.epam.istore.service;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Path;

import static com.epam.istore.messages.Messages.AVATAR_DIRECTORY;
import static com.epam.istore.messages.Messages.PNG;
import static com.epam.istore.messages.Messages.PHOTO;

public class AvatarService {
    private static final String DEFAULT_IMAGE = "/img/avdef.jpg";
    private final static Logger LOGGER = Logger.getRootLogger();
    private final static String PATH_TO_TEMP_FOLDER = "/temp";

    public void uploadAvatar(HttpServletRequest request, String fileName) {
        String fileDirectory = getDirectory(request);
        try {
            Part part = request.getPart(PHOTO);
            part.write(fileDirectory + File.separator + fileName + PNG);
        } catch (IOException | ServletException e) {
            LOGGER.error(e);
        }
    }

    public void removeTempAvatar(HttpServletRequest request, String filename){
        File tempImageToRemove = new File(request.getServletContext().getRealPath(PATH_TO_TEMP_FOLDER)+File.separator+filename+PNG);
        tempImageToRemove.delete();
    }

    public void uploadAvatarToTempFolder(HttpServletRequest request, String fileName) throws IOException {
        File file = new File(getFilePath(request,fileName).toUri());
        FileUtils.copyFile(file,getTempFileDirecotry(request,file));
        request.getSession().setAttribute("avatar",PATH_TO_TEMP_FOLDER);
    }

    private File getTempFileDirecotry(HttpServletRequest request, File file){
        return new File(request.getServletContext().getRealPath(PATH_TO_TEMP_FOLDER)+File.separator+file.getName());
    }

    public Path getFilePath(HttpServletRequest request, String fileName) {
        File imageFile = new File(getDirectory(request) + File.separator + fileName + PNG);
        if (imageFile.exists()) {
            return imageFile.toPath();
        }
        return new File(request.getServletContext().getRealPath(DEFAULT_IMAGE)).toPath();
    }

    private String getDirectory(HttpServletRequest request) {
        return request.getServletContext().getInitParameter(AVATAR_DIRECTORY);
    }
}
