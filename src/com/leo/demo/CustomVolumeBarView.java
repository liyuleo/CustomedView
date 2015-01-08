package com.leo.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class CustomVolumeBarView extends View {

	private int mFirstColor;
	private int mSecondColor;
	private Bitmap mImage;
	private int mGap;
	private int mCircleWidth;
	private int mSpanCount;

	private int mCurrentCount = 5;
	private Paint mPaint;
	private Rect mRect;
	private float mLastY;
	
	public CustomVolumeBarView(Context context) {
		this(context, null);
	}

	public CustomVolumeBarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomVolumeBarView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.CustomVomule, defStyleAttr, 0);
		int size = a.getIndexCount();
		for (int i = 0; i < size; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomVomule_circleWidth:
				mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
								getResources().getDisplayMetrics()));
				break;
			case R.styleable.CustomVomule_firstColor:
				mFirstColor = a.getColor(attr, android.R.color.white);
				break;
			case R.styleable.CustomVomule_secondColor:
				mSecondColor = a.getColor(attr, android.R.color.black);
				break;
			case R.styleable.CustomVomule_image:
				mImage = BitmapFactory.decodeResource(getResources(),
						a.getResourceId(attr, R.drawable.ic_launcher));
				break;
			case R.styleable.CustomVomule_gap:
				mGap = a.getInt(attr, 10);
				break;
			case R.styleable.CustomVomule_spanCount:
				mSpanCount = a.getInt(attr, 20);
				break;

			default:
				break;
			}
		}

		a.recycle();

		mPaint = new Paint();
		mRect = new Rect();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(mCircleWidth);
		mPaint.setStrokeCap(Paint.Cap.ROUND);

		int center = getWidth() / 2;
		int radius = center - mCircleWidth / 2;
		drawOval(canvas, center, radius);

		int secondRadius = center - mCircleWidth;
		mRect.left = (int) (center - (Math.sqrt(2) * secondRadius / 2));
		mRect.top = (int) (center - (Math.sqrt(2) * secondRadius / 2));
		mRect.bottom = (int) (mRect.left + Math.sqrt(2) * secondRadius);
		mRect.right = (int) (mRect.left + Math.sqrt(2) * secondRadius);

		if (mImage.getWidth() < Math.sqrt(2) * secondRadius) {
			mRect.left = (int) (mRect.left + Math.sqrt(2) * secondRadius * 1.0f
					/ 2 - mImage.getWidth() * 1.0f / 2);
			mRect.top = (int) (mRect.top + Math.sqrt(2) * secondRadius * 1.0f
					/ 2 - mImage.getHeight() * 1.0f / 2);
			mRect.right = (int) (mRect.left + mImage.getWidth());
			mRect.bottom = (int) (mRect.top + mImage.getHeight());

		}

		canvas.drawBitmap(mImage, null, mRect, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float y = event.getY();
			if(y > mLastY){
				mCurrentCount ++;
				if(mCurrentCount > mSpanCount){
					mCurrentCount = mSpanCount;
				}
				postInvalidate();
			}else if(y < mLastY){
				mCurrentCount --;
				if(mCurrentCount < 0){
					mCurrentCount = 0;
				}
				postInvalidate();
			}
			mLastY = y;
			break;
		case MotionEvent.ACTION_UP:
			mLastY = event.getY();
			break;

		default:
			break;
		}
		return true;
	}

	private void drawOval(Canvas canvas, int center, int radius) {
		float itemSize = (360 * 1.0f - mSpanCount * mGap) / mSpanCount;
		RectF oval = new RectF(center - radius, center - radius, center
				+ radius, center + radius);

		mPaint.setColor(mFirstColor);
		for (int i = 0; i < mSpanCount; i++) {
			canvas.drawArc(oval, i * (itemSize + mGap), itemSize, false, mPaint);
		}

		mPaint.setColor(mSecondColor);
		for (int i = 0; i < mCurrentCount; i++) {
			canvas.drawArc(oval, i * (itemSize + mGap), itemSize, false, mPaint);
		}

	}

}
