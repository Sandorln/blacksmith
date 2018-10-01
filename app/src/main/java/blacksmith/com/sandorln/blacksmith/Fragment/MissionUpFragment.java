package blacksmith.com.sandorln.blacksmith.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import blacksmith.com.sandorln.blacksmith.Dto.upDto;
import blacksmith.com.sandorln.blacksmith.MainActivity;
import blacksmith.com.sandorln.blacksmith.Play.UpgradePlay;
import blacksmith.com.sandorln.blacksmith.R;
import blacksmith.com.sandorln.blacksmith.View.UpStateBarView;

/**
 * Created by SanDorln on 2016-08-29.
 */
public class MissionUpFragment extends Fragment {

    View missionUpView;
    MainActivity mainActivity;

    UpStateBarView upStateBarView;
    upDto upinfo;

    TextView Item_name_T;
    TextView Item_hp_T;
    TextView Item_hp_Sub;
    ImageView Item_Weapon;
    ImageView Upgrade_check;
    ImageView Upgrade_text;
    ImageView Upgrade_effect;

    LinearLayout Stop_layout;
    LinearLayout Go_layout;
    LinearLayout Up_layout;
    LinearLayout StateBar_layout;

    UpgradePlay upgradePlay;

    int Great_image;
    int Good_image;

    Animation text_ani;
    Animation upgrade_effect_ani;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        missionUpView = inflater.inflate(R.layout.mission_upgrade, container, false);
        mainActivity = (MainActivity) getActivity();

        Reset_Object();
        Reset_View();

        return missionUpView;
    }

    private void Reset_Object() {
        upinfo = new upDto();

        Item_hp_T = (TextView) missionUpView.findViewById(R.id.Up_ItemHp);
        Item_name_T = (TextView) missionUpView.findViewById(R.id.Up_ItemName);
        Item_hp_Sub = (TextView) missionUpView.findViewById(R.id.Up_Item_Sub);

        Stop_layout = (LinearLayout) missionUpView.findViewById(R.id.Up_Stop_layout);
        Go_layout = (LinearLayout) missionUpView.findViewById(R.id.Up_Go_layout);
        Up_layout = (LinearLayout) missionUpView.findViewById(R.id.Up_Upgrade_layout);

        StateBar_layout = (LinearLayout) missionUpView.findViewById(R.id.Up_StateBar_layout);
        upStateBarView = new UpStateBarView(StateBar_layout.getContext(), upinfo);
        StateBar_layout.addView(upStateBarView);

        Item_Weapon = (ImageView) missionUpView.findViewById(R.id.Up_weapon);
        Upgrade_check = (ImageView) missionUpView.findViewById(R.id.Up_StateBar_check);
        Upgrade_text = (ImageView) mainActivity.findViewById(R.id.M_Upgrade_text);
        Upgrade_effect = (ImageView) missionUpView.findViewById(R.id.Up_effect);

        Go_layout.setOnTouchListener(btn_Touch);
        Stop_layout.setOnTouchListener(btn_Touch);
        Up_layout.setOnTouchListener(btn_Touch);

        Go_layout.setOnClickListener(btn_click);
        Stop_layout.setOnClickListener(btn_click);
        Up_layout.setOnClickListener(btn_click);

        String resName = "@drawable/great_text";
        Great_image = getResources().getIdentifier(resName, "drawable", mainActivity.getPackageName());

        resName = "@drawable/good_text";
        Good_image = getResources().getIdentifier(resName, "drawable", mainActivity.getPackageName());

        text_ani = AnimationUtils.loadAnimation(getContext(), R.anim.uptext_ani);
        upgrade_effect_ani = AnimationUtils.loadAnimation(getContext(), R.anim.upgrade_effect);
    }

    private void Reset_View() {
        if (MainActivity.missioninfo.getItem_hp() <= 0) {
            Go_layout.setBackgroundColor(0xFF727272);
        }

        Item_hp_Sub.setText("내구도 - " + MainActivity.missioninfo.getItem_hp_sub());
        Item_hp_T.setText(MainActivity.missioninfo.getItem_hp() + "");
        Item_name_T.setText(MainActivity.missioninfo.getItem_name() + " + " + MainActivity.missioninfo.getItem_lv());
        Item_Weapon.setImageResource(MainActivity.missioninfo.getWeapon_icon());

        //Upgrade_check.setVisibility(View.INVISIBLE);
    }

    View.OnTouchListener btn_Touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    Go_layout.setBackgroundColor(0xff68b9ff);
                    Stop_layout.setBackgroundColor(0xffff2b2b);
                    if (MainActivity.missioninfo.getItem_hp() <= 0) {
                        Go_layout.setBackgroundColor(0xFF727272);
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    switch (v.getId()) {
                        case R.id.Up_Go_layout:
                            if (MainActivity.missioninfo.getItem_hp() <= 0) {
                                Go_layout.setBackgroundColor(0xFF727272);
                            } else {
                                Go_layout.setBackgroundColor(0xff2f628e);
                            }
                            break;
                        case R.id.Up_Stop_layout:
                            Stop_layout.setBackgroundColor(0xff911c1c);
                            break;
                        case R.id.Up_Upgrade_layout:
                            int upgradeX = upgradePlay.GetX();
                            UpgradeCheck(upgradeX);
                            break;
                    }
                    break;
            }
            return false;
        }
    };

    View.OnClickListener btn_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Up_Go_layout:
                    if (MainActivity.missioninfo.getItem_hp() > 0) {
                        setViewVisibility(0);
                        upStateBarView.invalidate();
                        upgradePlay = new UpgradePlay(upStateBarView.getWidth(), Upgrade_check, (int) MainActivity.missioninfo.getLine_speed());
                        upgradePlay.start();

                        // 아이템 내구도 감소
                        MainActivity.missioninfo.setItem_hp(MainActivity.missioninfo.getItem_hp() - MainActivity.missioninfo.getItem_hp_sub() > 0 ? MainActivity.missioninfo.getItem_hp() - MainActivity.missioninfo.getItem_hp_sub() : 0);

                        // 속도 체크
                        MainActivity.missioninfo.setLine_speed(MainActivity.missioninfo.getLine_speed() > 5 ? MainActivity.missioninfo.getLine_speed() - 0.2f : 5);

                        Item_hp_T.setText(MainActivity.missioninfo.getItem_hp() + "");
                    }
                    break;
                case R.id.Up_Stop_layout:

                    // 구매자들이 등장하는 화면으로 이동
                    MainActivity.missioninfo.setClear_gold((int) (MainActivity.missioninfo.getClear_gold() * (MainActivity.missioninfo.getItem_lv() * 0.1 + 1)));
                    mainActivity.View_Change(MainActivity.MODE_VIEW_MISSION);
                    break;
            }
        }
    };

    private void UpgradeCheck(int upgradeX) {
        Random random = new Random();
        random.setSeed(random.nextInt());

        if (upinfo.getMinGreatX() <= upgradeX && upgradeX <= upinfo.getMaxGreatX()) {
            MainActivity.missioninfo.setItem_lv(MainActivity.missioninfo.getItem_lv() + random.nextInt(2) + 2);
            Upgrade_text.setImageResource(Great_image);
            Upgrade_text.startAnimation(text_ani);
            Upgrade_effect.startAnimation(upgrade_effect_ani);
        } else if (upinfo.getMinNormalX() <= upgradeX && upgradeX <= upinfo.getMaxNormalX()) {
            MainActivity.missioninfo.setItem_lv(MainActivity.missioninfo.getItem_lv() + 1);
            Upgrade_text.setImageResource(Good_image);
            Upgrade_text.startAnimation(text_ani);
            Upgrade_effect.startAnimation(upgrade_effect_ani);
        }

        int itemnum = MainActivity.missioninfo.getItem_id();
        MainActivity.missioninfo.setItem_hp_sub((MainActivity.iteminfo.get(itemnum).getiHp_Sub() * MainActivity.missioninfo.getItem_lv() * 4));

        Reset_View();
    }

    public void setViewVisibility(int value) {
        switch (value) {
            case 0:
                Go_layout.setVisibility(View.INVISIBLE);
                Stop_layout.setVisibility(View.INVISIBLE);
                Up_layout.setVisibility(View.VISIBLE);
                break;
            case 1:
                Go_layout.setVisibility(View.VISIBLE);
                Stop_layout.setVisibility(View.VISIBLE);
                Up_layout.setVisibility(View.INVISIBLE);
                Reset_View();
                break;
        }
    }
}