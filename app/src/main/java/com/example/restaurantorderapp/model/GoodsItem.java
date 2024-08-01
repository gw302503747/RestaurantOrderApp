package com.example.restaurantorderapp.model;

import com.google.gson.annotations.SerializedName;

public class GoodsItem {
    @SerializedName("goodsId")
    private int goodsId;

    @SerializedName("goodsName")
    private String goodsName;

    @SerializedName("goodsImage")
    private String goodsImage;

    @SerializedName("goodsDescription")
    private String goodsDescription;

    @SerializedName("goodsQuantity")
    private String goodsQuantity;

    @SerializedName("quantityImage")
    private String quantityImage;

    @SerializedName("goodsPrice")
    private int goodsPrice;

    @SerializedName("goodsCategory")
    private String goodsCategory;

    @SerializedName("goodsTag")
    private String goodsTag;

    @SerializedName("createTime")
    private String createTime;

    // Getters and Setters
    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public String getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(String goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public String getQuantityImage() {
        return quantityImage;
    }

    public void setQuantityImage(String quantityImage) {
        this.quantityImage = quantityImage;
    }

    public int getGoodsPrice() {
        return goodsPrice;  // Updated to return int
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}