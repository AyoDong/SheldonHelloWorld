package com.sheldon.spi;

/**
 * @author fangxiaodong
 * @date 2020/10/09
 */
public class ChinaNetCDN implements UploadCDN {
    @Override
    public void upload() {
        System.out.println("upload to ChinaNet cdn");
    }
}
