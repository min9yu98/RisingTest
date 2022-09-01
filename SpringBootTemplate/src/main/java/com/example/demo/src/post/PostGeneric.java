package com.example.demo.src.post;

import java.util.ArrayList;
import java.util.List;

public class PostGeneric<T> {
    private List<T> getPostsRes;
    public List<T> getPostsRes(){return this.getPostsRes;}
    public void setPostsRes(List<T> t){this.getPostsRes = t;}
    public T get(int idx){
        return getPostsRes.get(idx);
    }


    public List<T> printResult(long pageNum){
        long size = getPostsRes.size();
        long page = size / 20;
        page += (size % 20 != 0) ? 1 : 0;


        List<T> arr = new ArrayList<>();
        if (page <= pageNum) {
            for (long i = (page - 1) * 20; i < 20 * (page - 1) + (size % 20 == 0 ? 20 : size % 20); i++) {
                arr.add(getPostsRes.get((int) i));
            }
        } else{
            for (long i = (pageNum - 1) * 20; i < 20 * pageNum; i++) {
                arr.add(getPostsRes.get((int) i));
            }
        }
        return arr;
    }
}
