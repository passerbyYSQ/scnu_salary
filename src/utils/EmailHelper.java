package utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @author passerbyYSQ
 * @create 2020-03-17 12:48
 */
public class EmailHelper {

    private static final String regionId = "cn-hangzhou";
    private static final String accessKeyId = "LTAI4FnM6LpGsjE2qJ8sEqkA";
    private static final String secret = "LjVhzlltTdYXbeOfrFUtapmhzWhYut";

    public static IAcsClient getClient() {
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        return new DefaultAcsClient(profile);
    }


    public static SingleSendMailResponse singleSend(EmailBean email) {
        try {
            IAcsClient client = getClient();
            SingleSendMailRequest request = email.buildRequest();
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            return httpResponse;
        } catch (ServerException e) {
            //捕获错误异常码
            System.out.println("ErrCode :"  + e.getErrCode());
            e.printStackTrace();
        }
        catch (ClientException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        singleSend(new EmailBean("idea", "1127664027@qq.com", "测试",
//                "<h1>测试。。。。</h1>"));
//    }

    public static class EmailBean {
        // 发信地址，一般不去更改
        private String accountName = "passerbyysq@email.aliyunysq.xyz";
        // 发信人的名字
        private String alias;
        // 收信人邮箱
        private String toAddress;
        // 邮件标题
        private String subject;
        // 邮件内容
        private String htmlBody;

        // 请求方式
        private MethodType method = MethodType.POST;
        //
        private boolean replyToAddress = false;
        // Tag
        private String tagName;

        // 必须信息的构造函数
        /**
         * @param alias		发信人的名字
         * @param toAddress	收信人邮箱	
         * @param subject	邮件标题
         * @param htmlBody	邮件内容。支持Html
         */
        public EmailBean(String alias, String toAddress, String subject, String htmlBody) {
            this.alias = alias;
            this.toAddress = toAddress;
            this.subject = subject;
            this.htmlBody = htmlBody;
        }

        public SingleSendMailRequest buildRequest() {
            SingleSendMailRequest request = new SingleSendMailRequest();
            request.setAccountName(accountName);
            request.setFromAlias(alias);
            request.setAddressType(1);
            request.setTagName(tagName);
            request.setReplyToAddress(replyToAddress);
            request.setToAddress(toAddress);
            request.setSubject(subject);
            //如果采用byte[].toString的方式的话请确保最终转换成utf-8的格式再放入htmlbody和textbody，
            // 若编码不一致则会被当成垃圾邮件。
            //注意：文本邮件的大小限制为3M，过大的文本会导致连接超时或413错误
            request.setHtmlBody(htmlBody);
            request.setMethod(method);
            //开启需要备案，0关闭，1开启
            //request.setClickTrace(“0”);
            return request;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getHtmlBody() {
            return htmlBody;
        }

        public void setHtmlBody(String htmlBody) {
            this.htmlBody = htmlBody;
        }

        public MethodType getMethod() {
            return method;
        }

        public void setMethod(MethodType method) {
            this.method = method;
        }

        public boolean isReplyToAddress() {
            return replyToAddress;
        }

        public void setReplyToAddress(boolean replyToAddress) {
            this.replyToAddress = replyToAddress;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
    }



}
