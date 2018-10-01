package blacksmith.com.sandorln.blacksmith.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import blacksmith.com.sandorln.blacksmith.Dto.nDto;
import blacksmith.com.sandorln.blacksmith.MainActivity;
import blacksmith.com.sandorln.blacksmith.R;

/**
 * Created by SanDorln on 2016-08-10.
 */
public class MissionFragment extends Fragment {

    Animation NpcReset_ani;
    Animation NpcReset_ani_layout;

    TextView mission_Sell_Gold;

    ImageView btn_Ok;
    ImageView btn_No;

    ImageView Npc_image;    // 메인 화면 NPC 이미지
    ImageView Npc_icon;     // 대화 화면 NPC Icon
    TextView Npc_talk;      // 대화 화면 NPC 고유 대화

    RelativeLayout NPC_Layout;
    RelativeLayout Mission_Layout;

    // 만들어 낸 물건 보여주는 화면 객체
    RelativeLayout MadeWeapon_Layout;
    ImageView MadeWeapon_image;
    TextView MadeWeapon_name;
    private Animation MadeLayout_ani;

    ImageView npcType_purpose_icon; // npc 타입에 맞춘 아이콘 등장

    MainActivity mainActivity;
    View missionView;

    public int cnt = 0;     // 현재 화면 초기화 상태인지 구분
    public nDto temp_npc;

    private TextView gold_text;          // 현재 GOLD 량

    private RelativeLayout missionClear_layout;   // Mission clear 시 등장할 레이아웃
    private TextView missionClear_gold;           // Mission gold 획득

    private Animation npcExit_ani;
    private Animation npcCome_ani;
    private Animation gameclear_ani;
    private Animation gameclear_view_ani;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        missionView = inflater.inflate(R.layout.mission, container, false);

        // 객체 초기화
        Reset_Object();

        // NPC 초기화
        Reset_Npc();

        // 화면 셋팅
        Reset_View();

        return missionView;
    }

    private void Reset_Object() {
        cnt = 0;

        mission_Sell_Gold = (TextView) missionView.findViewById(R.id.M_Sell_Gold);

        NPC_Layout = (RelativeLayout) missionView.findViewById(R.id.Minfo_NPC_Layout);
        Mission_Layout = (RelativeLayout) missionView.findViewById(R.id.Minfo_info_Layout);

        btn_No = (ImageView) missionView.findViewById(R.id.btn_No);
        btn_Ok = (ImageView) missionView.findViewById(R.id.btn_Ok);

        mainActivity = (MainActivity) getActivity();

        btn_No.setOnClickListener(clickListener);
        btn_Ok.setOnClickListener(clickListener);

        Npc_icon = (ImageView) missionView.findViewById(R.id.M_NPC_icon);
        Npc_talk = (TextView) missionView.findViewById(R.id.M_NPC_text);
        Npc_image = (ImageView) mainActivity.findViewById(R.id.M_NPC);

        gold_text = (TextView) mainActivity.findViewById(R.id.M_Gold);

        missionClear_layout = (RelativeLayout) mainActivity.findViewById(R.id.M_MissionClear_layout);
        missionClear_gold = (TextView) mainActivity.findViewById(R.id.M_MissionClear_gold);

        MadeWeapon_Layout = (RelativeLayout) mainActivity.findViewById(R.id.M_Made_layout);
        MadeWeapon_image = (ImageView) mainActivity.findViewById(R.id.M_Made_layout_weapon);
        MadeWeapon_name = (TextView) mainActivity.findViewById(R.id.M_Made_name);

        npcType_purpose_icon = (ImageView) missionView.findViewById(R.id.M_Sell_purpose_image);

        // 애니메이션
        NpcReset_ani = AnimationUtils.loadAnimation(getContext(), R.anim.mission_reset);
        NpcReset_ani_layout = AnimationUtils.loadAnimation(getContext(), R.anim.mission_reset_layout);
        npcExit_ani = AnimationUtils.loadAnimation(getContext(), R.anim.npc_exit);
        npcCome_ani = AnimationUtils.loadAnimation(getContext(), R.anim.npc_come);

        gameclear_ani = AnimationUtils.loadAnimation(getContext(), R.anim.gameclear_ani);
        gameclear_view_ani = AnimationUtils.loadAnimation(getContext(), R.anim.gameclear_view_ani);

        MadeLayout_ani = AnimationUtils.loadAnimation(getContext(), R.anim.madelayout_move);
    }

    private void Reset_View() {
        String resName;
        int icon;

        if (temp_npc.getnType() == 1) {
            resName = "@drawable/dungeon_icon";
            icon = getResources().getIdentifier(resName, "drawable", mainActivity.getPackageName());
        } else {
            resName = "@drawable/goldpocket_icon";
            icon = getResources().getIdentifier(resName, "drawable", mainActivity.getPackageName());
        }

        npcType_purpose_icon.setImageResource(icon);

        MadeWeapon_Layout.setVisibility(View.VISIBLE);
        MadeWeapon_image.setImageResource(MainActivity.missioninfo.getWeapon_icon());
        MadeWeapon_name.setText(MainActivity.missioninfo.getItem_name() + "+" + MainActivity.missioninfo.getItem_lv());

        MadeWeapon_Layout.startAnimation(MadeLayout_ani);

        btn_Ok.setVisibility(View.INVISIBLE);
        btn_No.setVisibility(View.INVISIBLE);

        // 미션 관련 설정
        if (temp_npc.getnType() != 1) {
            mission_Sell_Gold.setText(MainActivity.missioninfo.getClear_gold() + " G ");
        } else {
            mission_Sell_Gold.setText("던전 진행률 상승");
        }
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_Ok:
                    Bye_npc();
                    GameClear();
                    break;
                case R.id.btn_No:
                    if (cnt < MainActivity.buyerinfo.size() - 1) {
                        cnt++;
                        Reset_Npc();
                        Reset_View();
                    } else {
                        Toast.makeText(getContext(), "더 이상 사람이 없습니다", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void Reset_Npc() {
        temp_npc = MainActivity.buyerinfo.get(cnt);

        // NPC Icon 및 대화 설정
        Npc_icon.setImageResource(temp_npc.getnIcon());
        Npc_talk.setText(temp_npc.getnTalk());

        // 메인 화면 변경 값
        // NPC 화면 애니메이션
        NPC_Layout.startAnimation(NpcReset_ani);
        Mission_Layout.startAnimation(NpcReset_ani_layout);
        if (cnt != 0)                                               // 맨 처음 화면일 시 캐릭터는 퇴장하지 않음
            Npc_image.startAnimation(npcExit_ani);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Npc_image.setImageResource(temp_npc.getnCharacter());
                Npc_image.startAnimation(npcCome_ani);
                Npc_image.setVisibility(View.VISIBLE);
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btn_Ok.setVisibility(View.VISIBLE);
                btn_No.setVisibility(View.VISIBLE);
            }
        }, 800);
    }

    private void Bye_npc() {

        cnt = 0;
        Npc_image.startAnimation(npcExit_ani);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Npc_image.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }

    public void GameClear() {
        MadeWeapon_Layout.setVisibility(View.INVISIBLE);

        // Gold 획득 애니메이션
        gold_text.setAnimation(gameclear_ani);

        // 제작된 무기 이미지 사라지는 애니메이션
        MadeWeapon_Layout.startAnimation(npcExit_ani);

        missionClear_gold.setText(MainActivity.missioninfo.getClear_gold() + "G");

        missionClear_layout.startAnimation(gameclear_view_ani); // 뷰 애니메이션

        MainActivity.userinfo.setuGold(MainActivity.userinfo.getuGold() + MainActivity.missioninfo.getClear_gold());

        MadeLayout_ani.cancel();

        mainActivity.Reset_View();
        mainActivity.View_Change(MainActivity.MODE_LIST_MISSION);
    }
}