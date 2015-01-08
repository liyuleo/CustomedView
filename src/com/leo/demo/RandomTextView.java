package com.leo.demo;

import java.util.Random;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class RandomTextView extends View {
	private String mText;
	private int mTextSize;
	private int mTextColor;

	private Random mRandom = new Random(10);
	private Rect mBound;
	private Paint mPaint;

	public RandomTextView(Context context) {
		this(context, null);
	}

	public RandomTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RandomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
		int size = typedArray.getIndexCount();
		for (int i = 0; i < size; i++) {
			int attr = typedArray.getIndex(i);
			switch (attr) {
			case R.styleable.CustomTitleView_text:
				mText = typedArray.getString(attr);
				break;
			case R.styleable.CustomTitleView_textSize:
				mTextSize = typedArray.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_SP, 16, getResources()
										.getDisplayMetrics()));
				break;
			case R.styleable.CustomTitleView_textColor:
				mTextColor = typedArray.getColor(attr, android.R.color.black);
				break;

			default:
				break;
			}
		}
		typedArray.recycle();

		mPaint = new Paint();
		mPaint.setTextSize(mTextSize);

		mBound = new Rect();
		mPaint.getTextBounds(mText, 0, mText.length(), mBound);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setColor(Color.YELLOW);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

		mPaint.setColor(mTextColor);
		canvas.drawText(mText, (getWidth() - mBound.width()) / 2,
				(getHeight() + mBound.height()) / 2, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			random();
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	private void random(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < 4; i++){
			sb.append(mRandom.nextInt(10));
		}
		mText = sb.toString();
		postInvalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize= MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		int width = 0;
		int height = 0;
		
		if(widthMode == MeasureSpec.EXACTLY){
			width = widthSize;
		}else{
			mPaint.setTextSize(mTextSize);
			mPaint.getTextBounds(mText, 0, mText.length(), mBound);
			float textWidth = mBound.width();
			int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());  
	        width = desired;
		}
		
		if(heightMode == MeasureSpec.EXACTLY){
			height = heightSize;
		}else{
			mPaint.setTextSize(mTextSize);
			mPaint.getTextBounds(mText, 0, mText.length(), mBound);
			float textHeight = mBound.height();
			int desired = (int)(getPaddingTop() + textHeight + getPaddingBottom());
			height = desired;
		}
		
		setMeasuredDimension(width, height);
	}
	

}
