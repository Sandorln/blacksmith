package blacksmith.com.sandorln.blacksmith.ani;

import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by SanDorln on 2016-08-21.
 */
public class Hit_ani extends Thread {
    final String packName = "blacksmith.com.sandorln.blacksmith";
    Resources resources;
    ImageView hit;
    Random random;
    Handler handler;

    private int X;
    private int Y;

    int cnt;

    public Hit_ani(Resources resources, ImageView hit) {
        this.resources = resources;
        this.hit = hit;
        random = new Random();
        handler = new Handler();

        random.setSeed(random.nextInt());

        X = random.nextInt(500);
        Y = random.nextInt(120);
    }

    @Override
    public void run() {
        super.run();
        try {
            while (cnt < 2) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String resName = "@drawable/hit_effect_" + cnt;
                        int effect_Num = resources.getIdentifier(resName, "drawable", packName);

                        hit.setImageResource(effect_Num);

//                        hit.setX(500);
//                        hit.setY(120);
                        hit.setX(X);
                        hit.setY(Y);
                        hit.setVisibility(View.VISIBLE);
                    }
                });
                sleep(100);
                cnt++;
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    hit.setVisibility(View.INVISIBLE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
