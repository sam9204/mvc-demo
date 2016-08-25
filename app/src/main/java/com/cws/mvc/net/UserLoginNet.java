package com.cws.mvc.net;

import android.os.SystemClock;

import com.cws.mvc.bean.User;

/**
 * Created by cws on 2016/8/25.
 */
public class UserLoginNet {

    public boolean sendUserInfo(User user) {
        SystemClock.sleep(2000);

        if ("1".equals(user.username) && "1".equals(user.password)) {
            return true;
        } else {
            return false;
        }
    }
}
