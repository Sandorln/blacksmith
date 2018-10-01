package blacksmith.com.sandorln.blacksmith;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Random;

import blacksmith.com.sandorln.blacksmith.Dto.cDto;
import blacksmith.com.sandorln.blacksmith.Dto.iDto;
import blacksmith.com.sandorln.blacksmith.Dto.mDto;
import blacksmith.com.sandorln.blacksmith.Dto.nDto;
import blacksmith.com.sandorln.blacksmith.Dto.uDto;
import blacksmith.com.sandorln.blacksmith.Fragment.MissionFragment;
import blacksmith.com.sandorln.blacksmith.Fragment.MissionListFragment;
import blacksmith.com.sandorln.blacksmith.Fragment.MissionPlayFragment;
import blacksmith.com.sandorln.blacksmith.Fragment.MissionUpFragment;
import blacksmith.com.sandorln.blacksmith.Fragment.ShopFragment;
import blacksmith.com.sandorln.blacksmith.Fragment.SkillFragment;
import blacksmith.com.sandorln.blacksmith.SQL.MySQLiteOpenHelper;
import blacksmith.com.sandorln.blacksmith.SQL.SQLData_Management;

public class MainActivity extends AppCompatActivity {

    // SQL DATA 객체들
    SQLData_Management sqlite_M;
    MySQLiteOpenHelper openHelper;
    String dbName = "GameData.db";
    int dbVersion = 1;

    // 모드 정의
    public final static int MODE_VIEW_MISSION = 0, MODE_PLAY_MISSION = 1, MODE_UP_MISSION = 2, MODE_LIST_MISSION = 3;

    // 화면 객체들
    public static MissionFragment missionFragment;
    public static MissionPlayFragment missionPlayFragment;
    public static MissionUpFragment missionUpFragment;
    public static MissionListFragment missionListFragment;

    private TextView gold_text;          // 현재 GOLD 량

    // DTO 관리
    public static uDto userinfo;
    public static ArrayList<cDto> characterinfo;
    public static ArrayList<iDto> iteminfo;
    public static ArrayList<nDto> npcinfo;
    public static mDto missioninfo;

    public static ArrayList<nDto> buyerinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Search_DB();        // 디비 초기화
        Reset_Object();     // 객체 초기화
        Reset_View();       // 화면 초기화 및 데이터 저장
    }

    public void View_Change(int value) {
        switch (value) {
            case MODE_VIEW_MISSION:
                getSupportFragmentManager().beginTransaction().replace(R.id.M_Mission, missionFragment).commit();
                break;
            case MODE_PLAY_MISSION:
                getSupportFragmentManager().beginTransaction().replace(R.id.M_Mission, missionPlayFragment).commit();
                break;
            case MODE_UP_MISSION:
                getSupportFragmentManager().beginTransaction().replace(R.id.M_Mission, missionUpFragment).commit();
                break;
            case MODE_LIST_MISSION:
                getSupportFragmentManager().beginTransaction().replace(R.id.M_Mission, missionListFragment).commit();
                break;
        }
    }

    public void Reset_Object() { // 객체 초기화

        // 배너 광고 초기화
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        if (mAdView != null) {
            mAdView.loadAd(adRequest);
        }

        missionFragment = new MissionFragment();
        missionPlayFragment = new MissionPlayFragment();
        missionUpFragment = new MissionUpFragment();
        missionListFragment = new MissionListFragment();

        ShopFragment shopFragment = new ShopFragment();
        SkillFragment skillFragment = new SkillFragment();

        gold_text = (TextView) findViewById(R.id.M_Gold);

        getSupportFragmentManager().beginTransaction().replace(R.id.M_Mission, missionListFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.M_Shop, shopFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.M_Character, skillFragment).commit();

        // 탭 메뉴 초기화
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        if (tabHost != null) {
            tabHost.setup();
            TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Tab1").setContent(R.id.M_Mission).setIndicator("MISSION");
            TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Tab2").setContent(R.id.M_Character).setIndicator("CHARACTER");
            TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("Tab3").setContent(R.id.M_Shop).setIndicator("SHOP");

            tabHost.addTab(tabSpec1);
            tabHost.addTab(tabSpec2);
            tabHost.addTab(tabSpec3);

            for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) { // 글자 크기
                TextView temp = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                temp.setTextSize(13);
            }
        }


    }

    public void Reset_View() {   // 게임 클리어, 실패 시마다 뷰 리셋 및 데이터 저장
        missionListFragment.Reset_itemlist();

        // 데이터 관리
        sqlite_M.Update_userinfo(userinfo);             // 유저 정보 데이터 저장
        sqlite_M.Update_iteminfo(iteminfo);             // 상점 정보 데이터 저장
        sqlite_M.Update_characterinfo(characterinfo);   // 스킬 정보 데이터 저장

        gold_text.setText(userinfo.getuGold() + "");
    }

    private void Search_DB() { // 디비에서 값 가져오기
        // 객체 초기화
        openHelper = new MySQLiteOpenHelper(this, dbName, null, dbVersion);         //SQL 데이터

        try {
            sqlite_M = new SQLData_Management(openHelper.getWritableDatabase());    //SQL 연결

            // 값 가져오기
            userinfo = sqlite_M.Select_Userinfo();
            characterinfo = sqlite_M.Select_charinfo();
            iteminfo = sqlite_M.Select_iteminfo();
            npcinfo = sqlite_M.Select_npcinfo();

            // 아이템 아이콘 설정
            for (iDto value : iteminfo) {
                // 각 아이템 아이콘 ID 값 가져오기
                String resName = "@drawable/weapon_icon_" + value.getiId();
                int itemiCon = getResources().getIdentifier(resName, "drawable", getPackageName());
                value.setiIcon(itemiCon);
            }

            for (nDto value : npcinfo) {
                // 메인화면 NPC 이미지 값 저장
                String resName = "@drawable/npc_" + value.getnId();
                int npcNum = getResources().getIdentifier(resName, "drawable", getPackageName());
                value.setnCharacter(npcNum);

                // 대화화면 NPC 아이콘 값 저장
                resName = "@drawable/npcicon_" + value.getnId();
                npcNum = getResources().getIdentifier(resName, "drawable", getPackageName());
                value.setnIcon(npcNum);
            }

            Log.d("GameDataLOAD", "성공");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("GameDataLOAD", "실패");
        }
    }
}