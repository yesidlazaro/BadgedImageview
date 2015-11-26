/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.creativityapps.badgedimageviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Gravity;


/**
 * A view group that draws a badge drawable on top of it's contents.
 */
public class BadgedFourThreeImageView extends FourThreeImageView {

    private Drawable badge;
    private boolean drawBadge;
    private boolean badgeBoundsSet = false;
    private int badgeGravity;
    private int badgePadding;
    private String badgeText;
    private int badgeColor;

    public BadgedFourThreeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BadgedImageView, 0, 0);
        badgeGravity = a.getInt(R.styleable.BadgedImageView_badgeGravity, Gravity.END | Gravity
                .BOTTOM);
        badgePadding = a.getDimensionPixelSize(R.styleable.BadgedImageView_badgePadding, 0);
        badgeText=a.getString(R.styleable.BadgedImageView_badgeText);
        badgeColor=a.getColor(R.styleable.BadgedImageView_badgeColor,Color.WHITE);
        badge = new GifBadge(context,badgeText,badgeColor);

        a.recycle();
    }

    public void showBadge(boolean show) {
        drawBadge = show;
    }

    public void setBadgeColor(@ColorInt int color) {
        badgeColor=color;
        badge.setColorFilter(badgeColor, PorterDuff.Mode.SRC_IN);
    }

    public void setBadgeText(String newText) {
        this.badgeText=newText;
        badge=new GifBadge(getContext(),badgeText,badgeColor);
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
