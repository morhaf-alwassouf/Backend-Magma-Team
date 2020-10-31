package com.magma.main.Models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "images")
@XmlAccessorType(XmlAccessType.FIELD)
public class Images {

    @XmlElement(name = "image")
    private List<AdImages> images = null;

    public Images(){}

    public List<AdImages> getImages() {
        return images;
    }

    public void setImages(List<AdImages> images) {
        this.images = images;
    }
}
