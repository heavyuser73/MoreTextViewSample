package com.heavyuser.moretextview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

public class MoreTextView extends androidx.appcompat.widget.AppCompatTextView {
    final private int MIN_CHAR_LENGTH = 1;
    final private int MAGIC_LENGTH = 50;
    private CharSequence oriText;
    private int defaultSpanColor = Color.RED;
    private boolean bInit = false;
    private boolean moreState = false;
    private CharSequence moreText = "...More";
    private CharSequence lessText = "Less";
    private boolean bLessButton = false;
    private int nLastLine = 1;


    public void setMaxLines(int line) {
        nLastLine = line-1;
        if(nLastLine < 0) {
            nLastLine = 0;
        }
    }

    public void setSpanColor(int color) {
        defaultSpanColor = color;
    }

    //default value is false
    public void setLessButton(boolean bLessButton) {
        this.bLessButton = bLessButton;
    }

    public boolean getInit() {
        return bInit;
    }

    public void setInit(boolean bInit) {
        this.bInit = bInit;
    }

    public void setMoreText(CharSequence text) {
        moreText = text;
    }

    public void setLessText(CharSequence text) {
        lessText =text;
    }

    public MoreTextView(Context context) {
        this(context, null);
    }

    private CharSequence getOriText() {
        return oriText;
    }

    public void setOriText(CharSequence oriText) {
        this.oriText = oriText;
    }

    public boolean getMoreState() {
        return moreState;
    }

    public void setMoreState(boolean moreState) {
        this.moreState = moreState;
    }

    public MoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(getInit() == false) {
                    if (getLayout().getLineCount() <= nLastLine+1) {
                        return;
                    }
                    int start1 = getLayout().getLineStart(0);
                    int end1 = getLayout().getLineEnd(nLastLine -1);
                    int start2 = getLayout().getLineStart(nLastLine);
                    int end2 = getLayout().getLineEnd(nLastLine);
                    String displayed1 = getText().toString().substring(start1, end1);
                    String displayed2 = getText().toString().substring(start2, end2);

                    Rect tempRect = new Rect();
                    getDrawingRect(tempRect);
                    int measuredWidth = tempRect.width();

                    int realWidth = getTextViewWidth(displayed2+moreText);


                    while (measuredWidth < realWidth + MAGIC_LENGTH) {
                        if (displayed2.length() < MIN_CHAR_LENGTH + 1) {
                            break;
                        }
                        displayed2 = displayed2.substring(0,displayed2.length()-1);
                        realWidth = getTextViewWidth(displayed2+moreText);
                    }

                    setOriText(getText());
                    addMore(displayed1 + displayed2, moreText);
                }
            }
            int getTextViewWidth(String str) {
                Rect realSize = new Rect();
                getPaint().getTextBounds(str, 0, str.length(), realSize);
                return realSize.width();
            }
        });
    }

    private void addMore(final String text, final CharSequence readMore) {

        SpannableString ss = null;

        ss = new SpannableString(text.trim()+readMore);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                addLess(text, readMore);
            }
            @Override
            public void updateDrawState(TextPaint tp) {
                super.updateDrawState(tp);
                tp.setUnderlineText(false);
                tp.setColor(defaultSpanColor);

            }
        };
        ss.setSpan(clickableSpan, ss.length() - readMore.length(), ss.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(ss);
        setMovementMethod(LinkMovementMethod.getInstance());
        setInit(true);
    }
    private void addLess(final String text, final CharSequence readMore) {

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                addMore(text, readMore);
            }
            @Override
            public void updateDrawState(TextPaint tp) {
                super.updateDrawState(tp);
                tp.setUnderlineText(false);
                tp.setColor(defaultSpanColor);
            }
        };
        if(bLessButton == true) {
            SpannableString ss = new SpannableString(getOriText().toString() + lessText.toString());
            ss.setSpan(clickableSpan, ss.length() - lessText.length(), ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(ss);
        }
        else {
            setText(getOriText());
        }
        setMovementMethod(LinkMovementMethod.getInstance());
        setMoreState(true);
    }

}