package com.kutear.app.upload;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.callback.IUploadCallBack;
import com.kutear.app.utils.L;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by kutear on 15-10-7.
 * 上传文件到七牛
 */
public class QiniuUpload {
    private static final String AccessKey = "9kBoBm9bWfIRTm_sdBn8IlUd3_CYiICeETnflPaJ";
    private static final String SecretKey = "8YIxcuQXJTa5dwnfl-M79vFpgusxlhgByRhT5Twr";
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private static final String QINIU_HOST = "http://kutear.qiniudn.com/";
    private static final String SCOPE = "kutear";//容器名称

    private static final String TAG = QiniuUpload.class.getSimpleName();

    public static void upload(final File file, final IUploadCallBack callBack) {
        UploadManager uploadManager = AppApplication.getUploadManager();
        try {
            // 1 构造上传策略
            JSONObject _json = new JSONObject();
            long _dataline = System.currentTimeMillis() / 1000 + 3600;
            _json.put("deadline", _dataline);// 有效时间为一个小时
            _json.put("scope", SCOPE);
            String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                    .toString().getBytes());
            byte[] _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);
            String _encodedSign = UrlSafeBase64.encodeToString(_sign);
            String _uploadToken = AccessKey + ':' + _encodedSign + ':'
                    + _encodedPutPolicy;
            Calendar calendar = Calendar.getInstance();
            final String fileName = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + file.getName();
            UpProgressHandler progressHandler = new UpProgressHandler() {
                @Override
                public void progress(String key, double percent) {
                    if (callBack != null) {
                        callBack.onProcess(percent);
                    }
                }
            };
            UploadOptions options = new UploadOptions(null, "image/*", false, progressHandler, null);
            uploadManager.put(file, fileName, _uploadToken,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info,
                                             JSONObject response) {
                            if (callBack != null) {
                                callBack.onSuccess(QINIU_HOST + key);
                            }
                        }
                    }, options);
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onError(AppApplication.getKString(R.string.failed_to_upload));
            }
        }
    }

    /**
     * 这个签名方法找了半天 一个个对出来的、、、、程序猿辛苦啊、、、 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }
}
