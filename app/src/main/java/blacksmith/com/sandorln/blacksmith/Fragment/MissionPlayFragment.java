package blacksmith.com.sandorln.blacksmith.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import blacksmith.com.sandorln.blacksmith.Dto.mDto;
import blacksmith.com.sandorln.blacksmith.Dto.uDto;
import blacksmith.com.sandorln.blacksmith.MainActivity;
import blacksmith.com.sandorln.blacksmith.Play.Hit_auto;
import blacksmith.com.sandorln.blacksmith.Play.HpAddPlay;
import blacksmith.com.sandorln.blacksmith.R;
import blacksmith.com.sandorln.blacksmith.ani.Anvil_ani;
import blacksmith.com.sandorln.blacksmith.ani.Hit_ani;

/**
 * Created by SanDorln on 2016-08-10.
 */
public class MissionPlayFragment extends Fragment {

    ReentrantLock reentrantLock;

    public uDto userinfo;
    public mDto missioninfo;

    private TextView ItemHP_text;                   // 아이템 HP 표시되는 곳
    private TextView Item_Name;                     // 아이템 이름 및 강화 수치가 표시되는 곳
    private ImageView anvil;                        // 모루 이미지
    private ImageView weapon;                       // 무기 이미지
    private ImageView hit;                          // Hit_Effect 이미지

    private HpAddPlay hpAddPlay;         // 게임 시간 쓰레드
    private TextView play_time;                     // 게임 시간이 표시되는 곳

    MainActivity mainActivity;                      // 시간이 끝난 후 메인 엑티비티로 변경을 위한 객체

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View missionPlayView = inflater.inflate(R.layout.mission_play, container, false);

        Reset_Object(missionPlayView);
        Reset_View();

        // 게임 시작
        GamePlay_Start();
        return missionPlayView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (hpAddPlay != null) {
            hpAddPlay.Stop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (hpAddPlay != null) {
            hpAddPlay.Stop();
        }
    }

    private void Reset_Object(View missionPlayView) {
        // 객체 초기화
        reentrantLock = new ReentrantLock();

        ItemHP_text = (TextView) missionPlayView.findViewById(R.id.PlayItemHP);
        Item_Name = (TextView) missionPlayView.findViewById(R.id.PlayItemName);
        play_time = (TextView) missionPlayView.findViewById(R.id.PlayTime);
        LinearLayout gameTouch = (LinearLayout) missionPlayView.findViewById(R.id.Touch_layout);

        mainActivity = (MainActivity) getActivity();

        userinfo = MainActivity.userinfo;
        missioninfo = MainActivity.missioninfo;

        anvil = (ImageView) missionPlayView.findViewById(R.id.play_anvil);
        weapon = (ImageView) missionPlayView.findViewById(R.id.play_weapon);
        hit = (ImageView) missionPlayView.findViewById(R.id.play_hit);

        // 클릭 리스너 설정
        gameTouch.setOnTouchListener(Play_TouchListener);
    }

    private void Reset_View() {
        weapon.setImageResource(missioninfo.getWeapon_icon()); // 무기 이미지 셋팅
        ItemHP_text.setText(MainActivity.missioninfo.getItem_hp() + "");
        Item_Name.setText(missioninfo.getItem_name() + " + " + MainActivity.missioninfo.getItem_lv());
    }

    public void GamePlay_Start() {
        // 시간 조절
        hpAddPlay = new HpAddPlay(10, play_time, mainActivity); // 임무 시간 (1 = 1초)
        Hit_auto hit_auto = new Hit_auto(hpAddPlay, MainActivity.missionPlayFragment);
        hpAddPlay.start();
        hit_auto.start();
    }

    View.OnTouchListener Play_TouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.Touch_layout:
                    Item_HP_Add(userinfo.getuTouchLv());     // 터치로 인한 아이템 설정 변경
            }
            return false;
        }
    };

    public void Item_HP_Add(int damage) {
        // 아이템 내구도 증가
        try {
            reentrantLock.lock();
            // HIT 애니 시작
            Start_Ani();

            MainActivity.missioninfo.setItem_hp(MainActivity.missioninfo.getItem_hp() + damage);
            ItemHP_text.setText(MainActivity.missioninfo.getItem_hp() + "");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void Start_Ani() {
        // 모루 및 아이템 흔들리는 애니메이션
        Anvil_ani anvil_ani = new Anvil_ani(anvil, weapon);
        anvil_ani.start();

        // 타격 이펙트 애니메이션
        Hit_ani hit_ani = new Hit_ani(getResources(), hit);
        hit_ani.start();
    }

}