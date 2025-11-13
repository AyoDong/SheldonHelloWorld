package com.sheldon.cmsk;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

import java.nio.charset.StandardCharsets;

public class yhTest {

    private static final String tenantId = "7a73736b";

    private static final String ticketPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuoYQpVdtcOSfLX2N/cYiXTqdlSip6UpGnwsvaLx48IG6om8vrwboQkL+oe1v9imvgZhIY4EwVa4OSt59lbAcKs/rLzQ3kTmUe86qlEZUee8aT3qZ3HhxSC0MrHnqm8BrSllNRyHT85NwDBa1FmkUykHLJygb9a0rjXo/BpVzNeQIDAQAB";

    private static final String signPrivateKey = "3082014b0201003082012c06072a8648ce3804013082011f02818100fd7f53811d75122952df4a9c2eece4e7f611b7523cef4400c31e3f80b6512669455d402251fb593d8d58fabfc5f5ba30f6cb9b556cd7813b801d346ff26660b76b9950a5a49f9fe8047b1022c24fbba9d7feb7c61bf83b57e7c6a8a6150f04fb83f6d3c51ec3023554135a169132f675f3ae2b61d72aeff22203199dd14801c70215009760508f15230bccb292b982a2eb840bf0581cf502818100f7e1a085d69b3ddecbbcab5c36b857b97994afbbfa3aea82f9574c0b3d0782675159578ebad4594fe67107108180b449167123e84c281613b7cf09328cc8a6e13c167a8b547c8d28e0a3ae1e2bb3a675916ea37f0bfa213562f1fb627a01243bcca4f1bea8519089a883dfe15ae59f06928b665e807b552564014c3bfecf492a041602145bafe54eb3cb75575200c22f1320df0dc57a913e";

    private static final String accountId = "10084418";

    private static final String URL = "https://yhbi.cmft.com/bi/sso?proc=1&action=viewer&hback=true&db=!62db!!5546!!5c40!!86c7!!53e3!!5de5!!4e1a!!533a!!63a7!!80a1!!80a1!!4efd!!6709!!9650!!516c!!53f8!_CMSK!2f!!6295!!8d44!!6027!!623f!!5730!!4ea7!!8d44!!4ea7!!7ba1!!7406!!7cfb!!7edf!!2f!01_!6301!!6709!!8d44!!4ea7!!2f!01_!6301!!6709!!8d44!!4ea7!!603b!!89c8!.db&platform=PC&browserType=chrome";

    public static void main(String[] args) {
        yhTest yhTest = new yhTest();
        System.out.println(yhTest.getUrlToken());
    }

    /**
     * 获取永洪url签名
     *
     * @return
     */
    public String getUrlToken() {
        RSA rsaPublicKey = new RSA(null, ticketPublicKey);

        long timestamp = System.currentTimeMillis();//当前时间戳（用于校验是否过期，服务端校验提交的timestamp和系统时间，超过10s认为该token过期)
        String ticket = getTicket(rsaPublicKey, accountId); //票据
        String signed = getSign(ticket, timestamp); //签名

        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer
                .append(URL)
                .append("&ticket=").append(URLUtil.encodeAll(ticket))
                .append("&sign=").append(URLUtil.encodeAll(signed))
                .append("&timestamp=").append(timestamp)
                .append("&tenant_id=").append(tenantId)
                .append("&User_ID=").append(accountId)
                .append("&encrypt=false");
        return urlBuffer.toString();
    }

    /**
     * @param rsaPublicKey   公钥
     * @param accountId 当前用户给工号
     * @return 对用户ID进行加密（公钥加密)
     */
    private String getTicket(RSA rsaPublicKey, String accountId) {
        return rsaPublicKey.encryptBase64(accountId, KeyType.PublicKey);
    }

    /**
     * @param ticket    ticket
     * @param timestamp 当前时间戳
     * @return 对加密后信息+":"+时间戳  进行签名
     */
    private String getSign(String ticket, long timestamp) {
        String signContent = ticket + ":" + timestamp;
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withDSA, signPrivateKey, null);
        return HexUtil.encodeHexStr(sign.sign(signContent.getBytes(StandardCharsets.UTF_8)));
    }

}
