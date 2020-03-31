package com.example.ebook.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author pxx
 * Date 2019/10/10 15:38
 * @Description
 */
@Component
public class UCloudProvider {
	
	@Value("${ucloud.ufile.public-key}")
	private String publicKey;
	@Value("${ucloud.ufile.private-key}")
	private String privateKey;
	@Value("${ucloud.ufile.bucket-name}")
	private String bucketName;
	@Value("${ucloud.ufile.region}")
	private String region;
	@Value("${ucloud.ufile.proxy-suffix}")
	private String proxySuffix;
	@Value("${ucloud.ufile.expires-duration}")
	private Integer expiresDuration;
	public String upload(InputStream fileStream, String mimeType, String fileName) {
		String generateFileName;
		String[] filePaths = fileName.split("\\.");
		if (filePaths.length > 1) {
			generateFileName = UUID.randomUUID().toString() +"."+ filePaths[filePaths.length - 1];
		}else {
			throw new MyException(ResultCode.FILE_UPLOAD_ERROR);
		}
		try {
			
			ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
			ObjectConfig config = new ObjectConfig(region, proxySuffix);
			PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
												   .putObject(fileStream, mimeType)
												   .nameAs(generateFileName)
												   .toBucket(bucketName)
												   /**
													* 是否上传校验MD5, Default = true
													*/
												   //  .withVerifyMd5(false)
												   /**
													* 指定progress callback的间隔, Default = 每秒回调
													*/
												   //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
												   /**
													* 配置进度监听
													*/
												   .setOnProgressListener(new OnProgressListener() {
													   @Override
													   public void onProgress(long bytesWritten, long contentLength) {
							
													   }
												   })
												   .execute();
			if (response != null && response.getRetCode() == 0) {
				return UfileClient.object(objectAuthorization,config)
						.getDownloadUrlFromPrivateBucket(generateFileName,bucketName, expiresDuration)
						.createUrl();
			}else {
				throw new MyException(ResultCode.FILE_UPLOAD_ERROR);
			}
		} catch (UfileClientException | UfileServerException e) {
			e.printStackTrace();
			throw new MyException(ResultCode.FILE_UPLOAD_ERROR);
		}
	}
}
