package com.magma.main.Models;

import java.util.HashMap;

public class AdsInfo {
    public int TotalImages;
    public long RemainingImages;

    public HashMap<String,Object> AdsInfo;
    public HashMap<String,Object> ImagesInfo;

    public AdsInfo(int totalImages,long remainingImages,HashMap<String,Object> adsInfo,HashMap<String,Object> imagesInfo){
        this.TotalImages = totalImages;
        this.AdsInfo = adsInfo;
        this.RemainingImages = remainingImages;
        this.ImagesInfo = imagesInfo;
    }

}
