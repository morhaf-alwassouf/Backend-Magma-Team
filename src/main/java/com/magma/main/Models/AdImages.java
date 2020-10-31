package com.magma.main.Models;

import org.bson.Document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "employees")
@XmlAccessorType (XmlAccessType.FIELD)
public class AdImages {

    public int adCode;
    public String imageIndex;
    public String imageURL;

    public AdImages(){}

    public AdImages(int adCode,String index,String url){
        this.adCode = adCode;
        this.imageIndex = index;
        this.imageURL = url;
    }


}
