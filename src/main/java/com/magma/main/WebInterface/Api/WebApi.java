package com.magma.main.WebInterface.Api;

import com.magma.main.DataAccess.Api.AndroidDevDA;
import com.magma.main.Models.MultipleObjectsResponse;
import com.magma.main.Utils.AppConstants;
import com.magma.main.Utils.GeneralFunctions;
import com.magma.main.WebInterface.ContentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.magma.main.DataAccess.Api.AndroidDevDA.*;
import static com.magma.main.Utils.GeneralFunctions.returnResponse;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

import org.json.JSONObject;

@Path("/Ads")
public class WebApi extends ContentService {


    @GET
    @Path("/info")
    public MultipleObjectsResponse getAdsInfo() {
        try {
            return AndroidDevDA.getAds_Images_info(1);
        } catch (Exception e) {
            e.printStackTrace();
            return returnResponse(AppConstants.SERVER_ERROR_CODE, 1);
        }
    }







    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public MultipleObjectsResponse login(@HeaderParam("language") String language,String data) {
        try {
            JSONObject jsonObj = new JSONObject(data);
            String username = GeneralFunctions.getStringJSONParameter(jsonObj,"username");
            String password = GeneralFunctions.getStringJSONParameter(jsonObj,"password");

            return AndroidDevDA.login(username, password,
                    GeneralFunctions.GetLanguage(language)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return returnResponse(AppConstants.SERVER_ERROR_CODE, GeneralFunctions.GetLanguage(language));
        }
    }



    @POST
    @Path("/setImageStatus")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public MultipleObjectsResponse set_Image_Status(@HeaderParam("language") String language,String data) {
        try {
            JSONObject jsonObj = new JSONObject(data);
            String token = GeneralFunctions.getStringJSONParameter(jsonObj,"token");
            String image = GeneralFunctions.getStringJSONParameter(jsonObj,"image");
            String status = GeneralFunctions.getStringJSONParameter(jsonObj,"status");

            return setImageStatus(token,image,status,GeneralFunctions.GetLanguage(language));
        } catch (Exception e) {
            e.printStackTrace();
            return returnResponse(AppConstants.SERVER_ERROR_CODE, GeneralFunctions.GetLanguage(language));
        }
    }



    @DELETE
    @Path("/images/del")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public MultipleObjectsResponse delete_Ad_Image(@HeaderParam("language") String language,
                                                   String data) {
        try {
            JSONObject jsonObj = new JSONObject(data);
            String token = GeneralFunctions.getStringJSONParameter(jsonObj,"token");
            String image = GeneralFunctions.getStringJSONParameter(jsonObj,"image");

            return deleteImage(token,image,GeneralFunctions.GetLanguage(language));
        } catch (Exception e) {
            return returnResponse(AppConstants.SERVER_ERROR_CODE, GeneralFunctions.GetLanguage(language));
        }

    }

    @DELETE
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public MultipleObjectsResponse delete_Ad(@HeaderParam("language") String language,
                                            String data) {
        try {
            JSONObject jsonObj = new JSONObject(data);
            String token = GeneralFunctions.getStringJSONParameter(jsonObj,"token");
            String adCode = GeneralFunctions.getStringJSONParameter(jsonObj,"adCode");

            return deleteAd(token,adCode,GeneralFunctions.GetLanguage(language));
        } catch (Exception e) {
            return returnResponse(AppConstants.SERVER_ERROR_CODE, GeneralFunctions.GetLanguage(language));
        }

    }








}
