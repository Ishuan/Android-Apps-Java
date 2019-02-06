package com.example.android.miwok;

import android.media.Image;

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageID = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mAudioID;


    public Word(String mDefaultTranslation, String mMiwokTranslation, int mAudioID) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mAudioID = mAudioID;
    }

    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageID, int mAudioID) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageID = mImageID;
        this.mAudioID = mAudioID;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageID() {
        return mImageID;
    }

    public int getmAudioID() {
        return mAudioID;
    }

    public boolean hasImage() {
        return mImageID != NO_IMAGE_PROVIDED;
    }
}
