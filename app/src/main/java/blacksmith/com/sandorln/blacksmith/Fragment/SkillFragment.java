package blacksmith.com.sandorln.blacksmith.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import blacksmith.com.sandorln.blacksmith.Dto.cDto;
import blacksmith.com.sandorln.blacksmith.MainActivity;
import blacksmith.com.sandorln.blacksmith.R;
import blacksmith.com.sandorln.blacksmith.adapter.SkillListAdapter;

/**
 * Created by SanDorln on 2016-08-24.
 */
public class SkillFragment extends Fragment {

    View skillView;

    LinearLayout SkillLvUp_layout;      // 터치 레벨 강화
    TextView skillLv_text;              // 망치 레벨 표시
    TextView skillMoney_text;           // 망치 강화시 필요 GOLD 표시

    ListView skillList;

    MainActivity mainActivity;
    SkillListAdapter adapter;

    ArrayList<cDto> characterinfo;

    int touchLvGold;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        skillView = inflater.inflate(R.layout.skill_list, container, false);
        characterinfo = MainActivity.characterinfo;

        Reset_Object();
        Reset_View();

        return skillView;
    }

    public void Reset_Object() {
        SkillLvUp_layout = (LinearLayout) skillView.findViewById(R.id.Skill_TouchLvUp);

        skillLv_text = (TextView) skillView.findViewById(R.id.Skill_listview_touchLv);
        skillMoney_text = (TextView) skillView.findViewById(R.id.Skill_listview_touchMoney);
        skillList = (ListView) skillView.findViewById(R.id.Skill_listview);

        touchLvGold = MainActivity.userinfo.getuTouchLv() * 500;

        mainActivity = (MainActivity) getActivity();
    }

    public void Reset_View() {
        skillLv_text.setText("망치 숙련도 Lv. " + MainActivity.userinfo.getuTouchLv());
        skillMoney_text.setText(touchLvGold + " G");

        SkillLvUp_layout.setOnClickListener(clickListener);

        adapter = new SkillListAdapter(characterinfo, mainActivity);
        skillList.setAdapter(adapter);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (MainActivity.userinfo.getuGold() >= MainActivity.userinfo.getuTouchLv() * 500) {
                MainActivity.userinfo.setuGold(MainActivity.userinfo.getuGold() - MainActivity.userinfo.getuTouchLv() * 500);
                MainActivity.userinfo.setuTouchLv(MainActivity.userinfo.getuTouchLv() + 1);
                mainActivity.Reset_View();

                Reset_Object();
                Reset_View();
            }
        }
    };
}
