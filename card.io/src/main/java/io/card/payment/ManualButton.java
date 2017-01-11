package io.card.payment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;

import java.util.Arrays;

import io.card.payment.ui.ViewUtil;

/**
 * Created by faridul on 28/12/2016.
 */

class ManualButton {
    private static final String TAG = ManualButton.class.getSimpleName();


    private static final int ALPHA = 100;
    private final Paint mPaint;

    private Bitmap mManualButton;
    private boolean mUseCardIOManualButton;
    private final Context mContext;

    public ManualButton(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(ALPHA);
        mManualButton = null;
        mContext = context;
    }

    void loadManualButton(boolean useCardIOManualButton) {
        if (mManualButton != null && useCardIOManualButton == mUseCardIOManualButton) {
            return; // no change, don't reload
        }
        int density = DisplayMetrics.DENSITY_HIGH;
        mUseCardIOManualButton = useCardIOManualButton;
//        if (useCardIOManualButton) {
            mManualButton = ViewUtil.base64ToBitmap(Base64EncodedImages.card_io_logo, mContext, density);


//        } else {
//            mManualButton = ViewUtil.base64ToBitmap(Base64EncodedImages.paypal_logo, mContext, density);
//        }
    }

    public void draw(Canvas canvas, float maxWidth, float maxHeight) {

        if (mManualButton == null) {
            loadManualButton(false);
        }

        canvas.save();

        float drawWidth, drawHeight;
        float targetAspectRatio = (float) mManualButton.getHeight() / mManualButton.getWidth();
        if ((maxHeight / maxWidth) < targetAspectRatio) {
            drawHeight = maxHeight;
            drawWidth = maxHeight / targetAspectRatio;
        } else {
            drawWidth = maxWidth;
            drawHeight = maxWidth * targetAspectRatio;
        }

        float halfWidth = drawWidth / 2;
        float halfHeight = drawHeight / 2;

        canvas.drawBitmap(mManualButton, new Rect(0, 0, mManualButton.getWidth(), mManualButton.getHeight()), new RectF(
                -halfWidth, -halfHeight, halfWidth, halfHeight), mPaint);

        Button btn = new Button(mContext);
        btn.setText("test test tes test test");
        btn.setBackgroundColor(Color.WHITE);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        btn.draw(canvas);

        canvas.restore();
    }
}
