package com.fh.config.oss;

import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wkx
 * @create 2020-12-03 11:33
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping
    public CommonsReturn uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        String format = sdf.format(new Date());
        String url = OssUtil.upload(file,"images"+format);
        return CommonsReturn.success(url);
    }

}
