package com.magma.main.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import com.magma.main.Models.MultipleObjectsResponse;

import javax.net.ssl.*;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

/**
 * @author MhdHawa
 */
public class GeneralFunctions {


    public static int GetLanguage(String language) {
        int x = 0;
        if ("ar".equals(language.toLowerCase()))
            return 1;
        return 0;

    }

    public static String GetLanguageString(String language) {
        if ("1".equals(language))
            return "ar";
        else if ("ar".equals(language.toLowerCase()))
            return "ar";
        else
            return "en";

    }

    public static MultipleObjectsResponse returnResponse(int code, Object language, Object... params) {

        int paramsCount = params.length;
        assert paramsCount <= 2;
        assert paramsCount < 1 || (params[0] instanceof String || params[0] != null);
        assert paramsCount < 2 || params[1] instanceof Integer;

        Object data = paramsCount > 0 && params[0] != null ? params[0] : "";
        int totalCount = paramsCount > 1 && params[1] != null ? ((Integer) params[1]).intValue() : 0;

        int lang = Integer.valueOf(language.toString());

        if (code > 0) {
            if (lang == 1) {
                return new MultipleObjectsResponse(AppConstants.OPERATION_SUCCESSFUL_CODE, AppConstants.SUCESS_MSG_AR, data, totalCount);
            } else {
                return new MultipleObjectsResponse(AppConstants.OPERATION_SUCCESSFUL_CODE, AppConstants.SUCESS_MSG_EN, data, totalCount);
            }
        } else {
            String msg = data.toString();
            if (code == -99) {
                if (lang == 1) {
                    return new MultipleObjectsResponse(AppConstants.SERVER_ERROR_CODE, msg != "" ? msg : AppConstants.SERVER_ERROR_MESSAGE_AR, new ArrayList(), 0);
                } else {
                    return new MultipleObjectsResponse(AppConstants.SERVER_ERROR_CODE, msg != "" ? msg : AppConstants.SERVER_ERROR_MESSAGE_EN, new ArrayList(), 0);
                }
            } else if (lang == 1) {
                return new MultipleObjectsResponse(code, msg != "" ? msg : AppConstants.SERVER_ERROR_MESSAGE_AR, new ArrayList(), 0);
            } else {
                return new MultipleObjectsResponse(code, msg != "" ? msg : AppConstants.SERVER_ERROR_MESSAGE_EN, new ArrayList(), 0);
            }

        }

    }


    public static MultipleObjectsResponse returnExceptionResponse(int code, String message,Object object ) {
        return new MultipleObjectsResponse(code, message, object,0);
    }


    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean stringIsNullOrEmpty(String s)
    {
        if (s == null)
            return true;
        if (s.length() <= 0)
            return true;
        return false;
    }

    public  String getProjectPath() throws UnsupportedEncodingException
    {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/WEB-INF/classes/");
        fullPath = pathArr[0];
        return fullPath;
    }


    public String getProjectFilesPath() throws UnsupportedEncodingException
    {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/WEB-INF/classes/");
        fullPath = pathArr[0];
        return fullPath;
    }




    public static String getStringJSONParameter(JSONObject jsonObj, String parameterName)
    {
        if(jsonObj.has(parameterName)){
            return jsonObj.getString(parameterName);
        }else{
            return "";
        }
    }

    public static String getIntJSONParameter(JSONObject jsonObj, String parameterName)
    {
        if(jsonObj.has(parameterName)){
            return jsonObj.getInt(parameterName) + "";
        }else{
            return "";
        }
    }



    public static String sendSMSVerificationCode(String GSM)
    {
        return "1";
    }

    public static String verifyGSMCode(String GSM)
    {
        return "1";
    }


    public  static String Send_Verification_Service_API_SendSMS(String gsm) throws MalformedURLException, IOException
    {

        String send_sms_url = AppConstants.SMSApiLink+"SendVerification";
        String applicationId = AppConstants.SMSApplicationID;

        String gsm_ = gsm.replace("-","");

        if(gsm_.substring(0,1).equals("0")){
            gsm_ = gsm_.substring(1);
        }

        if(gsm_.length() != 12){
            gsm_ = "963"+gsm_;
        }

        String json_data = "{\"sender_id\":\""+applicationId+"\",\"gsm\":\""+gsm_+"\"}";

        URL url = new URL(null,send_sms_url, new sun.net.www.protocol.http.Handler());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-length", String.valueOf(json_data.length()));
        con.setRequestProperty("Content-Type","application/json");

        con.setDoOutput(true);
        con.setDoInput(true);


        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"UTF-8"));
        output.write(json_data);
        output.flush();
        output.close();
        DataInputStream input = new DataInputStream( con.getInputStream() );
        StringBuilder sb = new StringBuilder();
        for( int c = input.read(); c != -1; c = input.read() )
            sb.append((char)c);

        String response = sb.toString();
        input.close();

        if(con.getResponseCode() != 200){
            System.out.println("Resp Code:"+con.getResponseCode());
            System.out.println("Resp Message:"+ con .getResponseMessage());
        }
        return response;
    }


    public  static String Send_Verification_Service_API_VerifyCode(String gsm,String code) throws MalformedURLException, IOException
    {
        String send_sms_url = AppConstants.SMSApiLink+"CheckVerification";
        String applicationId = AppConstants.SMSApplicationID;

        String gsm_ = gsm.replace("-","");

        if(gsm_.substring(0,1).equals("0")){
            gsm_ = gsm_.substring(1);
        }

        if(gsm_.length() != 12){
            gsm_ = "963"+gsm_;
        }

        String json_data = "{\"sender_id\":\""+applicationId+"\",\"gsm\":\""+gsm_+"\",\"code\":\""+code+"\"}";

        URL url = new URL(null,send_sms_url, new sun.net.www.protocol.http.Handler());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-length", String.valueOf(json_data.length()));
        con.setRequestProperty("Content-Type","application/json");

        con.setDoOutput(true);
        con.setDoInput(true);


        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"UTF-8"));
        output.write(json_data);
        output.flush();
        output.close();
        DataInputStream input = new DataInputStream( con.getInputStream() );
        StringBuilder sb = new StringBuilder();
        for( int c = input.read(); c != -1; c = input.read() )
            sb.append((char)c);

        String response = sb.toString();
        input.close();

        if(con.getResponseCode() != 200){
            System.out.println("Resp Code:"+con.getResponseCode());
            System.out.println("Resp Message:"+ con .getResponseMessage());
        }
        return response;

    }


    public  static String Send_Verification_Service_API_SendBroadcastSMS(String gsm_list,String message) throws MalformedURLException, IOException
    {

        String send_sms_url = AppConstants.SMSApiLink+"SendBroadcast";
        String applicationId = AppConstants.SMSApplicationID;


        String gsm_list_json = "";
        String[] arr_gsm_list = gsm_list.split(",");

        for (String gsm : arr_gsm_list){
            String gsm_ = gsm.replace("-","");

            if(gsm_.substring(0,1).equals("0")){
                gsm_ = gsm_.substring(1);
            }

            if(gsm_.length() != 12){
                gsm_ = "963"+gsm_;
            }

            gsm_list_json = gsm_list_json+"\""+gsm_+"\",";
        }


        gsm_list_json = "["+gsm_list_json+"]";


        String json_data = "{\"sender_id\":\""+applicationId+"\",\"gsm_list\":"+gsm_list_json+",\"message\":\""+message+"\"}";

        URL url = new URL(null,send_sms_url, new sun.net.www.protocol.http.Handler());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-length", String.valueOf(json_data.length()));
        con.setRequestProperty("Content-Type","application/json");

        con.setDoOutput(true);
        con.setDoInput(true);


        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8));
        output.write(json_data);
        output.flush();
        output.close();
        DataInputStream input = new DataInputStream( con.getInputStream());
        StringBuilder sb = new StringBuilder();
        for( int c = input.read(); c != -1; c = input.read() )
            sb.append((char)c);

        String response = sb.toString();
        input.close();

        if(con.getResponseCode() != 200){
            System.out.println("Resp Code:"+con.getResponseCode());
            System.out.println("Resp Message:"+ con .getResponseMessage());
        }
        return response;

    }


    public  static String AuthViaSyriaStore(String syria_store_authSessionId) throws MalformedURLException, IOException, KeyManagementException, NoSuchAlgorithmException {

        String json_data = "{\"authSessionId\":\""+syria_store_authSessionId+"\",\"secretKey\":\""+AppConstants.SyriaStoreAppSecretKey+"\"}";


        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        URL url = new URL(null,AppConstants.SyriaStoreApiLink, new sun.net.www.protocol.https.Handler());
        Proxy webProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(AppConstants.DevProxy_Host, AppConstants.DevProxy_Port));
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection(webProxy);
        //HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Content-length", String.valueOf(json_data.length()));
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Authorization","Basic MDk4OGQ1ZDItZTg2NS00ODA4LWFiOWYtMDY2NmY4ZmE5MTRjOnN5cmlhc3RvcmVzZGs=");
        con.setRequestProperty("language","en");
        con.setRequestProperty("versionCode","2");
        con.setRequestProperty("platformType","1");

        con.setDoOutput(true);
        con.setDoInput(true);

        con.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"UTF-8"));
        output.write(json_data);
        output.flush();
        output.close();
        DataInputStream input = new DataInputStream( con.getInputStream() );
        StringBuilder sb = new StringBuilder();
        for( int c = input.read(); c != -1; c = input.read() )
            sb.append((char)c);

        String response = sb.toString();
        System.out.println(response);
        input.close();

        if(con.getResponseCode() != 200){
            System.out.println("Resp Code:"+con.getResponseCode());
            System.out.println("Resp Message:"+ con .getResponseMessage());
        }


        return response;

    }



    public  static boolean check_is_GSM(String gsm)
    {
        boolean response = false;
        if(gsm.length() == 10 && gsm.substring(0, 2).equals("09") && gsm.matches("-?\\d+(\\.\\d+)?")){
            response = true;
        }else if(gsm.length() == 9 && gsm.substring(0, 1).equals("9") && gsm.matches("-?\\d+(\\.\\d+)?")){
            response = true;
        }

        return response;
    }

    public  static boolean check_verification_code_validity(String code)
    {
        boolean response =false;

        if(code.length() == 6 && code.matches("-?\\d+(\\.\\d+)?")){
            response =true;
        }

        return response;
    }


    public static String getPhotoGalleryStringFromJSONObj(JSONObject jsonObj, String parameterName)
    {
        String images = "";
        if(jsonObj.has(parameterName)){
            try {
                JSONArray photo_gallery = jsonObj.getJSONArray(parameterName);

                for (int i = 0; i < photo_gallery.length(); i++) {
                    images = images + "|" + photo_gallery.get(i);
                }

                return images;
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println(jsonObj.getJSONArray(parameterName));
                e.printStackTrace();
                return "";
            }
        }else{
            return "";
        }
    }


    public static String getSpecialPropertiesStringFromJSONObj(JSONObject jsonObj, String parameterName)
    {
        String specialProperties = "";
        if(jsonObj.has(parameterName)){
            try {
                JSONArray special_properties = jsonObj.getJSONArray(parameterName);

                for (int i = 0; i < special_properties.length(); i++) {
                    JSONObject special_property = (JSONObject) special_properties.get(i);

                    int special_property_id = 0;
                    String special_property_value = "000";

                    if(special_property.has("special_property_id") && special_property.has("special_property_value")){
                        special_property_id = special_property.getInt("special_property_id");
                        special_property_value = special_property.getString("special_property_value");

                        if(special_property_id != 0 && special_property_value != "000"){
                            special_property_value = special_property_value.replace(":","&&&&");
                            specialProperties = specialProperties +"|"+special_property_id + ":" + special_property_value;
                        }
                    }
                }

                return specialProperties;
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println(jsonObj.getJSONArray(parameterName));
                e.printStackTrace();
                return "";
            }
        }else{
            return "";
        }
    }

}
