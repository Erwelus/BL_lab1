package com.example.bl_lab1.service.impl;

import com.example.bl_lab1.model.UserEntity;
import com.example.bl_lab1.model.Users;
import com.example.bl_lab1.service.XMLUtil;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.List;

@Service
public class XMLUtilImpl implements XMLUtil {
    private final XStream xstream;
    public XMLUtilImpl(){
        this.xstream = new XStream();
    }
    @Override
    public <T> Object getEntity(Class<T> convertClass, String aliasName, String xmlPath) {
        xstream.alias(aliasName, convertClass);
        try {
            File file = new File(xmlPath);
            FileReader reader = new FileReader(file);
            if (reader.read() > 0) {
                JAXBContext jaxbContext = JAXBContext.newInstance(convertClass);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                return jaxbUnmarshaller.unmarshal(file);
            }
        } catch (JAXBException | IOException e) {
            //System.err.println("Failed to load entity from "+xmlPath);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveEntity(List<?> saveEntity, String xmlPath) {
        xstream.alias("user", UserEntity.class);
        xstream.alias("users", List.class);
        try {
            xstream.toXML(saveEntity, new FileWriter(xmlPath, false));
        } catch (IOException e) {
            //System.err.println("Failed to save entity into file "+xmlPath);
            e.printStackTrace();
        }
    }
}
