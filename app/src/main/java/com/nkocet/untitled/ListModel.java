package com.nkocet.untitled;

import java.util.ArrayList;
import java.util.List;

public class ListModel {

    private int imageId;
    private  String title;

    public int getImageId(){
        return this.imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<ListModel>  getObjectList(){
        List<ListModel> dataList = new ArrayList<>();
        int[] images = getImages();

        for(int i =0 ; i < images.length; i++){
            ListModel listModel = new ListModel();
            listModel.setImageId(images[i]);
            listModel.setTitle("Picture"+1);
            dataList.add(listModel);
        }

        return  dataList;
    }

    private static  int[] getImages(){
        int[] imageArray = {R.drawable.image_1};
        return  imageArray;
    }
}
