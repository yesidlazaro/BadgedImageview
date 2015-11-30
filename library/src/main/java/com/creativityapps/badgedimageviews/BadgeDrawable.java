package com.creativityapps.badgedimageviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.util.DisplayMetrics;

/**
 * A drawable for put into the Imageview
 */
class BadgeDrawable extends Drawable {
    private static final int TEXT_SIZE = 12;    // sp
    private static final int PADDING = 4;       // dp
    private static final int CORNER_RADIUS = 2; // dp
    private static final String TYPEFACE = "sans-serif-black";
    private static final int TYPEFACE_STYLE = Typeface.NORMAL;
    private final Paint paint;
    private String text;
    private Bitmap bitmap;
    private int width;
    private int height;

    BadgeDrawable(Context context, String badgeText, int badgeColor) {
        if (bitmap == null && badgeText != null) {
            text = badgeText;
            final DisplayMetrics dm = context.getResources().getDisplayMetrics();
            final float density = dm.density;
            final float scaledDensity = dm.scaledDensity;
            final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(0, 0, width, height, cornerRadius, cornerRadius, backgroundPaint);
            } else {
                canvas.drawRoundRect(new RectF(0, 0, width, height), cornerRadius, cornerRadius, backgroundPaint);
            }
            // punch out the word ,leaving transparency
            textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawText(text, padding, height - padding, textPaint);
        }
        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, getBounds().left, getBounds().top, paint);
        }
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

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }
}
