package io.gonative.android;

import android.content.Context;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by weiyin on 4/28/14.
 */
public class UrlInspector {
    private static final String TAG = UrlInspector.class.getName();
    // singleton
    private static UrlInspector instance = null;
    private Context context;

    private Pattern userIdRegex = null;

    private String userId = null;


    public static UrlInspector getInstance(){
        if (instance == null) {
            instance = new UrlInspector();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();

        String regexString = AppConfig.getInstance(context).getString("authNavReplaceRegex");
        if (regexString != null && !regexString.isEmpty()) {
            try {
                userIdRegex = Pattern.compile(regexString);
            } catch (PatternSyntaxException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    private UrlInspector() {
        // prevent direct instantiation
    }

    public void inspectUrl(String url) {
        if (userIdRegex != null) {
            Matcher matcher = userIdRegex.matcher(url);
            if (matcher.groupCount() > 0 && matcher.find()) {
                setUserId(matcher.group(1));
            }
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
