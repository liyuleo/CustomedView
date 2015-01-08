package com.leo.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewGrounp extends ViewGroup {
	public CustomViewGrounp(Context context) {
		super(context);
	}

	public CustomViewGrounp(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomViewGrounp(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		MarginLayoutParams cParams = null;
		int childCount = getChildCount();
		int childWidth = 0;
		int chidHeight = 0;

		int left = 0;
		int top = 0;
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			childWidth = child.getMeasuredWidth();
			chidHeight = child.getMeasuredHeight();

			cParams = (MarginLayoutParams) child.getLayoutParams();
			switch (i) {
			case 0:
				left = cParams.leftMargin;
				top = cParams.topMargin;
				break;
			case 1:
				left = getWidth() - cParams.leftMargin - childWidth
						- cParams.rightMargin;
				top = cParams.topMargin;
				break;
			case 2:
				left = cParams.leftMargin;
				top = getHeight() - cParams.bottomMargin - chidHeight;
				break;
			case 3:
				left = getWidth() - cParams.leftMargin - childWidth
						- cParams.rightMargin;
				top = getHeight() - cParams.bottomMargin - chidHeight;
				break;

			default:
				break;
			}
			child.layout(left, top, left + childWidth, top + chidHeight);
		}
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		return new MarginLayoutParams(p);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int topWidth = 0;
		int bottomWidth = 0;

		int leftHeight = 0;
		int rightHeight = 0;

		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();
			MarginLayoutParams cParams = (MarginLayoutParams) child
					.getLayoutParams();
			if (i == 0 || i == 1) {
				topWidth += childWidth + cParams.leftMargin
						+ cParams.rightMargin;
			}
			if (i == 2 || i == 3) {
				bottomWidth += childWidth + cParams.leftMargin
						+ cParams.rightMargin;
			}

			if (i == 0 || i == 2) {
				leftHeight += childHeight + cParams.topMargin
						+ cParams.bottomMargin;
			}

			if (i == 1 || i == 3) {
				rightHeight += childHeight + cParams.topMargin
						+ cParams.bottomMargin;
			}

		}

		int height = Math.max(leftHeight, rightHeight);
		int width  = Math.max(bottomWidth, topWidth);

		int measuredWidth = widthMode == MeasureSpec.EXACTLY ? widthSize
				: width;
		int measuredHeight = heightMode == MeasureSpec.EXACTLY ? heightSize
				: height;
		setMeasuredDimension(measuredWidth, measuredHeight);

	}

}
