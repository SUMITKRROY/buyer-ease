package com.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.buyereasefsl.R;


public class TimerHandler {

    boolean mIsCountDownTimer = false;
    int mCountDownTimer = 0;
    int mCountDownInterval = 0;
    int mTotalTimeDelay = 0;

    Context mContext = null;
    Button mButtonToHandle = null;
    TextView mDisplayMsgView = null;

    private Handler mDelayHandler = null;
    private Runnable mDelayRunnable = null;
    private TimerExpiryCallBackListener mListner = null;

    //Use this method only for handling change in resend button behaviour
    public void configResendCountDownTimer(Context pContext
                            ,int pCountDownInterval
                            ,int pTotalTimeDelay
                            ,Button pButtonToHandle
                            ,TextView pDisplayMsgView) {
        if (mDelayHandler != null)
            mDelayHandler.removeCallbacks(mDelayRunnable);

        mDelayHandler = new Handler();
        mIsCountDownTimer = true;
        mCountDownInterval = pCountDownInterval;
        mTotalTimeDelay = pTotalTimeDelay;
        mButtonToHandle = pButtonToHandle;
        mDisplayMsgView = pDisplayMsgView;
        mContext = pContext;

        mDelayRunnable = new Runnable() {
            public void run() {
                postDelayResendButtonHandling();
            }
        };

    }

    public boolean startResendCountDownTimer() {
        if (mDelayHandler != null
                && mDelayRunnable != null
                && mContext != null
                && mButtonToHandle != null
                && mCountDownInterval > 0
                && mTotalTimeDelay > 0) {
            mDelayHandler.removeCallbacks(mDelayRunnable);
            mButtonToHandle.setEnabled(false);
            mDelayHandler.postDelayed(mDelayRunnable, 1000*mCountDownInterval);
            if (mDisplayMsgView != null)
                mDisplayMsgView.setText(mContext.getResources().getString(R.string.resendSMSMsg1)
                                    + " " + (mTotalTimeDelay - mCountDownTimer) + " "
                                    + mContext.getResources().getString(R.string.resendSMSMsg2));
            return true;
        } else {
            return false;
        }
    }

    public boolean stopResendCountDownTimer() {
        if (mDelayHandler != null) {
            mDelayHandler.removeCallbacks(mDelayRunnable);
            return true;
        } else {
            return false;
        }
    }

    private void postDelayResendButtonHandling() {
        mCountDownTimer += mCountDownInterval;
        if (mCountDownTimer == mTotalTimeDelay) {
            mCountDownTimer = 0;
            mButtonToHandle.setEnabled(true);
            if (mDisplayMsgView != null)
                mDisplayMsgView.setText("");
        } else {
            mDelayHandler.postDelayed(mDelayRunnable, 1000*mCountDownInterval);
            if (mDisplayMsgView != null)
                mDisplayMsgView.setText(mContext.getResources().getString(R.string.resendSMSMsg1)
                        + " " + (mTotalTimeDelay - mCountDownTimer) + " "
                        + mContext.getResources().getString(R.string.resendSMSMsg2));
        }
    }

    //To run timer only once make CountDownInterval & TotalTimeDelay same value
    public boolean startTimer(int pCountDownInterval
            ,int pTotalTimeDelay
            ,TimerExpiryCallBackListener pListner) {
        if ( pListner != null
                && pCountDownInterval > 0
                && pTotalTimeDelay > 0) {

            mDelayHandler = new Handler();
            mCountDownInterval = pCountDownInterval;
            mTotalTimeDelay = pTotalTimeDelay;
            mListner = pListner;

            mDelayRunnable = new Runnable() {
                public void run() {
                    postDelayHandling();
                }
            };

            mDelayHandler.postDelayed(mDelayRunnable, 1000*mCountDownInterval);

            return true;
        } else {
            return false;
        }
    }

    public boolean stopTimer() {
        if (mDelayHandler != null) {
            mDelayHandler.removeCallbacks(mDelayRunnable);
            return true;
        } else {
            return false;
        }
    }

    private void postDelayHandling() {
        mCountDownTimer += mCountDownInterval;
        if (mCountDownTimer == mTotalTimeDelay) {
            mCountDownTimer = 0;
            mListner.onTimerExpiry();
        } else {
            mDelayHandler.postDelayed(mDelayRunnable, 1000*mCountDownInterval);
        }
    }

    public interface TimerExpiryCallBackListener{
        void onTimerExpiry();
    }

}
