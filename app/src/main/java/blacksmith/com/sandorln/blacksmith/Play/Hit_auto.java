package blacksmith.com.sandorln.blacksmith.Play;

import android.os.Handler;

import blacksmith.com.sandorln.blacksmith.Fragment.MissionPlayFragment;
import blacksmith.com.sandorln.blacksmith.MainActivity;

/**
 * Created by SanDorln on 2016-08-26.
 */
public class Hit_auto extends Thread {
    HpAddPlay hpAddPlay;
    MissionPlayFragment missionPlayFragment;
    Handler handler;

    public Hit_auto(HpAddPlay hpAddPlay, MissionPlayFragment missionPlayFragment) {
        this.hpAddPlay = hpAddPlay;
        this.missionPlayFragment = missionPlayFragment;
        handler = new Handler();
    }

    @Override
    public void run() {
        super.run();

        try {

            while (true) {
                if (hpAddPlay.isPlaying) {
                    if (MainActivity.characterinfo.get(0).getcBuy() == 1) {         // 스킬 0번을 보유하고 있을 시
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.missionPlayFragment.Item_HP_Add(MainActivity.characterinfo.get(0).getcLv());
                            }
                        });
                    }
                } else {
                    break;
                }
                sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
