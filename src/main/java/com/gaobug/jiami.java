package com.gaobug;

import io.xjar.XCryptos;
import io.xjar.XKit;
import io.xjar.boot.XBoot;
import io.xjar.key.XKey;

import java.io.File;
import java.security.NoSuchAlgorithmException;

public class jiami {
    public static void main(String[] args) {
        File file=new File("jiami");{
            if (!file.exists())
                file.mkdirs();
        }
//        try {
//            XCryptos.encryption()
//                    .from("target/gaobug-1.0.jar") //指定待加密文件路径
//                    .use("ZK9beI2i9G2") //密码
//                    .to("jiami/gaobug-1.0.jar"); //加密后的文件存放的路径
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("success");
        System.out.println("加密开始.....");

        String password = "csidc.9089.aaa";
        XKey xKey = null;
        try {
            xKey = XKit.key(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //String path = "F:\\ideworks\\datac\\target/";   //jar包地址
        String path = "target/";   //jar包地址
//        XBoot.encrypt(path+"dataApi-1.0.0.jar", path+"0-dataApi-1.0.0.jar", xKey);

        System.out.println("加密执行.....");

        // 项目所有类的包名都以 com.company.project 开头，那只加密自身项目的字节码即可采用以下方式。
        try {
            XBoot.encrypt(
                    path+"gaobug-1.0.jar",
                    path+"datachanl.jar",               //新jar包
                    xKey,
                    (entry) -> {
                        String name = entry.getName();
                        String pkg = "com/datac/";
                        return name.startsWith(pkg);
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("加密结束.....");


    }
}
