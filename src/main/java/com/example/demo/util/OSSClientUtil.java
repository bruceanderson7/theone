package com.example.demo.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/28 2:01 下午
 **/
@RestController
@RequestMapping("/oss")
public class OSSClientUtil {
    //阿里云OSS地址，这里看根据你的oss选择
    protected static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    //阿里云OSS账号
    protected static String accessKeyId  = "xxxxxxxxxxxx";
    //阿里云OSS密钥
    protected static String accessKeySecret  = "xxxxxxxxxxxx";
    //阿里云OSS上的存储块bucket名字
    protected static String bucketName  = "hx-xhd";
    //阿里云图片文件存储目录
    private String homeimagedir = "/Users/shenanda/Pictures/ossPhoto";

    private OSSClient ossClient;

    public void OSSClientUtil() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
    /**
     * 销毁
     */
    public void destroy() {
        ossClient.shutdown();
    }
    /**
     * 图片 上传阿里云oss
     * @param file
     * @return
     */
    public String uploadHomeImageOSS(MultipartFile file) throws Exception {
        if (file.getSize() > 1024 * 1024 * 3) {
            throw new Exception("上传图片大小不能超过3M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadHomeImageFileOSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }
    }
    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getHomeImageUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.homeimagedir + split[split.length - 1]);
        }
        return null;
    }
    /**
     * 图片上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadHomeImageFileOSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, homeimagedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    /**
     * 判断OSS服务文件上传时文件的类型contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }
    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    /**
     * 判断图片
     * @param file
     * @return
     * @throws Exception
     */
    public String updateHomeImage(MultipartFile file) throws Exception {
        OSSClientUtil ossClient=new OSSClientUtil();
        if (file == null || file.getSize() <= 0) {
            throw new Exception("图片不能为空");
        }
        String name = ossClient.uploadHomeImageOSS(file);
        String imgUrl = ossClient.getHomeImageUrl(name);
        return imgUrl;
    }

    //处理文件上传
    @RequestMapping(value="/homeImageUpload", method = RequestMethod.POST)
    public Map<String, Object> homeImageUpload(MultipartFile file) {
        Map<String, Object> value = new HashMap<String, Object>();
        value.put("success", true);
        value.put("errorCode", 0);
        value.put("errorMsg", "");
        try {
            String head = updateHomeImage(file);//此处是调用上传服务接口
            value.put("data", head);
        } catch (IOException e) {
            e.printStackTrace();
            value.put("success", false);
            value.put("errorCode", 200);
            value.put("errorMsg", "图片上传失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
