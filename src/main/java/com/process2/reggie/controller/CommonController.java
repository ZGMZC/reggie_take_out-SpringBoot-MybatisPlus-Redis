package com.process2.reggie.controller;

import com.process2.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.UUID;

/**
 * 文件上传和下载
 */
/*@RestController 是 Spring4 后新加的注解，从 RestController 类源码可以看出
@RestController 是 @Controller 和 @ResponseBody 两个注解的结合体。
* */
@RestController
/* 映射URL
* 类定义处：规定初步的请求映射，相对于web应用的根目录；
* 方法定义处：进一步细分请求映射，相对于类定义处的URL。如果类定义处没有使用该注解，则方法标记的URL相对于根目录而言；
*/
@RequestMapping("common")
public class CommonController {
    /*通过@Value将外部的值动态注入到Bean中，使用的情况有：
        注入普通字符串
        注入操作系统属性
        注入表达式结果
        注入其他Bean属性：注入beanInject对象的属性another
        注入文件资源
        注入URL资源*/
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("upload")
    public R<String> upload(MultipartFile file) throws IOException {
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件的后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名
        String fileName= UUID.randomUUID().toString()+suffix;
        //保存图片
        file.transferTo(new File(basePath+fileName));
        return R.success(fileName);
    }

    /**
     * 文件下载
     * @param name
     * @param response
     * @throws IOException
     */
    @GetMapping("download")
    public void download(String name, HttpServletResponse response) throws IOException {
        //输入流 将文件写入内存
        FileInputStream fileInputStream=new FileInputStream(new File(basePath+name));

        //输出流 将文件从内存写到网页上
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("image/jpeg");
        int len=0;
        byte[] bytes=new byte[1024];
        while((len=fileInputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
            outputStream.flush();
        }
        outputStream.close();
        fileInputStream.close();
    }

}
