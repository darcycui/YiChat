/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.htmessage.fanxinht.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class LocalUserManager {
    /**
     * 保存Preference的name
     */
    public static final String PREFERENCE_NAME = "userInfo";
    private static SharedPreferences mSharedPreferences;
    private static LocalUserManager mPreferencemManager;
    private static SharedPreferences.Editor editor;
    private String SHARED_KEY_USER_INFO = "shared_key_user_info";
    private static Context context;

    private LocalUserManager(Context cxt) {
        this.context=cxt;
         mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static synchronized void init(Context cxt) {
         if (mPreferencemManager == null) {
            mPreferencemManager = new LocalUserManager(cxt);
        }
    }

    /**
     * 单例模式，获取instance实例
     *
     * @param
     * @return
     */
    public synchronized static LocalUserManager getInstance() {
        Log.d("CurrentUserManager---->",context.getPackageName());
        if (mPreferencemManager == null) {
            throw new RuntimeException("please init first!");
        }

        return mPreferencemManager;
    }


    public void setUserJson(JSONObject userJson) {
        String userInfo = "";
        if (userJson != null) {
            try {
                userInfo = userJson.toJSONString();
            } catch (JSONException e) {
            }
        }
        editor.putString(SHARED_KEY_USER_INFO, userInfo);
        editor.commit();
    }

    public JSONObject getUserJson() {
        JSONObject userJson =null;

        String userStr = mSharedPreferences.getString(SHARED_KEY_USER_INFO, null);
        if (userStr != null) {
            userJson = JSONObject.parseObject(userStr);

        }
        return userJson;
    }

}
