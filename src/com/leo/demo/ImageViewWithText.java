package com.leo.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ImageViewWithText extends View {

	private static final int IMAGE_SCALE_FITXY = 0;
	private static final int IMAGE_SCALE_CENTER = 1;
	
	private String mText;
	private int mTextSize;
	private int mTextColor;
	private int mScaleType;
	private Bitmap mImage;
	private Rect mRect;
	private Paint mPaint;
	private Rect mTextBound;
	private int mWidth;
	private int mHeight;

	public ImageViewWithText(Context context) {
		this(context, null, 0);
	}

	public ImageViewWithText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ImageViewWithText(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.CustomImageView, defStyleAttr, 0);
		int size = a.getIndexCount();
		for (int i = 0; i < size; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomImageView_text:
				mText = a.getString(attr);
				break;
			case R.styleable.CustomImageView_textSize:
				mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
								getResources().getDisplayMetrics()));
				break;
			case R.styleable.CustomImageView_textColor:
				mTextColor = a.getColor(attr, android.R.color.black);
				break;
			case R.styleable.CustomImageView_image:
				mImage = BitmapFactory.decodeResource(getResources(),
						a.getResourceId(attr, 0));
				break;
			case R.styleable.CustomImageView_imageScaleType:
				mScaleType = a.getInt(attr, 0);
				break;

			default:
				break;
			}
		}
		a.recycle();

		mRect = new Rect();
		mPaint = new Paint();
		mTextBound = new Rect();
		mPaint.setTextSize(mTextSize);
		// 计算了描绘字体需要的范围
		mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			mWidth = specSize;
		} else {
			// 由图片决定的宽
			int desireByImg = getPaddingLeft() + getPaddingRight()
					+ mImage.getWidth();
			// 由字体决定的宽
			int desireByTitle = getPaddingLeft() + getPaddingRight()
					+ mTextBound.width();

			if (specMode == MeasureSpec.AT_MOST) {
				int desire = Math.max(desireByImg, desireByTitle);
				mWidth = Math.min(desire, specSize);
			}
		}

		specMode = MeasureSpec.getMode(heightMeasureSpec);
		specSize = MeasureSpec.getSize(heightMeasureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			mHeight = specSize;
		} else {
			int desireByImg = getPaddingTop() + getPaddingBottom()
					+ mImage.getHeight() + mTextBound.height();
			if (specMode == MeasureSpec.AT_MOST) {
				mHeight = Math.min(specSize, desireByImg);
			}

		}
		setMeasuredDimension(mWidth, mHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setStrokeWidth(4);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.CYAN);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

		mRect.left = getPaddingLeft();
		mRect.right = mWidth - getPaddingRight();
		mRect.top = getPaddingTop();
		mRect.bottom = mHeight - getPaddingBottom();

		mPaint.setColor(mTextColor);
		mPaint.setStyle(Style.FILL);

		if (mTextBound.width() > mWidth) {
			TextPaint paint = new TextPaint(mPaint);
			String msg = TextUtils.ellipsize(mText, paint,
					(float) mWidth - getPaddingLeft() - getPaddingRight(),
					TextUtils.TruncateAt.END).toString();
			canvas.drawText(msg, getPaddingLeft(),
					mHeight - getPaddingBottom(), mPaint);

		} else {
			// 正常情况，将字体居中
			canvas.drawText(mText, mWidth / 2 - mTextBound.width() * 1.0f / 2,
					mHeight - getPaddingBottom(), mPaint);
		}
		
		//取消使用掉的快  
        mRect.bottom -= mTextBound.height();  
  
        if (mScaleType == IMAGE_SCALE_FITXY)  
        {  
            canvas.drawBitmap(mImage, null, mRect, mPaint);  
        } else  
        {  
            //计算居中的矩形范围  
        	mRect.left = mWidth / 2 - mImage.getWidth() / 2;  
        	mRect.right = mWidth / 2 + mImage.getWidth() / 2;  
        	mRect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;  
        	mRect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;  
  
            canvas.drawBitmap(mImage, null, mRect, mPaint);  
        }  
	}

}
