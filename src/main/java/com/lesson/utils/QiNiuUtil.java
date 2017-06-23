package com.lesson.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;

public class QiNiuUtil {
	static String accessKey = "1yO6M0kKNO3NyoIbaQlf_rBt5TjhdXj4QpascjI_";
	static String secretKey = "rig7qqNi1ztZzsqjp52WfuPlvsx9wiuZctqU0f1u";
	static String bucket = "lesson";
	
	public static String getUploadToken() {
		
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		return upToken;
	}
	
	public static boolean deleteFile(String key) {
		//构造一个带指定Zone对象的配置类
		System.out.println(key);
		Configuration cfg = new Configuration(Zone.zone0());


		Auth auth = Auth.create(accessKey, secretKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
		    bucketManager.delete(bucket, key);
		} catch (QiniuException ex) {
		    //如果遇到异常，说明删除失败
		    System.err.println(ex.code());
		    System.err.println(ex.response.toString());
		    return false;
		}
		return true;
		
	}

}
