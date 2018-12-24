package com.example.nds.choosetheclothe.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

public class ScrollListenerAdapter<T extends RecyclerView.ViewHolder> implements PageScrollView.ScrollStateChangeListener<T> {

    private PageScrollView.ScrollListener<T> adapter;

    public ScrollListenerAdapter(@NonNull PageScrollView.ScrollListener<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onScrollStart(@NonNull T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScrollEnd(@NonNull T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScroll(float scrollPosition,
                         int currentIndex, int newIndex,
                         @Nullable T currentHolder, @Nullable T newCurrentHolder) {
        adapter.onScroll(scrollPosition, currentIndex, newIndex, currentHolder, newCurrentHolder);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScrollListenerAdapter) {
            return adapter.equals(((ScrollListenerAdapter) obj).adapter);
        } else {
            return super.equals(obj);
        }
    }
}
