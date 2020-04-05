package utils;

import java.io.File;
import java.net.URL;
import java.util.Date;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;

/**
 * @author passerbyYSQ
 * @create 2020年3月22日 下午4:48:30
 */
public class UploadHelper {
    // 与存储区域有关系
    public static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
    // 上传的仓库名
    private static final String BUCKET_NAME = "salary-management";
    
    private static final String ACCESS_KEY_ID = "LTAI4FnM6LpGsjE2qJ8sEqkA";
    private static final String ACCESS_KEY_SECRET = "LjVhzlltTdYXbeOfrFUtapmhzWhYut";
    

    private static OSS getClient() {
        return new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 上传的最终方法，成功则返回一个路径
     * @param objKey    上传上去后，在服务器上的独立的key
     * @param path      需要上传的文件的路径
     * @return          存储的地址
     */
    private static URL upload(String objKey, String path) {
        // 构造上传请求。
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, objKey, new File(path));
        try {
            // 初始化上传的Client
            OSS client = getClient();
            // 开始同步上传
            client.putObject(request);
            // 得到一个外网可访问的地址
            // String url = client.presignPublicObjectURL(BUCKET_NAME, objKey);
         // 设置URL过期时间为1小时。
            Date expiration = new Date(new Date().getTime() + 3600 * 1000);
            URL url = client.generatePresignedUrl(BUCKET_NAME, objKey, expiration);
            
            // 格式打印输出的到日志
            //Log.e(TAG, String.format("PublicObjectURL:%s", url));
            return url;

        } catch (Exception e) {
        	e.printStackTrace();
            // 如果有异常则返回null
            return null;
        }
    }

    /**
            * 上传普通图片
     * @param path  本地地址
     * @return      服务器中的地址
     */
    public static URL uploadPhoto(String path, String userId) {
    	// 图片的后缀名统一改为jpg
        String key = String.format("img/photo/%s.jpg", userId);
        return upload(key, path);
    }
    

    
}
