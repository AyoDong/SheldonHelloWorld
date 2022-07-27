package com.sheldon.spi;

import java.util.ServiceLoader;

/**
 * @author fangxiaodong
 * @date 2020/10/09
 */
public class SpiMain {
    public static void main(String[] args) {
        ServiceLoader<UploadCDN> uploadCDN = ServiceLoader.load(UploadCDN.class);
        for (UploadCDN u : uploadCDN) {
            u.upload();
        }
    }
}
