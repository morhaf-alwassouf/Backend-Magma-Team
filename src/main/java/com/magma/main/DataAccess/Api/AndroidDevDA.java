package com.magma.main.DataAccess.Api;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.magma.main.Exceptions.BusinessException;
import com.magma.main.Exceptions.ExceptionMessages;
import com.magma.main.Models.AdsInfo;
import com.magma.main.Models.MultipleObjectsResponse;
import com.magma.main.Models.UserModel;
import com.magma.main.Utils.AppConstants;
import com.magma.main.Utils.GeneralFunctions;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.magma.main.DataAccess.DbConnection.getDBConnection;
import static com.magma.main.DataAccess.DbConnection.getDBConnectionClose;
import static com.magma.main.Models.GCS.*;
import static com.magma.main.Utils.GeneralFunctions.returnExceptionResponse;
import static com.magma.main.Utils.GeneralFunctions.returnResponse;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.set;

public class AndroidDevDA {



    public static MultipleObjectsResponse login(String username,String password,int language) {

        MongoDatabase db = getDBConnection();
        try{
            if (GeneralFunctions.stringIsNullOrEmpty(username)) throw new BusinessException(ExceptionMessages.keyEmptyUsername, language + "");
            if (GeneralFunctions.stringIsNullOrEmpty(password)) throw new BusinessException(ExceptionMessages.keyEmptyPassword, language + "");


            if(!username.equals(AppConstants._USERNAME) || !password.equals(AppConstants._PASSWORD)){
                throw new BusinessException(ExceptionMessages.keyInvalidCredentials, language + "");
            }

            UserModel user = new UserModel(username,AppConstants._TOKEN);

            ArrayList data = new ArrayList();
            data.add(user);


            return returnResponse(1, language,data,1);

        }catch (BusinessException e){
            return returnExceptionResponse(Integer.parseInt(e.code), e.getMessage(),new ArrayList());
        }finally {
            getDBConnectionClose();
        }
    }


    private static boolean checkAuth(String token){
        if(token.equals(AppConstants._TOKEN)){
            return true;
        }
        return false;
    }


    public static MultipleObjectsResponse getAds_Images_info(int language) {

        MongoDatabase db = getDBConnection();
        try{
            long total_ads_count = db.getCollection("bh_ads").countDocuments();

            MongoCollection<Document> bh_ads_image_status = getDBConnection().getCollection("bh_ads_image_status");

            long total_images_status = bh_ads_image_status.countDocuments();

            HashMap<String,Object> adsInfo = new HashMap<String,Object>();
            adsInfo.put("TotalAds",total_ads_count);
            adsInfo.put("TotalImagesStatus",total_images_status);

            int total_rejected_images = bh_ads_image_status.find(eq("imageStatus", false)).into(new ArrayList<Document>()).size();
            int total_accepted_images = bh_ads_image_status.find(eq("imageStatus", true)).into(new ArrayList<Document>()).size();

            HashMap<String,Object> imagesInfo = new HashMap<String,Object>();
            imagesInfo.put("TotalAnnotatedImages",total_images_status);
            imagesInfo.put("TotalAcceptedImages",total_accepted_images);
            imagesInfo.put("TotalRejectedImages",total_rejected_images);


            init();
            findTotalImages();
            setAnnotatedImages(total_images_status);





            return returnResponse(1, language,new AdsInfo(getTotalImages(),getNotSeenImages(),adsInfo,imagesInfo),1);

        }finally {
            getDBConnectionClose();
        }
    }



    public static MultipleObjectsResponse setImageStatus(String token,String image,String status,int language) {

        try{
            if (GeneralFunctions.stringIsNullOrEmpty(token)) throw new BusinessException(ExceptionMessages.keyEmptyToken, language + "");
            if (GeneralFunctions.stringIsNullOrEmpty(image)) throw new BusinessException(ExceptionMessages.keyEmptyImageName, language + "");
            if (GeneralFunctions.stringIsNullOrEmpty(status)) throw new BusinessException(ExceptionMessages.keyEmptyImageStatus, language + "");

            Boolean image_status;
            if(status.equals("accepted")){
                image_status = true;
            }else if(status.equals("rejected")){
                image_status = false;
            }else{
                throw new BusinessException(ExceptionMessages.keyInvalidImageStatus, language + "");
            }

            int adCode,imageIndex;

            try {
                adCode = Integer.parseInt(image.substring(0,image.indexOf("-")));
                imageIndex = Integer.parseInt(image.substring(image.indexOf("-")+1,image.indexOf(".")));
            }catch (Exception e){
                throw new BusinessException(ExceptionMessages.keyInvalidImageName, language + "");
            }

            if (!checkAuth(token)) throw new BusinessException(ExceptionMessages.keyInvalidToken, language + "");

            _setImageStatus(image,adCode,imageIndex,image_status);

            return returnResponse(1, language,new ArrayList(),1);

        }catch (BusinessException e){
            return returnExceptionResponse(Integer.parseInt(e.code), e.getMessage(),new ArrayList());
        }
    }



    public static MultipleObjectsResponse deleteImage(String token,String image,int language) {

        try{
            if (GeneralFunctions.stringIsNullOrEmpty(token)) throw new BusinessException(ExceptionMessages.keyEmptyToken, language + "");
            if (GeneralFunctions.stringIsNullOrEmpty(image)) throw new BusinessException(ExceptionMessages.keyEmptyImageName, language + "");
            if (!checkAuth(token)) throw new BusinessException(ExceptionMessages.keyInvalidToken, language + "");

            deleteImageFromGCS(image);

            return returnResponse(1, language,new ArrayList(),1);

        }catch (BusinessException e){
            return returnExceptionResponse(Integer.parseInt(e.code), e.getMessage(),new ArrayList());
        }
    }


    public static MultipleObjectsResponse deleteAd(String token,String adCode,int language) {

        MongoDatabase db = getDBConnection();
        try{
            if (GeneralFunctions.stringIsNullOrEmpty(token)) throw new BusinessException(ExceptionMessages.keyEmptyToken, language + "");
            if (GeneralFunctions.stringIsNullOrEmpty(adCode)) throw new BusinessException(ExceptionMessages.keyEmptyAdCode, language + "");
            if (!checkAuth(token)) throw new BusinessException(ExceptionMessages.keyInvalidToken, language + "");



            MongoCollection<Document> bh_ads = db.getCollection("bh_ads");
            DeleteResult result = bh_ads.deleteOne(eq("adCode", adCode));

            if(result.getDeletedCount() != 1){
                throw new BusinessException(ExceptionMessages.keyAdCodeNotExist, language + "");
            }

            deleteAdImages(adCode);

            return returnResponse(1, language,new ArrayList(),1);

        }catch (BusinessException e){
            return returnExceptionResponse(Integer.parseInt(e.code), e.getMessage(),new ArrayList());
        }finally {
            getDBConnectionClose();
        }
    }


    public static ArrayList getRejectedImages(int language) {

        try{

            MongoCollection<Document> bh_ads_image_status = getDBConnection().getCollection("bh_ads_image_status");

            ArrayList<Document> image_list = bh_ads_image_status.find(eq("imageStatus", false)).projection(fields(excludeId(),
                    include("adCode",
                            "imageIndex",
                            "imageURL"))).into(new ArrayList<Document>());
            return image_list;

        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }






    private static void deleteAdImages(String adCode){
        Runnable task = new Runnable() {
            public void run() {
                try {
                    MongoCollection<Document> bh_ads_image_status = getDBConnection().getCollection("bh_ads_image_status");
                    bh_ads_image_status.deleteOne(eq("adCode", adCode));

                    init();
                    Storage storage = getStorage();
                    Bucket bucket = storage.get(getBucketName());

                    Page<Blob> blobs =
                            bucket.list(
                                    Storage.BlobListOption.prefix(adCode),
                                    Storage.BlobListOption.currentDirectory());

                    for (Blob blob : blobs.iterateAll()) {
                        storage.delete(getBucketName(), blob.getName());
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }


    private static void deleteImageFromGCS(String image){
        Runnable task = new Runnable() {
            public void run() {
                try {
                    init();
                    Storage storage = getStorage();
                    storage.delete(getBucketName(), image);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }


    private static void _setImageStatus(String image,int adCode,int imageIndex,Boolean image_status){
        Runnable task = new Runnable() {
            public void run() {
                try {

                    MongoCollection<Document> bh_ads_image_status = getDBConnection().getCollection("bh_ads_image_status");

                    Document image_status_obj = bh_ads_image_status.find(and(eq("adCode", adCode),eq("imgIndex", imageIndex))).first();

                    if(image_status_obj != null){
                        Boolean _img_status = image_status_obj.getBoolean("imageStatus");
                        if(_img_status != image_status){
                            Bson filter = and(eq("adCode", adCode),eq("imageIndex", imageIndex));
                            Bson updateOperation = set("imageStatus",image_status);
                            bh_ads_image_status.updateOne(filter, updateOperation);
                        }
                    }else{

                        Document new_image_status = new Document("_id", new ObjectId())
                                .append("adCode",adCode)
                                .append("imageIndex",imageIndex)
                                .append("imageURL","https://storage.googleapis.com/"+getBucketName()+"/"+image)
                                .append("imageStatus",image_status)
                                .append("annotatedBy","Morhaf.Alwassouf");
                        bh_ads_image_status.insertOne(new_image_status);


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    getDBConnectionClose();
                }
            }
        };

        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
}
