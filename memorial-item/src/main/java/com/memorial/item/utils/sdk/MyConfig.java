package com.memorial.item.utils.sdk;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MyConfig extends WXPayConfig {
    private byte[] certData;

    public MyConfig() throws Exception {
//        InputStream certStream = MyConfig.class.getClassLoader().getResourceAsStream("apiclient_cert.p12");
//        int n = 0;
//        this.certData = new byte[1024];
//        while ((n = certStream.read(this.certData)) != -1) ;
//        certStream.close();
//        String certPath = "D:\\Myeclipse\\ssm\\ssm_api\\src\\main\\resource\\apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
    }
    public String getAppID() {
        return "wxb35ad73dc44d2754";
    }

    public String getMchID() {
        return "1589763711";
    }

    public String getKey() {
        return "fLQI5EzrNF5oSol96uXDmelrTH8WV3xN";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        // 这个方法需要这样实现, 否则无法正常初始化WXPay
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {

            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;

    }
}
