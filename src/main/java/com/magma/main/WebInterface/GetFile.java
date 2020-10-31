package com.magma.main.WebInterface;

import com.google.gson.Gson;
import com.magma.main.Models.AdImages;
import com.magma.main.Models.Images;
import com.magma.main.Utils.EXPORT_EXCEL;
import com.magma.main.Utils.GeneralFunctions;
import org.bson.Document;

import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import static com.magma.main.DataAccess.Api.AndroidDevDA.getRejectedImages;

@WebServlet(name = "GetFile", urlPatterns = {"/export/rejected/images"})

public class GetFile extends HttpServlet{
    @Context
    private UriInfo context;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String type = request.getParameter("type");

        ArrayList<Document> data = getRejectedImages(1);

            if(type.equals("json")){
                Gson gson = new Gson();
                response.setContentType("application/json;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println(gson.toJson(data));
                }
            }else if(type.equals("xml")){
                String xmlString = "";
                try {

                    List<AdImages> images = new ArrayList<AdImages>();

                    for (Document doc : data) {
                        String url = "",index = "";

                        int adCode = doc.getInteger("adCode");

                        if(doc.get("imageURL") != null){
                            url = doc.getString("imageURL");
                        }

                        if(doc.get("imageIndex") != null){
                            index = doc.get("imageIndex").toString();
                        }

                        images.add(new AdImages(adCode,index,url));

                    }

                    JAXBContext jaxbContext = JAXBContext.newInstance(Images.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                    Images d = new Images();
                    d.setImages(images);

                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    StringWriter sw = new StringWriter();
                    jaxbMarshaller.marshal(d,sw);
                    xmlString = sw.toString();

                } catch (JAXBException e) {
                    e.printStackTrace();
                }

                response.setContentType("application/xml;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println(xmlString);
                }

        }else {
            try {
                String fileName = EXPORT_EXCEL.export_(data);
                File file = new File(fileName);
                if (file.exists()) {


                    response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
                    response.setContentType("APPLICATION/OCTET-STREAM");


                    response.setContentLength((int) file.length());
                    FileInputStream in = new FileInputStream(file);
                    OutputStream out = response.getOutputStream();
                    byte[] buf = new byte[1024];
                    int count = 0;

                    while ((count = in.read(buf)) >= 0) {
                        out.write(buf, 0, count);
                    }
                    in.close();
                    out.close();
                    file.delete();

                } else {
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet NewServlet</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>The file not exist on this server!!</h1>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        }
    }
