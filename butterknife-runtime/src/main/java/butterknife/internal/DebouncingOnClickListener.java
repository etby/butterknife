package butterknife.internal;

import android.view.View;

/**
 * A {@linkplain View.OnClickListener click listener} that debounces multiple clicks posted in the
 * same frame. A click on one button disables all buttons for that frame.
 */
public abstract class DebouncingOnClickListener implements View.OnClickListener {
  static boolean enabled = true;

  public static final long DEFAULT_DELAY_TIME = 200;
  /**
   * Interval between two clicks.
   */
  private static long mDelayTime = DEFAULT_DELAY_TIME;
  /**
   * Last Clicked Time
   */
  private long mLastTime = 0L;

  private static final Runnable ENABLE_AGAIN = () -> enabled = true;

  @Override public final void onClick(View v) {
    if (enabled) {
      enabled = false;
      v.post(ENABLE_AGAIN);
      performClick(v);
    }
  }

  public abstract void doClick(View v);

  /**
   * Call doClick over delay time.
   */
  private void performClick(View v) {
    if (System.currentTimeMillis() - mLastTime > mDelayTime) {
      doClick(v);
    }
    mLastTime = System.currentTimeMillis();
  }

  /**
   * Set time of between two clicks.
   * @param delayTime MilliSeconds
   */
  public static void setDelayTime(long delayTime) {
    mDelayTime = delayTime;
  }
}
