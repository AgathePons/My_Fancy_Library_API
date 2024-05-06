package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingTestController {
    Logger logger = LoggerFactory.getLogger(LoggingTestController.class);

    @RequestMapping("test")
    public String index() {
        logger.trace("TRACE message");
        logger.debug("DEBUG message");
        logger.info("INFO message");
        logger.warn("WARN message");
        logger.error("ERROR message");

        return "Test LOG bip boop...";
    }
}
