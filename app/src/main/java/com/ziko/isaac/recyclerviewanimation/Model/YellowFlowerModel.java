package com.ziko.isaac.recyclerviewanimation.Model;

public class YellowFlowerModel {
    private String mImageUrl;
    private String mCreator;
    private int mLikes;
//    private int views;
//    private int downloads;

    public YellowFlowerModel(String mImageUrl, String mCreator, int mLikes) {
        this.mImageUrl = mImageUrl;
        this.mCreator = mCreator;
        this.mLikes = mLikes;
//        this.views = views;
//        this.downloads = downloads;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmCreator() {
        return mCreator;
    }

    public void setmCreator(String mCreator) {
        this.mCreator = mCreator;
    }

    public int getmLikes() {
        return mLikes;
    }

    public void setmLikes(int mLikes) {
        this.mLikes = mLikes;
    }


}
