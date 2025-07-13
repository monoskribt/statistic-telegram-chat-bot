package com.statistictgchatbot.service.impl;

import com.statistictgchatbot.props.BotProps;
import com.statistictgchatbot.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.Optional;

import static com.statistictgchatbot.constant.Constants.PATH_TO_UPLOADED_FILE;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final BotProps botProps;

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
            String filePath = jsonObject
                    .getJSONObject("result")
                    .getString("file_path");

            URL fileUrl = new URL("https://api.telegram.org/file/bot" + botProps.token() + "/" + filePath);
            log.info("File url: {}", fileUrl);

            try (InputStream inputStream = fileUrl.openStream()) {
                java.io.File localFile =
                        new java.io.File(PATH_TO_UPLOADED_FILE + fileName);
                FileUtils.copyInputStreamToFile(inputStream, localFile);
                log.info("File successfully saved");
            }
        }
    }

    @Override
    public void deleteFileFromLocal(String fileName) {
        File file = new File(PATH_TO_UPLOADED_FILE + fileName);
        Optional.of(file)
                .filter(File::exists)
                .ifPresent(File::delete);
    }

}