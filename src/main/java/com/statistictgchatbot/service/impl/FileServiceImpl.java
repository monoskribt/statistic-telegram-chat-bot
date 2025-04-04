package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.props.BotProps;
import com.statistictgchatbot.service.FileService;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Component
public class FileServiceImpl implements FileService {

    private final BotProps botProps;
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    public FileServiceImpl(BotProps botProps) {
        this.botProps = botProps;
    }

    @Override
    public void uploadFile(String fileName, String fileId) throws IOException {
        URL url = new URL("https://api.telegram.org/bot" + botProps.token() + "/getFile?file_id=" + fileId);
        log.info("Url: {}", url);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            log.info("Content from url: {}", jsonBuilder);

            JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
            String filePath = jsonObject.getJSONObject("result").getString("file_path");

            URL fileUrl = new URL("https://api.telegram.org/file/bot" + botProps.token() + "/" + filePath);
            log.info("File url: {}", fileUrl);

            try (InputStream inputStream = fileUrl.openStream()) {
                java.io.File localFile =
                        new java.io.File("src/main/resources/uploaded/" + fileName);
                FileUtils.copyInputStreamToFile(inputStream, localFile);
            }
        }
    }
}
