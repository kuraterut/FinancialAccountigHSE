package org.kuraterut.FinancialAccountingHSE.adapters;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlAdapter {
    public static <T> T readXml(InputStream fileStream, Class<T> clazz) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(fileStream, clazz);
    }

    public static <T> void writeXml(String filePath, T data) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File(filePath), data);
    }

    public static <T> List<T> readXmlList(InputStream fileStream, Class<T> clazz) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(fileStream, xmlMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> void writeXmlList(String filePath, List<T> data) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File(filePath), data);
    }
}