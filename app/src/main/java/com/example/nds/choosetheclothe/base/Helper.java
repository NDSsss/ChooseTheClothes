package com.example.nds.choosetheclothe.base;

import android.graphics.Point;
import android.support.v7.widget.RecyclerView;

public interface Helper {

    int getViewEnd(int recyclerWidth, int recyclerHeight);

    int getDistanceToChangeCurrent(int childWidth, int childHeight);

    void setCurrentViewCenter(Point recyclerCenter, int scrolled, Point outPoint);

    void shiftViewCenter(Direction direction, int shiftAmount, Point outCenter);

    int getFlingVelocity(int velocityX, int velocityY);

    int getPendingDx(int pendingScroll);

    int getPendingDy(int pendingScroll);

    void offsetChildren(int amount, RecyclerView.LayoutManager lm);

    float getDistanceFromCenter(Point center, int viewCenterX, int viewCenterY);

    boolean isViewVisible(Point center, int halfWidth, int halfHeight, int endBound, int extraSpace);

    boolean hasNewBecomeVisible(PageScrollLayoutManager lm);

    boolean canScrollVertically();

    boolean canScrollHorizontally();

}
