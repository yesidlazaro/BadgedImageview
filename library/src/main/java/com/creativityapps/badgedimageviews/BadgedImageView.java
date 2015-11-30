package com.creativityapps.badgedimageviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Gravity;

public class BadgedImageView extends ForegroundImageView {
    private Drawable badge;
    private boolean drawBadge;
    private boolean badgeBoundsSet = false;
    private int badgeGravity;
    private int badgePadding;
    private String badgeText;
    private int badgeColor;

    public BadgedImageView(Context context) {
        this(context, null);
    }

    public BadgedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BadgedImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BadgedImageView, 0, 0);

        badgeGravity = a.getInt(R.styleable.BadgedImageView_badgeGravity, Gravity.END | Gravity
                .BOTTOM);
        badgePadding = a.getDimensionPixelSize(R.styleable.BadgedImageView_badgePadding, 0);
        badgeText = a.getString(R.styleable.BadgedImageView_badgeText);
        badgeColor = a.getColor(R.styleable.BadgedImageView_badgeColor, Color.WHITE);
        badge = new BadgeDrawable(context, badgeText, badgeColor);

        a.recycle();
    }

    public void showBadge(boolean show) {
        drawBadge = show;
    }

    public void setBadgeColor(@ColorInt int color) {
        badgeColor = color;
        updateBadge(getContext());
    }

    public void setBadgeText(String newText) {
        this.badgeText = newText;
        updateBadge(getContext());
    }

    private void updateBadge(Context context) {
        badge = new BadgeDrawable(context, badgeText, badgeColor);
        invalidate();
    }

    public boolean isBadgeVisible() {
        return drawBadge;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (drawBadge) {
            if (!badgeBoundsSet) {
                layoutBadge();
            }
            badge.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        layoutBadge();
    }

    private void layoutBadge() {
        Rect badgeBounds = badge.getBounds();
        Gravity.apply(badgeGravity,
                badge.getIntrinsicWidth(),
                badge.getIntrinsicHeight(),
                new Rect(0, 0, getWidth(), getHeight()),
                badgePadding,
                badgePadding,
                badgeBounds);
        badge.setBounds(badgeBounds);
        badgeBoundsSet = true;
    }
}
