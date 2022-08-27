package com.example.demo.src.post;

import java.util.ArrayList;
import java.util.List;

public class PostGeneric<T> {
    private List<T> getPostsRes;
    public T getPostsRes(T t){return t;}
    public void setPostsRes(List<T> t){this.getPostsRes = t;}
    public T get(int idx){
        return getPostsRes.get(idx);
    }

    public boolean checkPage(long pageNum, long total){
        if (total >= pageNum){
            return true;
        }
        return false;
    }

    public List<T> printResult(long pageNum){
        long size = getPostsRes.size();
        long page = size / 20;
        page += (size % 20 != 0) ? 1 : 0;

        List<T> arr = new ArrayList<>();
        if (checkPage(pageNum, page)) {
            if (page == pageNum) {
                for (long i = (pageNum - 1) % 20; i < 20 * (pageNum - 1) + (size % 20); i++) {
                    arr.add(getPostsRes.get((int) i));
                }
            } else {
                for (long i = (pageNum - 1) * 20; i < 20 * pageNum - 1; i++) {
                    arr.add(getPostsRes.get((int) i));
                }
            }
        }
        return arr;
    }
}
