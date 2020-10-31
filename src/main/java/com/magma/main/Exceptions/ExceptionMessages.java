package com.magma.main.Exceptions;

import java.util.HashMap;

public class ExceptionMessages {
    public static HashMap<String, String> ar = new HashMap<>();
    public static HashMap<String, String> en = new HashMap<>();
    public static HashMap<String, String> codes = new HashMap<>();





    public static final String keyEmptyUsername = "emptyUsername";

    public static final String keyEmptyPassword = "emptyPassword";

    public static final String keyInvalidCredentials = "invalidCredentials";

    public static final String keyEmptyToken = "emptyToken";

    public static final String keyInvalidToken = "invalidToken";

    public static final String keyEmptyAdCode = "emptyAdCode";

    public static final String keyAdCodeNotExist = "adCodeNotExist";

    public static final String keyEmptyImageName = "adCodeNotExist";


    public static final String keyEmptyImageStatus = "keyEmptyImageStatus";

    public static final String keyInvalidImageStatus = "keyInvalidImageStatus";

    public static final String keyInvalidImageName = "keyInvalidImageName";











    static {

        en.put(keyEmptyUsername, "Sorry, Username is required");
        ar.put(keyEmptyUsername, "عذرا, اسم المستخدم مطلوب");
        codes.put(keyEmptyUsername, "-30000");

        en.put(keyEmptyPassword, "Sorry, Password is required");
        ar.put(keyEmptyPassword, "عذرا, كلمة المرور مطلوبة");
        codes.put(keyEmptyPassword, "-30001");

        en.put(keyInvalidCredentials, "Sorry, Incorrect Username or Password please, check and try again.");
        ar.put(keyInvalidCredentials, "عذرا, خطأ في اسم المستخدم او كلمة المرور الرجاء التأكد والمحاولة مجدداً");
        codes.put(keyInvalidCredentials, "-30002");

        en.put(keyInvalidToken, "Sorry, Invalid access token or you dont have permission to preform this action");
        ar.put(keyInvalidToken, "عذرا, خطأ في رمز الوصول او لا تملك صلاحية للقيام بهذة العملية.");
        codes.put(keyInvalidToken, "-30003");


        en.put(keyEmptyToken, "Sorry, Access token is required");
        ar.put(keyEmptyToken, "عذرا, رمز الوصول مطلوب");
        codes.put(keyEmptyToken, "-30004");


        en.put(keyEmptyAdCode, "Sorry, Ad code is required");
        ar.put(keyEmptyAdCode, "عذرا, معرق الاعلان مطلوب");
        codes.put(keyEmptyAdCode, "-30005");


        en.put(keyAdCodeNotExist, "Sorry, Ad code is not exist");
        ar.put(keyAdCodeNotExist, "عذرا, معرق الاعلان غير موجود");
        codes.put(keyAdCodeNotExist, "-30006");


        en.put(keyEmptyImageName, "Sorry, Image name is required");
        ar.put(keyEmptyImageName, "عذرا,اسم الصورة مطلوبة");
        codes.put(keyEmptyImageName, "-30007");

        en.put(keyEmptyImageStatus, "Sorry, Image status is required");
        ar.put(keyEmptyImageStatus, "عذرا,حالة الصورة مطلوبة");
        codes.put(keyEmptyImageStatus, "-30008");


        en.put(keyInvalidImageStatus, "Sorry, Invalid image status");
        ar.put(keyInvalidImageStatus, "عذرا,خطأ في حالة الصورة");
        codes.put(keyInvalidImageStatus, "-30009");

        en.put(keyInvalidImageName, "Sorry, Invalid image name");
        ar.put(keyInvalidImageName, "عذرا,خطأ في اسم الصورة");
        codes.put(keyInvalidImageName, "-300010");




    }




}
