package com.leo.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class DoubleColorCircleProgressBar extends View {

	private int mFirstColor;
	private int mSecondColor;
	private int mSpeed;
	private int mCircleWidth;

	private Paint mPaint;
	private int mProgress;
	private boolean isNext = false;

	public DoubleColorCircleProgressBar(Context context) {
		this(context, null);
	}

	public DoubleColorCircleProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DoubleColorCircleProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.CustomProgressBar, defStyleAttr, 0);

		int size = a.getIndexCount();
		for (int i = 0; i < size; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomProgressBar_firstColor:
				mFirstColor = a.getColor(attr,
						android.R.color.holo_orange_light);
				break;
			case R.styleable.CustomProgressBar_secondColor:
				mSecondColor = a
						.getColor(attr, android.R.color.holo_green_dark);
				break;
			case R.styleable.CustomProgressBar_circleWidth:
				mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
								getResources().getDisplayMetrics()));
				break;
			case R.styleable.CustomProgressBar_speed:
				mSpeed = a.getColor(attr, 10);
				break;

			default:
				break;
			}
		}
		a.recycle();

		mPaint = new Paint();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					mProgress++;
					if (mProgress == 360) {
						mProgress = 0;
						if (!isNext) {
							isNext = true;
						} else {
							isNext = false;
						}
					}
					postInvalidate();
					try {
						Thread.sleep(mSpeed);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int centre = getWidth() / 2; // 获取圆心的x坐标
		int radius = centre - mCircleWidth;// 半径
		mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
		mPaint.setAntiAlias(true); // 消除锯齿
		mPaint.setStyle(Paint.Style.STROKE); // 设置空心
		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
		if (!isNext) {// 第一颜色的圈完整，第二颜色跑
			mPaint.setColor(mFirstColor); // 设置圆环的颜色
			canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
			mPaint.setColor(mSecondColor); // 设置圆环的颜色
			canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
		} else {
			mPaint.setColor(mSecondColor); // 设置圆环的颜色
			canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
			mPaint.setColor(mFirstColor); // 设置圆环的颜色
			canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
		}
	}

}
