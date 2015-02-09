package io.picarete.picarete.model.container.userdata;

import android.content.Context;

/**
 * Created by iem on 16/01/15.
 */
public class UserAccessor {
    private static User user;
    private static UserNotifications userNotifications;

    public static User getUser(Context context){
        if(user == null){
            user = new User(context).load(context);
            userNotifications = new UserNotifications();
            user.listener = userNotifications;
        }

        return user;
    }
}
