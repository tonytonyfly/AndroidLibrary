package com.wytiger.common.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.wytiger.common.utils.encryp.AESUtil;
import com.wytiger.common.utils.common.LogUtil;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wytiger on 2016-10-17.
 * 存储数据管理器
 */

public class StorageManager {
    private String TAG = "StorageManager";
    private Context context;

    private static StorageManager instance;
    private String SPUserKey = "savedUsers";

    private String SPFileName = "share_data";

    private SharedPreferences sharedPref;

    private StorageManager(Context context) {
        this.context = context;
        //文件名
        sharedPref = context.getSharedPreferences(SPFileName, Context.MODE_PRIVATE);
    }

    public static StorageManager getInstance(Context context) {
        if (instance == null) {
            synchronized (StorageManager.class) {
                if (instance == null) {
                    instance = new StorageManager(context);
                }
            }
        }
        return instance;
    }

    public String[] getData() {
        String temp = getDataString();

        if (!TextUtils.isEmpty(temp)) {
            String[] usersWithComma = temp.split(",");
            List<String> userList = Arrays.asList(usersWithComma);
//            LogUtils.i(TAG,"userList = " + userList);
            return usersWithComma;
        }
        return new String[0];
    }


    public void saveData(String data) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String hasString = getDataString();
        if (hasString.contains(data)) {
            LogUtil.i(TAG, "数据已存在, user = " + data);
            return;
        } else {
            String temp =hasString + data + ",";
            String temp2 = AESUtil.encrypt(temp);
            editor.putString(SPUserKey, temp2);
            editor.commit();
            LogUtil.i(TAG, "加密前, encryptedString = " + temp);
            LogUtil.i(TAG, "加密后, decryptString = " + temp2);
            LogUtil.i(TAG, "保存数据, data = " + data);
        }
    }


    public String getDataString() {
        String encryptedString = (String) sharedPref.getString(SPUserKey, "");
        String decryptString = AESUtil.decrypt(encryptedString);
        LogUtil.i(TAG, "解密前, encryptedString = " + encryptedString);
        LogUtil.i(TAG, "解密后, decryptString = " + decryptString);

        return decryptString;
    }


}
