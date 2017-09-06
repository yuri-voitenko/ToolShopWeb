package com.epam.preprod.voitenko.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.EMPTY_STRING;

public class SecurityXMLParserUtil {
    private static final Logger LOGGER = LogManager.getLogger(SecurityXMLParserUtil.class);

    public static Map<String, Collection<String>> parse(String path) {
        SAXHandler handler = new SAXHandler();
        tryParseFile(path, handler);
        return handler.getPermissionMap();
    }

    private static void tryParseFile(String path, SAXHandler handler) {
        try {
            File fileForParse = new File(path);
            SAXParserFactory parserFactor = SAXParserFactory.newInstance();
            SAXParser parser = parserFactor.newSAXParser();
            parser.parse(fileForParse, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error(e);
        }
    }
}

class SAXHandler extends DefaultHandler {
    private final String SECURITY = "security";
    private final String CONSTRAINT = "constraint";
    private final String URL_PATTERN = "url-pattern";
    private final String ROLE = "role";

    private Map<String, Collection<String>> permissionMap;
    private String url;
    private Collection<String> roles;
    private String label;

    public Map<String, Collection<String>> getPermissionMap() {
        return permissionMap;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        label = qName;
        if (SECURITY.equals(qName)) {
            permissionMap = new HashMap<>();
        } else if (CONSTRAINT.equals(qName)) {
            roles = new ArrayList<>();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String content = String.copyValueOf(ch, start, length).trim();
        if (URL_PATTERN.equals(label)) {
            url = content;
        } else if (ROLE.equals(label)) {
            roles.add(content);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        label = EMPTY_STRING;
        if (CONSTRAINT.equals(qName)) {
            addPermissionUrlToRoles();
        }
    }

    private void addPermissionUrlToRoles() {
        for (String role : roles) {
            addPermissionUrlToRole(role, url);
        }
    }

    private void addPermissionUrlToRole(String keyRole, String url) {
        Collection<String> urls = new ArrayList<>();
        if (permissionMap.containsKey(keyRole)) {
            urls = permissionMap.get(keyRole);
        }
        urls.add(url);
        permissionMap.put(keyRole, urls);
    }
}