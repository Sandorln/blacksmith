package blacksmith.com.sandorln.blacksmith.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import blacksmith.com.sandorln.blacksmith.Dto.iDto;
import blacksmith.com.sandorln.blacksmith.Dto.mDto;
import blacksmith.com.sandorln.blacksmith.Dto.nDto;
import blacksmith.com.sandorln.blacksmith.MainActivity;
import blacksmith.com.sandorln.blacksmith.R;

/**
 * Created by SanDorln on 2016-08-30.
 */
public class MissionListFragment extends Fragment {

    View missionlistView;
    LinearLayout itemlist;
    LayoutInflater inflater;
    ViewGroup container;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        missionlistView = inflater.inflate(R.layout.mission_list, container, false);
        itemlist = (LinearLayout) missionlistView.findViewById(R.id.M_itemlist);
        mainActivity = (MainActivity) getActivity();

        this.inflater = inflater;
        this.container = container;

        Reset_itemlist();

        return missionlistView;
    }

    public void Reset_itemlist() {
        try {

            if (itemlist != null)
                itemlist.removeAllViews();

            if (inflater != null) {
                for (final iDto value : MainActivity.iteminfo) {
                    View itemvalue = inflater.inflate(R.layout.mission_list_value, container, false);

                    // 화면 객체 초기화
                    ImageView item_icon = (ImageView) itemvalue.findViewById(R.id.M_list_value_icon);

                    TextView item_plus = (TextView) itemvalue.findViewById(R.id.M_list_value_plus);
                    TextView item_name = (TextView) itemvalue.findViewById(R.id.M_list_value_name);
                    TextView item_madeG = (TextView) itemvalue.findViewById(R.id.M_list_value_MadeG);

                    final LinearLayout Made_layout = (LinearLayout) itemvalue.findViewById(R.id.M_list_value_Madelayout);
                    LinearLayout BuyNo_layout = (LinearLayout) itemvalue.findViewById(R.id.M_list_value_buyNo);

                    // 제작 비 설정
                    final int madeGold = value.getiMadeMoney() * value.getiLv() + value.getiMadeMoney();

                    // 화면 객체에 값 설정
                    item_icon.setImageResource(value.getiIcon());
                    item_plus.setText(" + " + value.getiLv());
                    item_name.setText(value.getiName());
                    item_madeG.setText(madeGold + " G ");


                    if (value.getiBuy() != 1) {
                        BuyNo_layout.setVisibility(View.VISIBLE);
                    } else {
                        Made_layout.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        Made_layout.setBackgroundColor(0xFF9C6A00);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        Made_layout.setBackgroundColor(0xffffcc5f);
                                        break;
                                }
                                return false;
                            }
                        });

                        Made_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (madeGold <= MainActivity.userinfo.getuGold()) {
                                    // 제작관련 설정 값 초기화
                                    Reset_Mission(value, madeGold);

                                    // 구매자 관련 설정 값 초기화
                                    Reset_buyer();

                                    mainActivity.Reset_View();
                                    mainActivity.View_Change(MainActivity.MODE_PLAY_MISSION);
                                } else {
                                    Toast.makeText(getContext(), "제작 비용이 부족합니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    itemlist.addView(itemvalue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Reset_Mission(iDto value, int madeGold) {
        MainActivity.userinfo.setuGold(MainActivity.userinfo.getuGold() - madeGold);

        MainActivity.missioninfo = new mDto();

        // 무작위로 선택된 아이템 고유 번호 저장
        MainActivity.missioninfo.setItem_id(value.getiId());

        // 아이템 아이콘 설정
        MainActivity.missioninfo.setWeapon_icon(value.getiIcon());

        // 아이템 이름 설정
        MainActivity.missioninfo.setItem_name(value.getiName());

        // 아이템 체력 무작위 설정
        MainActivity.missioninfo.setItem_hp(value.getiHp());

        // 아이템 강화 레벨 설정
        MainActivity.missioninfo.setItem_lv(value.getiLv());

        // 아이템 강화 시 내구도 감소
        MainActivity.missioninfo.setItem_hp_sub(value.getiHp_Sub());

        // 미션 성공 시 획득 골드 설정
        MainActivity.missioninfo.setClear_gold(value.getiSellMoney());

        // 강화 게이지 이동 속도 설정
        MainActivity.missioninfo.setLine_speed(15);
    }

    public void Reset_buyer() {

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        // 구매자 초기화
        MainActivity.buyerinfo = new ArrayList<>();

        // 구매자 넣기
        for (int i = 0; i < 10 + MainActivity.userinfo.getuBuyerLv(); i++) {
            int npc_num = random.nextInt(MainActivity.npcinfo.size());
            nDto npc = MainActivity.npcinfo.get(npc_num);
            MainActivity.buyerinfo.add(npc);
            Log.d("NPClist", "npc_num : " + npc_num);
        }
    }
}
