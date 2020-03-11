package com.example.hyvu.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomView extends View {

    final static String CONTENT="Android offers a sophisticated and powerful componentizedeeeee model for building your UI," +
            " based on the fundamental layout classes: View and ViewGroup.";


    private Paint squarePaint,textPaint;
    private ArrayList<String> linesContent;

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
        linesContent = new ArrayList<>();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasWidth = getWidth();
        int canvasHeight= getHeight();
        float centerView= canvasWidth * 0.5f;
        canvas.drawRect(0,0,canvasWidth,getWidth(),squarePaint);

        textPaint.measureText(CONTENT);
        textPaint.setTextSize(40);
        String t=CONTENT;
        String line="";
        float lengthText = textPaint.measureText(t);

        Rect rect = new Rect();
        canvas.getClipBounds(rect);

        int ratio = (int) ((lengthText/rect.width())+2);
        Toast.makeText(getContext(),textPaint.measureText(t)+"",Toast.LENGTH_SHORT).show();

        while(ratio!=0){

//            int newLength = textPaint.breakText(t,true,canvasWidth,null);
//            line = t.substring(0,newLength);
//            newLength=line.lastIndexOf(" ");
//            newLength = newLength +1;
//            String k = t.substring(newLength);
//            for(int i=0;i<k.length();i++){
//                if(k.charAt(i) == ' '){
//                    k=t.substring(newLength,i);
//                }
//            }
//            ratio--;

            int newLength = textPaint.breakText(t,true,canvasWidth,null);
            line = t.substring(0,newLength);
            if(ratio>1){
                newLength=line.lastIndexOf(" ");
                linesContent.add(t.substring(0,newLength));
                newLength = newLength +1;
                t = t.substring(newLength);
            }
            else{
                linesContent.add(t.substring(0,newLength));
                t = t.substring(newLength);
            }
            ratio--;
        }
        float canvasText = canvasWidth+(textPaint.descent()*4);
        for(int i=0;i<linesContent.size();i++){
            canvas.drawText(linesContent.get(i),0,canvasText,textPaint);
            textPaint.descent();
            canvasText+=((textPaint.descent()*4.5f));;
        }
    }
}
