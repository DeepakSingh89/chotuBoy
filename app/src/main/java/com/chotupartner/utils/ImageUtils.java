package com.chotupartner.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * Created by t.gupta on 24-10-2017.
 */

public class ImageUtils {
    private static final String TAG = "ImageUtils";
    public static void setTintedDrawable(Context context, int drawableId, ImageView imageView, @ColorRes int color) {
        if (context == null) {
            return;
        }

        if (imageView == null) {
            return;
        }

        Drawable drawable  = ContextCompat.getDrawable(context, drawableId);
        @ColorInt Integer tintColor = ContextCompat.getColor(context, color);
        drawable = ImageUtils.getTintedDrawable(drawable, tintColor);
        if (drawable != null)
            imageView.setImageDrawable(drawable);
    }
    public static Drawable getTintedDrawable(Drawable drawable, @ColorInt int tintColor) {
        if (drawable == null) {
            return null;
        }
        drawable = DrawableCompat.wrap(drawable);
        drawable = drawable.mutate();
        DrawableCompat.setTint(drawable, tintColor);
        return drawable;
    }

}
