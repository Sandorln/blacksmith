package blacksmith.com.sandorln.blacksmith.Play;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import blacksmith.com.sandorln.blacksmith.MainActivity;

/**
 * Created by SanDorln on 2016-08-29.
 */
public class UpgradePlay extends Thread {
    public boolean isPlaying = true;

    int width;
    int checkwidth;
    int line_move;
    int line_speed;

    ImageView upgrade_check;
    Handler handler;

    public UpgradePlay(int width, ImageView upgrade_check, int line_speed) {
        this.width = width;
        this.upgrade_check = upgrade_check;
        this.line_speed = line_speed;

        checkwidth = width / 30;
        handler = new Handler();
        line_move = width / 100;

        upgrade_check.setVisibility(View.VISIBLE);
        upgrade_check.setX(checkwidth);
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {

                if (isPlaying) {
                    checkwidth += line_move;
                    sleep(line_speed);
                }

                final int finalCnt = checkwidth;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        upgrade_check.setX(finalCnt);
                    }
                });

                if (checkwidth >= width - width / 30 || !isPlaying) {
                    break;
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handler.post(new Runnable() {
                         @Override
                         public void run() {
                             if (checkwidth >= width - width / 30) {
                                 upgrade_check.setX(width / 30);
                             } else {
                                 upgrade_check.setX(checkwidth);
                             }
                             MainActivity.missionUpFragment.setViewVisibility(1);
                         }
                     }
        );
    }

    public int GetX() {
        isPlaying = !isPlaying;
        return checkwidth;
    }
}
