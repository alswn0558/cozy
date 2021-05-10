package com.example.teamproject.view.decorator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;
/**
 * Created by 양민주
 */
class CustomMultipleDotSpan implements LineBackgroundSpan {

    private final float radius;
    private final int[] color;

    public CustomMultipleDotSpan(float radius, int color) {
        this.radius = radius;
        this.color = new int[] {color};
    }

    public CustomMultipleDotSpan(float radius, int[] color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void drawBackground( Canvas canvas, Paint paint,
                                int left, int right, int top, int baseline, int bottom,
                                CharSequence charSequence,
                                int start, int end, int lineNum){
        // 총 찍을수 있는 점의 개수 5개
        int total = Math.min(color.length, 5);

        int leftMost = (total - 1) * - 10;

        for (int i = 0; i < total; i++) {
            int oldColor = paint.getColor();
            if (color[i] != 0) {
                paint.setColor(color[i]);
            }
            // x = 중심 - 오른쪽 이동
            // y = 반지름 만큼 이동
            canvas.drawCircle((left + right) / 2 - leftMost, bottom + radius, radius, paint);
            paint.setColor(oldColor);
            // x 값 이동시킴
            leftMost = leftMost + 20;
        }
    }
}
