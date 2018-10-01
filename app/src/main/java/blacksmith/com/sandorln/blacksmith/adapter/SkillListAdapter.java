package blacksmith.com.sandorln.blacksmith.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import blacksmith.com.sandorln.blacksmith.Dto.cDto;
import blacksmith.com.sandorln.blacksmith.MainActivity;
import blacksmith.com.sandorln.blacksmith.R;

/**
 * Created by SanDorln on 2016-08-24.
 */
public class SkillListAdapter extends BaseAdapter {

    final String packName = "blacksmith.com.sandorln.blacksmith";

    Toast toast;

    private ArrayList<cDto> characterinfo;
    private MainActivity mainActivity;

    public SkillListAdapter(ArrayList<cDto> characterinfo, MainActivity mainActivity) {
        this.characterinfo = characterinfo;
        this.mainActivity = mainActivity;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final Context context = parent.getContext();

        // "Listview_item" Layout을 inflate하여 converView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.skill_list_value, parent, false);
        }

        // 화면에 표시할 View에서 위젯에 대한 참조 획득
        ImageView cIcon = (ImageView) convertView.findViewById(R.id.Skill_listview_skill_icon);
        TextView cName = (TextView) convertView.findViewById(R.id.Skill_listview_skill_name);
        TextView cInformation = (TextView) convertView.findViewById(R.id.Skill_listview_skill_Text);
        TextView cUpgradeM = (TextView) convertView.findViewById(R.id.Skill_listview_skill_upgradeM);
        TextView cSkillBtnText = (TextView) convertView.findViewById(R.id.Skill_listview_skill_btnText);

        LinearLayout cUpgrade_layout = (LinearLayout) convertView.findViewById(R.id.Skill_listview_upgrade);

        // DATA set 에서 position에 위치한 데이터 참조 획득
        final cDto cdto = characterinfo.get(position);

        // 각 아이템 아이콘 ID 값 가져오기
        String resName = "@drawable/skill_icon_" + cdto.getcId();
        int skilliCon = parent.getResources().getIdentifier(resName, "drawable", packName);

        // 아이템 내 각 위젯에 데이터 반영
        cIcon.setImageResource(skilliCon);                              // 스킬 아이콘

        cInformation.setText(cdto.getcInformation());                   // 설명
        cUpgradeM.setText(cdto.getcMoney() + " G");                     // 강화 금액

        if (cdto.getcBuy() == 0) {
            cName.setText(cdto.getcName() + "");       // 스킬 이름
            cSkillBtnText.setText("구매");
            cUpgrade_layout.setBackgroundColor(0xff5fbcff);
        } else {
            cName.setText(cdto.getcName() + " Lv. " + cdto.getcLv());       // 스킬 이름
            cSkillBtnText.setText("강화");
            cUpgrade_layout.setBackgroundColor(0xffffcc5f);
        }


        // 업그레이드 버튼
        cUpgrade_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cdto.getcBuy() == 0 && MainActivity.userinfo.getuGold() >= cdto.getcMoney()) {
                    MainActivity.userinfo.setuGold(MainActivity.userinfo.getuGold() - cdto.getcMoney());
                    cdto.setcBuy(1);
                    mainActivity.Reset_View();
                } else {
                    if (MainActivity.userinfo.getuGold() >= cdto.getcMoney()) { // 가지고 있는 돈 비교
                        MainActivity.userinfo.setuGold(MainActivity.userinfo.getuGold() - cdto.getcMoney());
                        cdto.setcLv(cdto.getcLv() + 1);
                        cdto.setcMoney((int) (cdto.getcMoney() * 1.1));
                        mainActivity.Reset_View();
                    } else {
                        toast.makeText(parent.getContext(), "비용이 부족합니다", Toast.LENGTH_SHORT).show();
                    }
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return characterinfo.size();
    }

    @Override
    public Object getItem(int position) {
        return characterinfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
