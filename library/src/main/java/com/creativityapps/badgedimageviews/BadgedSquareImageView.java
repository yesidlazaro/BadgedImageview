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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;


/**
 * A view group that draws a badge drawable on top of it's contents.
 */
public class BadgedSquareImageView extends SquareImageView {

    private Drawable badge;
    private boolean drawBadge;
    private boolean badgeBoundsSet = false;
    private int badgeGravity;
    private int badgePadding;
    private String badgeText;
    private int badgeColor;

    public BadgedSquareImageView(Context context, AttributeSet attrs) {
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

    /**
     * A drawable for indicating that an image is animated
     */
    public  class GifBadge extends Drawable {

        private static final int TEXT_SIZE = 12;    // sp
        private static final int PADDING = 4;       // dp
        private static final int CORNER_RADIUS = 2; // dp
        private static final String TYPEFACE = "sans-serif-black";
        private static final int TYPEFACE_STYLE = Typeface.NORMAL;
        private  Bitmap bitmap;
        private  int width;
        private  int height;
        private final Paint paint;
        public String text;

        GifBadge(Context context,String badgeText,int badgeColor) {
            if (bitmap == null) {
                text=badgeText;
                final DisplayMetrics dm = context.getResources().getDisplayMetrics();
                final float density = dm.density;
                final float scaledDensity = dm.scaledDensity;
                final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint
                        .SUBPIXEL_TEXT_FLAG);
                textPaint.setTypeface(Typeface.create(TYPEFACE, TYPEFACE_STYLE));
                textPaint.setTextSize(TEXT_SIZE * scaledDensity);

                final float padding = PADDING * density;
                final float cornerRadius = CORNER_RADIUS * density;
                final Rect textBounds = new Rect();
                textPaint.getTextBounds(text, 0, text.length(), textBounds);
                height = (int) (padding + textBounds.height() + padding);
                width = (int) (padding + textBounds.width() + padding);
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                bitmap.setHasAlpha(true);
                final Canvas canvas = new Canvas(bitmap);
                final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                backgroundPaint.setColor(badgeColor);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                    canvas.drawRoundRect(0, 0, width, height, cornerRadius, cornerRadius, backgroundPaint);
                }else {
                    // TODO: 11/21/15 support cornerRadius for api < 21
                    canvas.drawRect(0, 0, width, height,backgroundPaint);
                }
                // punch out the word ,leaving transparency
                textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                canvas.drawText(text, padding, height - padding, textPaint);
            }
            paint = new Paint();
        }

        @Override
        public int getIntrinsicWidth() {
            return width;
        }

        @Override
        public int getIntrinsicHeight() {
            return height;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(bitmap, getBounds().left, getBounds().top, paint);
        }

        @Override
        public void setAlpha(int alpha) {
            // ignored
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }
}
