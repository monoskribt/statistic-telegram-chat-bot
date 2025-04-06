package com.statistictgchatbot.service;

import java.io.IOException;

public interface UserEventService {

    void parseUserEventFromFile(String filePath) throws IOException;
}
