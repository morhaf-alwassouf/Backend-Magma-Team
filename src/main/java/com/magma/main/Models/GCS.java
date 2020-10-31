package com.magma.main.Models;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import com.magma.main.Utils.GeneralFunctions;

public class GCS {


    private static Storage _Storage;
    private static String _BucketName = "waseet-ads-images-bh";

    private static int _TotalImages;
    private static long _AnnotatedImages;
    private static int _NotSeenImages;


    public static void init() {

        try {
            InputStream auth = GCS.class.getClassLoader().getResourceAsStream("ak-works-272219-7169d3546482.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(auth)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            _Storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Storage getStorage(){
        return _Storage;
    }

    public static String getBucketName(){
        return _BucketName;
    }



    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }



    public static void show(String urlLocation) {
        Image image = null;
        try {
            URL url = new URL(urlLocation);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            conn.connect();
            InputStream urlStream = conn.getInputStream();
            image = ImageIO.read(urlStream);

            JFrame frame = new JFrame();
            JLabel lblimage = new JLabel(new ImageIcon(image));
            frame.getContentPane().add(lblimage, BorderLayout.CENTER);
            frame.setSize(image.getWidth(null) + 50, image.getHeight(null) + 50);
            frame.setVisible(true);

        } catch (IOException e) {
            System.out.println("Something went wrong, sorry:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void findTotalImages() {
        Bucket bucket = _Storage.get(_BucketName);
        Page<Blob> blobs = bucket.list();
        int total = 0;
        for (Blob blob : blobs.iterateAll()) {
            total++;
        }
        _TotalImages = total;
    }


    public static int getTotalImages() {
        return  _TotalImages;
    }

    public static long setAnnotatedImages(long annotatedImagesCount) {
        return  _AnnotatedImages = annotatedImagesCount;
    }

    public static long getAnnotatedImages() {
        return  _AnnotatedImages;
    }

    public static long getNotSeenImages() {
        return  _TotalImages - _AnnotatedImages;
    }




}
