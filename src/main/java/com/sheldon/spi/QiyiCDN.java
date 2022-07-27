package com.sheldon.spi;

/**
 * @author fangxiaodong
 * @date 2020/10/09
 */
public class QiyiCDN implements UploadCDN {
    @Override
    public void upload() {
        System.out.println("upload to qiyi cdn");
    }
}
