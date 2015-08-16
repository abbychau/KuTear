package com.kutear.app.swipebacklayout;

/**
 * @author Yrom
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    abstract SwipeBackLayout getSwipeBackLayout();

    abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    abstract void scrollToFinishActivity();

}
