package blacksmith.com.sandorln.blacksmith.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import blacksmith.com.sandorln.blacksmith.Dto.iDto;
import blacksmith.com.sandorln.blacksmith.Dto.uDto;
import blacksmith.com.sandorln.blacksmith.MainActivity;
import blacksmith.com.sandorln.blacksmith.R;

/**
 * Created by SanDorln on 2016-08-17.
 */
public class ShopListAdapter extends BaseAdapter {

    private uDto userinfo;
    private MainActivity mainActivity;

    public ShopListAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        // 유저 정보값 가져오기
        userinfo = MainActivity.userinfo;

        final Context context = parent.getContext();

        // "Listview_item" Layout을 inflate하여 converView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.shop_list_value, parent, false);
        }

        // 화면에 표시할 View에서 위젯에 대한 참조 획득
        ImageView iIcon = (ImageView) convertView.findViewById(R.id.Shop_listview_weapon_icon);
        TextView iName = (TextView) convertView.findViewById(R.id.Shop_listview_weapon_name);
        TextView iSellMoney = (TextView) convertView.findViewById(R.id.Shop_listview_weapon_SellM);

        TextView iBuyMoney = (TextView) convertView.findViewById(R.id.Shop_listview_weapon_madeM);
        TextView iBuyMoney_Text = (TextView) convertView.findViewById(R.id.Shop_listview_weapon_made);

        final LinearLayout iUpgrade_layout = (LinearLayout) convertView.findViewById(R.id.Shop_listview_upgrade);

        RelativeLayout iLVcut_layout = (RelativeLayout) convertView.findViewById(R.id.Shop_listview_weapon_LVcut);

        TextView iLvcut_Text = (TextView) convertView.findViewById(R.id.Shop_listview_weapon_LVcutT);


        // DATA set 에서 position에 위치한 데이터 참조 획득
        final iDto idto = MainActivity.iteminfo.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iIcon.setImageResource(idto.getiIcon());                                // 아이템 아이콘
        iName.setText(idto.getiName() + " + " + idto.getiLv());                 // 아이템 이름
        iSellMoney.setText(idto.getiInformation());                             // 설명

        iBuyMoney.setText(idto.getiBuyMoney() + "G");                           // 제작 금액
        if (idto.getiBuy() != 1) {
            iBuyMoney_Text.setText("제작법 구매");                              // 문구 변경
            iUpgrade_layout.setBackgroundColor(0xff5fbcff);
        } else {
            iBuyMoney_Text.setText("제작법 강화");                              // 문구 변경
            iUpgrade_layout.setBackgroundColor(0xffffcc5f);
        }


        if (userinfo.getuSmithyLv() >= idto.getiSLv()) {
            iLVcut_layout.setVisibility(View.INVISIBLE);
            // 업그레이드 버튼
            iUpgrade_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (idto.getiBuy() != 1) {
                        if (userinfo.getuGold() >= idto.getiBuyMoney()) { // 가지고 있는 돈 비교
                            userinfo.setuGold(userinfo.getuGold() - idto.getiBuyMoney());
                            idto.setiBuy(1);
                            idto.setiBuyMoney((int) (idto.getiBuyMoney() * 1.5));
                            mainActivity.Reset_View();
                        } else {
                            Toast.makeText(parent.getContext(), "구매 비용이 부족합니다", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (userinfo.getuSmithyLv() > idto.getiLv()) {
                            if (userinfo.getuGold() >= idto.getiBuyMoney()) { // 가지고 있는 돈 비교
                                userinfo.setuGold(userinfo.getuGold() - idto.getiBuyMoney());
                                idto.setiLv(idto.getiLv() + 1);
                                idto.setiBuyMoney((int) (idto.getiBuyMoney() * 1.5));
                                mainActivity.Reset_View();
                            } else {
                                Toast.makeText(parent.getContext(), "업그레이드 비용이 부족합니다", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(parent.getContext(), "대장간 Lv 부족", Toast.LENGTH_SHORT).show();
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        } else {
            iLVcut_layout.setVisibility(View.VISIBLE);
            iLvcut_Text.setText("대장간 Lv. " + idto.getiSLv() + " 이상 필요");
            iLVcut_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(parent.getContext(), "대장간 Lv 부족", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // 버튼 색깔 내기용
        iUpgrade_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (idto.getiBuy() != 1) {
                            iUpgrade_layout.setBackgroundColor(0xff2f628e);
                        } else {
                            iUpgrade_layout.setBackgroundColor(0xFF9C6A00);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (idto.getiBuy() != 1) {
                            iUpgrade_layout.setBackgroundColor(0xff68b9ff);
                        } else {
                            iUpgrade_layout.setBackgroundColor(0xffffcc5f);
                        }
                        break;
                }
                return false;
            }
        });


        return convertView;
    }

    @Override
    public int getCount() {
        return MainActivity.iteminfo.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.iteminfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
