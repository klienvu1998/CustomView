package com.example.hyvu.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomView extends View {

    private final static String CONTENT="Android offers a sophisticated and powerful componentize model for building your UI," +
            " based on the fundamental layout classes: View and ViewGroup.";


    private Paint squarePaint,textPaint;
    private ArrayList<String> linesContent;
    private int countLine;

    public CustomView(Context context) {
        super(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //ANTI_ALIAS_FLAG khử răng cưa
        squarePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        squarePaint.setColor(Color.BLUE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(60);
        linesContent = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        linesContent.clear();
        StringMeasure(widthMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec,widthMeasureSpec);
        setMeasuredDimension(width,height);
    }

    private int measureHeight(int heightMeasureSpec,int widthMeasureSpec ){
        int result = 0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode == MeasureSpec.EXACTLY){
            result = heightSize;
        }
        else {
            result = Math.round(
                    ((textPaint.descent()-textPaint.ascent())*(countLine))+
                    ((textPaint.descent())*(countLine/2)) +
                    MeasureSpec.getSize(widthMeasureSpec) +
                    getPaddingRight() +
                    getPaddingLeft());
            if(heightMode == MeasureSpec.AT_MOST){
                result = Math.min(result,heightSize);
            }
        }

        return result;
    }

    private int measureWidth(int widthMeasureSpec){
        int result = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            result = widthSize;
        }
        else{
            result = (int) (textPaint.measureText(CONTENT) + getPaddingLeft() + getPaddingRight());
            if(widthMode == MeasureSpec.AT_MOST){
                result = Math.min(result,widthSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasWidth = getWidth();
        canvas.drawRect(0,0,canvasWidth,getWidth(),squarePaint);
        float canvasText = canvasWidth;
        for(int i=0;i<countLine;i++){
            canvasText+=((textPaint.descent()*4.5f));;
            canvas.drawText(linesContent.get(i),0,canvasText,textPaint);
            textPaint.descent();
        }
    }

    private void StringMeasure(int canvasWidth){
        canvasWidth = MeasureSpec.getSize(canvasWidth);
        textPaint.measureText(CONTENT);
        String t=CONTENT;
        while(t.length()>0){
            int breakIndex = textPaint.breakText(t,true,canvasWidth,null);
            if(t.length() <= breakIndex){}
            else if(t.charAt(breakIndex) == ' ' && t.length() > breakIndex){ }
            else{
                while(t.charAt(breakIndex-1) != ' '){
                    breakIndex--;
                }
            }
            linesContent.add(t.substring(0,breakIndex));
            t = t.substring(breakIndex);
        }
        countLine = linesContent.size();
    }
}
