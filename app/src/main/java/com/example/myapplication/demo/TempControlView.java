package com.example.myapplication.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class TempControlView extends View {
  private float angleOne = 270.0F / (this.maxTemp - this.minTemp) / this.angleRate;
  
  private int angleRate = 4;
  
  private Paint arcPaint;
  
  private Paint arcPaint1;
  
  private Paint arcPaint2;
  
  private Paint arcPaint3;
  
  private int arcRadius;
  
  private Bitmap buttonImage = BitmapFactory.decodeResource(getResources(), 2130903040);
  
  private Bitmap buttonImageShadow = BitmapFactory.decodeResource(getResources(), 2130903041);
  
  private Paint buttonPaint;
  
  private double checkAngle;
  
  private float currentAngle;
  
  private Paint dialPaint;
  
  private int dialRadius;
  
  private int height;
  
  private boolean isDown;
  
  private boolean isMove;
  
  private int maxTemp = 30;
  
  private int minTemp = 15;
  
  private OnClickListener onClickListener;
  
  private OnTempChangeListener onTempChangeListener;
  
  private PaintFlagsDrawFilter paintFlagsDrawFilter;
  
  private float rotateAngle;
  
  private int scaleHeight = dp2px(10.0F);
  
  private Paint tempFlagPaint;
  
  private Paint tempPaint;
  
  private int temperature = 15;
  
  private double temperature2 = 15.0D;
  
  private String title = "Rdf Ble Temperature";
  
  private Paint titlePaint;
  
  private int width;
  
  public TempControlView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public TempControlView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public TempControlView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private void IncreaseAngle(float paramFloat) {
    this.rotateAngle += paramFloat;
    if (this.rotateAngle < 0.0F) {
      this.rotateAngle = 0.0F;
    } else if (this.rotateAngle > 270.0F) {
      this.rotateAngle = 270.0F;
    } 
    this.temperature = (int)((this.rotateAngle / this.angleOne / this.angleRate) + 0.5D) + this.minTemp;
  }
  
  private float calcAngle(float paramFloat1, float paramFloat2) {
    paramFloat1 -= (this.width / 2);
    paramFloat2 -= (this.height / 2);
    if (paramFloat1 != 0.0F) {
      float f = Math.abs(paramFloat2 / paramFloat1);
      if (paramFloat1 > 0.0F) {
        if (paramFloat2 >= 0.0F) {
          double d3 = Math.atan(f);
          return (float)(180.0D * d3 / Math.PI);
        } 
        double d2 = 6.283185307179586D - Math.atan(f);
        return (float)(180.0D * d2 / Math.PI);
      } 
      if (paramFloat2 >= 0.0F) {
        double d2 = Math.PI - Math.atan(f);
        return (float)(180.0D * d2 / Math.PI);
      } 
      double d1 = Math.PI + Math.atan(f);
      return (float)(180.0D * d1 / Math.PI);
    } 
    if (paramFloat2 > 0.0F) {
      double d1 = 1.5707963267948966D;
      return (float)(180.0D * d1 / Math.PI);
    } 
    double d = -1.5707963267948966D;
    return (float)(180.0D * d / Math.PI);
  }
  
  private void drawArc(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.translate((getWidth() / 2), (getHeight() / 2));
    paramCanvas.rotate(137.0F);
    RectF rectF = new RectF(-this.arcRadius, -this.arcRadius, this.arcRadius, this.arcRadius);
    if (this.checkAngle <= 37.0D) {
      paramCanvas.drawArc(rectF, 0.0F, 265.0F, false, this.arcPaint1);
    } else if (this.checkAngle <= 38.5D) {
      paramCanvas.drawArc(rectF, 0.0F, 265.0F, false, this.arcPaint2);
    } else {
      paramCanvas.drawArc(rectF, 0.0F, 265.0F, false, this.arcPaint3);
    } 
    paramCanvas.restore();
  }
  
  private void drawArc1(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.translate((getWidth() / 2), (getHeight() / 2));
    paramCanvas.rotate(137.0F);
    paramCanvas.drawArc(new RectF(-this.arcRadius, -this.arcRadius, this.arcRadius, this.arcRadius), 0.0F, 60.0F, false, this.arcPaint1);
    paramCanvas.restore();
  }
  
  private void drawArc2(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.translate((getWidth() / 2), (getHeight() / 2));
    paramCanvas.rotate(137.0F);
    paramCanvas.drawArc(new RectF(-this.arcRadius, -this.arcRadius, this.arcRadius, this.arcRadius), 61.0F, 200.0F, false, this.arcPaint2);
    paramCanvas.restore();
  }
  
  private void drawArc3(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.translate((getWidth() / 2), (getHeight() / 2));
    paramCanvas.rotate(137.0F);
    paramCanvas.drawArc(new RectF(-this.arcRadius, -this.arcRadius, this.arcRadius, this.arcRadius), 201.0F, 265.0F, false, this.arcPaint3);
    paramCanvas.restore();
  }
  
  private void drawButton(Canvas paramCanvas) {
    int i = this.buttonImage.getWidth();
    int j = this.buttonImage.getHeight();
    int k = this.buttonImageShadow.getWidth();
    int m = this.buttonImageShadow.getHeight();
    paramCanvas.drawBitmap(this.buttonImageShadow, ((this.width - k) / 2), ((this.height - m) / 2), this.buttonPaint);
    Matrix matrix = new Matrix();
    matrix.setTranslate(((this.width - i) / 2), ((this.height - j) / 2));
    matrix.postRotate(45.0F + this.rotateAngle, (this.width / 2), (this.height / 2));
    paramCanvas.setDrawFilter((DrawFilter)this.paintFlagsDrawFilter);
    paramCanvas.drawBitmap(this.buttonImage, matrix, this.buttonPaint);
  }

    private void drawScale(Canvas paramCanvas) {
        paramCanvas.save();
        paramCanvas.translate((getWidth() / 2), (getHeight() / 2));
        paramCanvas.rotate(-133.0F);
        this.dialPaint.setColor(Color.parseColor("#3CB7EA"));
        for (int i = 0;; i++) {
            if (i >= this.angleRate * (this.maxTemp - this.minTemp)) {
                paramCanvas.rotate(90.0F);
                this.dialPaint.setColor(Color.parseColor("#E37364"));
                for (i = 0;; i++) {
                    if (i >= (this.temperature - this.minTemp) * this.angleRate) {
                        paramCanvas.restore();
                        return;
                    }
                    paramCanvas.drawLine(0.0F, -this.dialRadius, 0.0F, (-this.dialRadius + this.scaleHeight), this.dialPaint);
                    paramCanvas.rotate(this.angleOne);
                }
            }
            paramCanvas.drawLine(0.0F, -this.dialRadius, 0.0F, (-this.dialRadius + this.scaleHeight), this.dialPaint);
            paramCanvas.rotate(this.angleOne);
        }
    }
  
  private void drawTemp(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.translate((getWidth() / 2), (getHeight() / 2));
    float f1 = this.tempPaint.measureText((new StringBuilder(String.valueOf(this.temperature2))).toString());
    float f2 = (this.tempPaint.ascent() + this.tempPaint.descent()) / 2.0F;
    paramCanvas.drawText(String.valueOf(this.temperature2) + "。", -f1 / 2.0F - dp2px(5.0F), -f2, this.tempPaint);



    paramCanvas.restore();
  }
  
  private void drawText(Canvas paramCanvas) {
    String str;
    paramCanvas.save();
    float f = this.titlePaint.measureText(this.title);
    paramCanvas.drawText(this.title, (this.width - f) / 2.0F, (this.dialRadius * 2 + dp2px(15.0F)), this.titlePaint);
    if (this.minTemp < 10) {
      str = "0" + this.minTemp;
    } else {
      str = (new StringBuilder(String.valueOf(this.minTemp))).toString();
    } 
    f = this.titlePaint.measureText((new StringBuilder(String.valueOf(this.maxTemp))).toString());
    paramCanvas.rotate(55.0F, (this.width / 2), (this.height / 2));
    paramCanvas.drawText(str, (this.width - f) / 2.0F, (this.height + dp2px(5.0F)), this.tempFlagPaint);
    paramCanvas.rotate(-105.0F, (this.width / 2), (this.height / 2));
    paramCanvas.drawText((new StringBuilder(String.valueOf(this.maxTemp))).toString(), (this.width - f) / 2.0F, (this.height + dp2px(5.0F)), this.tempFlagPaint);
    paramCanvas.restore();
  }
  
  private void init() {
    this.dialPaint = new Paint();
    this.dialPaint.setAntiAlias(true);
    this.dialPaint.setStrokeWidth(dp2px(2.0F));
    this.dialPaint.setStyle(Paint.Style.STROKE);
    this.arcPaint = new Paint();
    this.arcPaint.setAntiAlias(true);
    this.arcPaint.setColor(Color.parseColor("#3CB7EA"));
    this.arcPaint.setStrokeWidth(dp2px(10.0F));
    this.arcPaint.setStyle(Paint.Style.STROKE);
    this.arcPaint1 = new Paint();
    this.arcPaint1.setAntiAlias(true);
    this.arcPaint1.setColor(Color.parseColor("#00FF00"));
    this.arcPaint1.setStrokeWidth(dp2px(10.0F));
    this.arcPaint1.setStyle(Paint.Style.STROKE);
    this.arcPaint2 = new Paint();
    this.arcPaint2.setAntiAlias(true);
    this.arcPaint2.setColor(Color.parseColor("#FFFF00"));
    this.arcPaint2.setStrokeWidth(dp2px(10.0F));
    this.arcPaint2.setStyle(Paint.Style.STROKE);
    this.arcPaint3 = new Paint();
    this.arcPaint3.setAntiAlias(true);
    this.arcPaint3.setColor(Color.parseColor("#FF0000"));
    this.arcPaint3.setStrokeWidth(dp2px(10.0F));
    this.arcPaint3.setStyle(Paint.Style.STROKE);
    this.titlePaint = new Paint();
    this.titlePaint.setAntiAlias(true);
    this.titlePaint.setTextSize(sp2px(15.0F));
    this.titlePaint.setColor(Color.parseColor("#3B434E"));
    this.titlePaint.setStyle(Paint.Style.STROKE);
    this.tempFlagPaint = new Paint();
    this.tempFlagPaint.setAntiAlias(true);
    this.tempFlagPaint.setTextSize(sp2px(25.0F));
    this.tempFlagPaint.setColor(Color.parseColor("#E4A07E"));
    this.tempFlagPaint.setStyle(Paint.Style.STROKE);
    this.buttonPaint = new Paint();
    this.tempFlagPaint.setAntiAlias(true);
    this.paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
    this.tempPaint = new Paint();
    this.tempPaint.setAntiAlias(true);
    this.tempPaint.setTextSize(sp2px(40.0F));
    this.tempPaint.setColor(Color.parseColor("#E27A3F"));
    this.tempPaint.setStyle(Paint.Style.STROKE);
    this.checkAngle = 0.0D;
  }
  
  private int sp2px(float paramFloat) {
    return (int)TypedValue.applyDimension(2, paramFloat, getResources().getDisplayMetrics());
  }
  
  public int dp2px(float paramFloat) {
    return (int)TypedValue.applyDimension(1, paramFloat, getResources().getDisplayMetrics());
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    drawArc(paramCanvas);
    drawText(paramCanvas);
    drawButton(paramCanvas);
    drawTemp(paramCanvas);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    paramInt1 = Math.min(paramInt2, paramInt1);
    this.height = paramInt1;
    this.width = paramInt1;
    this.dialRadius = this.width / 2 - dp2px(20.0F);
    this.arcRadius = this.dialRadius - dp2px(20.0F);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
      float f1;
      float f2;
      float f3;
      switch (paramMotionEvent.getAction()) {
          default:
              return true;
          case 0:
              this.isDown = true;
              this.currentAngle = calcAngle(paramMotionEvent.getX(), paramMotionEvent.getY());
              return true;
          case 2:
              this.isMove = true;
              f3 = calcAngle(paramMotionEvent.getX(), paramMotionEvent.getY());
              f2 = f3 - this.currentAngle;
              if (f2 < -270.0F) {
                  float f = f2 + 360.0F;
                  IncreaseAngle(f);
                  this.currentAngle = f3;
                  invalidate();
                  return true;
              }
              f1 = f2;
              if (f2 > 270.0F)
                  f1 = f2 - 360.0F;
              IncreaseAngle(f1);
              this.currentAngle = f3;
              invalidate();
              return true;
          case 1:
          case 3:
              break;
      }
      if (this.isDown) {
          if (this.isMove) {
              this.rotateAngle = ((this.temperature - this.minTemp) * this.angleRate) * this.angleOne;
              invalidate();
              if (this.onTempChangeListener != null)
                  this.onTempChangeListener.change(this.temperature);
              this.isMove = false;
              this.isDown = false;
              return true;
          }
          if (this.onClickListener != null)
              this.onClickListener.onClick(this.temperature);
          this.isDown = false;
          return true;
      }

      return true;
  }
  
  public void setAngleRate(int paramInt) {
    this.angleRate = paramInt;
  }
  
  public void setOnClickListener(OnClickListener paramOnClickListener) {
    this.onClickListener = paramOnClickListener;
  }
  
  public void setOnTempChangeListener(OnTempChangeListener paramOnTempChangeListener) {
    this.onTempChangeListener = paramOnTempChangeListener;
  }
  
  public void setTemp(float paramFloat) {
    setTemp(this.minTemp, this.maxTemp, paramFloat);
  }
  
  public void setTemp(int paramInt1, int paramInt2, double paramDouble) {
    this.minTemp = paramInt1;
    this.maxTemp = paramInt2;
    if (paramDouble < paramInt1) {
      this.temperature = paramInt1;
    } else {
      this.temperature = (int)paramDouble;
      this.temperature2 = paramDouble;
    } 
    this.checkAngle = paramDouble;
    this.rotateAngle = (float)((paramDouble - paramInt1) * this.angleRate * this.angleOne);
    this.angleOne = 270.0F / (paramInt2 - paramInt1) / this.angleRate;
    invalidate();
  }
  
  public static interface OnClickListener {
    void onClick(int param1Int);
  }
  
  public static interface OnTempChangeListener {
    void change(int param1Int);
  }
}
