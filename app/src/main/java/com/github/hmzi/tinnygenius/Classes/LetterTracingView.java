package com.github.hmzi.tinnygenius.Classes;

// LetterTracingView.java
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LetterTracingView extends View {
    private Paint paint;
    private Path path;

    public LetterTracingView(Context context, AttributeSet attrs) {
        super(context, attrs);

//        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeWidth(40);

//        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                path.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            default:
                return false;
        }

        // Invalidate the view to force a redraw
        invalidate();
        return true;
    }

    public void clearCanvas() {
        path.reset();
        invalidate();
    }
}
