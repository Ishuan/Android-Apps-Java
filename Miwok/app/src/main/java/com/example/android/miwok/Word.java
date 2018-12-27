package com.example.android.miwok;

import android.media.Image;

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageID = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String defaultTranslation,String miwokTranslation){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

   public Word(String defaultTranslation, String miwokTranslation, int imageID){
       mDefaultTranslation = defaultTranslation;
       mMiwokTranslation = miwokTranslation;
       mImageID = imageID;
   }

   public String getDefaultTranslation(){
       return mDefaultTranslation;
   }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageID() {
        return mImageID;
    }

    public boolean hasImage(){
        return mImageID != NO_IMAGE_PROVIDED;
    }
}
