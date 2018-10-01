package blacksmith.com.sandorln.blacksmith.Play;

import android.os.Handler;
import android.widget.TextView;

import blacksmith.com.sandorln.blacksmith.MainActivity;

/**
 * Created by SanDorln on 2016-08-10.
 */
public class HpAddPlay extends Thread {

    public boolean isPlaying = true;

    Handler handler;

    private int playTime;                   // 남은 시간
    private int realTime;                   // 실제 시간

    private TextView playTime_text;        // 남은 시간 표시
    private MainActivity mainActivity;      // MainActivity 객체

    public HpAddPlay(int playTime, TextView playTime_text, MainActivity mainActivity) {
        this.playTime = playTime;
        this.playTime_text = playTime_text;
        this.mainActivity = mainActivity;
        handler = new Handler();
    }

    @Override
    public void run() {
        realTime = 0;
        playTime_text.setText(playTime + "");
        isPlaying = true;

        try {
            while (realTime <= playTime) {
                if (!isPlaying) {
                    break;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        playTime_text.setText((playTime - realTime) + "");
                    }
                });

                sleep(1000);
                realTime++;
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (isPlaying && realTime > playTime) {
                        mainActivity.View_Change(MainActivity.MODE_UP_MISSION);
                    }
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Stop() {
        isPlaying = false;
    }
}
