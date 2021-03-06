package team.redrock.servlet;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;
import team.redrock.common.FileType;
import team.redrock.common.ServerResponse;
import team.redrock.exceptions.FileTypeNotSupportException;
import team.redrock.util.FileUtils;
import team.redrock.util.PropertiesUtil;
import team.redrock.util.UUIDUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UploadServlet", urlPatterns = {"/bbm/pic_upload.do"})
public class UploadServlet extends HttpServlet {
    private static int MEMORY_THRESHOLD = Integer.valueOf(PropertiesUtil.getProperty("MEMORY_THRESHOLD", "10240"));

    // 单位 bytes
    private static int MAX_FILE_SIZE = Integer.valueOf(PropertiesUtil.getProperty("MAX_FILE_SIZE", "10485760"));
    private static int MAX_REQUEST_SIZE = Integer.valueOf(PropertiesUtil.getProperty("MAX_REQUEST_SIZE", "11534336"));
    // 从 tomcat/bin 目录出发
    private static String UPLOAD_PATH = PropertiesUtil.getProperty("UPLOAD_PATH", "/root/uploadfile");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        PrintWriter writer = response.getWriter();
        if (!ServletFileUpload.isMultipartContent(request)) {
            ServerResponse res = ServerResponse.createByErrorMessage("Error 不是文件上传,表单必须包含 enctype='multipart/form-data'");
            writer.print(new ObjectMapper().writeValueAsString(res));
            writer.flush();
            writer.close();
        }
        //String uploadPath = request.getSession().getServletContext().getRealPath("../PHOTO_UPLOAD");
        String uploadPath = UPLOAD_PATH;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时文件夹的目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置单个文件大小的最大值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置上传文件总量的最大值，包括所有文件和表单的总和
        upload.setSizeMax(MAX_REQUEST_SIZE);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdir();

        try {
            // 解析request
            List<FileItem> formItems = upload.parseRequest(request);
            List<String[]> fileNameList = new ArrayList<>();
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    // 如果不是普通的formField则就是上传的文件
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        //类型检查
                        FileType fileType = FileUtils.getType(item.getInputStream());
                        //不符合类型的抛出异常
                        if (fileType == null) throw new FileTypeNotSupportException();

                        String uuid = UUIDUtil.create().toString();
                        String suffix = fileType.name().toLowerCase();
                        File storeFile = new File(uploadPath + "/" + uuid + "." + suffix);
                        File thumbFile = new File(uploadPath + "/" + uuid + "-small." + suffix);
                        item.write(storeFile);
                        FileInputStream inputStream = new FileInputStream(storeFile);
                        BufferedImage sourceImg = ImageIO.read(inputStream);
                        //图片压缩
                        FileChannel fc = inputStream.getChannel();
                        if (fc.size() > 1048576 && fc.size() <= 2621440) {
                            // 1M - 2.5M
                            Thumbnails.of(storeFile).scale(0.5f).outputQuality(0.5f).outputFormat(suffix).toFile(thumbFile);
                        } else if (fc.size() > 2621440 && fc.size() <= 5242880) {
                            // 2.5M - 5M
                            Thumbnails.of(storeFile).scale(0.5f).outputQuality(0.25f).outputFormat(suffix).toFile(thumbFile);
                        } else if (fc.size() > 5242880) {
                            // > 5M
                            Thumbnails.of(storeFile).scale(0.5f).outputQuality(0.1f).outputFormat(suffix).toFile(thumbFile);
                        } else {
                            // <= 1M
                            Thumbnails.of(storeFile).scale(0.5f).outputQuality(0.75f).outputFormat(suffix).toFile(thumbFile);
                        }
                        fc.close();
                        inputStream.close();
                        fileNameList.add(new String[]{storeFile.getName(), thumbFile.getName()});
                        item.delete();
                    }
                }
            }
            ServerResponse res = ServerResponse.createBySuccess("文件上传成功", fileNameList);
            writer.print(new ObjectMapper().writeValueAsString(res));
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            ServerResponse res = ServerResponse.createByErrorMessage("文件大小超过限制(10M)");
            writer.print(new ObjectMapper().writeValueAsString(res));
        } catch (FileUploadBase.SizeLimitExceededException e) {
            ServerResponse res = ServerResponse.createByErrorMessage("上传文件总大小超过限制(11M)");
            writer.print(new ObjectMapper().writeValueAsString(res));
        } catch (FileTypeNotSupportException e) {
            ServerResponse res = ServerResponse.createByErrorMessage("文件类型仅支持 JPEG(JPG),PNG,GIF,BMP,TIFF");
            writer.print(new ObjectMapper().writeValueAsString(res));
        } catch (FileUploadException e) {
            ServerResponse res = ServerResponse.createByErrorMessage("文件上传失败");
            writer.print(new ObjectMapper().writeValueAsString(res));
        } catch (Exception e) {
            ServerResponse res = ServerResponse.createByErrorMessage("网络问题或者服务器问题");
            writer.print(new ObjectMapper().writeValueAsString(res));
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        try (PrintWriter writer = response.getWriter()) {
            writer.print(new ObjectMapper().writeValueAsString(ServerResponse.createByErrorMessage("非法访问")));
        }
    }
}
