package com.statistictgchatbot.service;

import java.io.IOException;

public interface FileService {

    void uploadFile(String fileName, String fileId) throws IOException;

    void deleteFileFromLocal(String fileName);
}
