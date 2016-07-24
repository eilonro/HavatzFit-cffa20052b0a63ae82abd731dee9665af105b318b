package com.hackeruproj.android.havatzfit.custom_toolbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hackeruproj.android.havatzfit.R;

/**
 * Created by Igor on 20-Jul-16.
 */

//create thise custom toolbar class, make it inflate toolbar.xml res file
//once inflated implement this class unto the main Activity
//?????
//profit

public class UserToolbar extends RelativeLayout
{
    private ImageView mProfilePic;
    private TextView mUserName, mUserMail;

    private static String mStringName,mStringMail;
    private static Bitmap mBitmap;

    public UserToolbar(Context context)
    {
        super(context);
        init(context);
    }

    public UserToolbar(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public UserToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        //start inflate toolbar.xml
        inflate(context, R.layout.toolbar,this);
        //finish inflate toolbar.xml

        //Temporarily adding custom attr of the toolbar thruought code
        //set temp background color and shaping (possible to add custom drawble style instead
        setBackgroundResource(R.color.userBarColor);

        //set padding of the widget
        int padding = (int)getResources().getDimension(R.dimen.userBarPadding);
        setPadding(padding,padding,padding,padding);

        //start connect local variables with xml tag ids
        mProfilePic = (ImageView)findViewById(R.id.toolbarProfPicID);
        mUserName = (TextView)findViewById(R.id.userNameID);
        mUserMail = (TextView)findViewById(R.id.userMailID);
        //finish connect local variables with xml tag ids


        //TODO create if statement that checks if the String values are null then set defualt values
        //TODO in the objects
    }



}
