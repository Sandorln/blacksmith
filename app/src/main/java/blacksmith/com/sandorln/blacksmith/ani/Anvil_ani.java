package blacksmith.com.sandorln.blacksmith.ani;

import android.os.Handler;
import android.widget.ImageView;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by SanDorln on 2016-08-21.
 */
public class Anvil_ani extends Thread {
    ImageView anvil;
    ImageView weapon;
    Handler handler;

    boolean step = true;
    ReentrantLock reentrantLock;

    public Anvil_ani(ImageView anvil, ImageView weapon) {
        this.anvil = anvil;
        this.weapon = weapon;
        handler = new Handler();
        reentrantLock = new ReentrantLock();
    }

    @Override
    public void run() {
        try {
            super.run();
            int cnt = 1;
            while (cnt <= 4) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (step) {
                            anvil.setX(3.0f);
                            weapon.setX(12.0f);
                        } else {
                            anvil.setX(-3.0f);
                            weapon.setX(-12.0f);
                        }
                    }
                });
                sleep(60);
                cnt++;
                step = !step;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
