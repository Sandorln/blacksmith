package blacksmith.com.sandorln.blacksmith.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import blacksmith.com.sandorln.blacksmith.Dto.iDto;
import blacksmith.com.sandorln.blacksmith.MainActivity;
import blacksmith.com.sandorln.blacksmith.R;
import blacksmith.com.sandorln.blacksmith.adapter.ShopListAdapter;

/**
 * Created by SanDorln on 2016-08-17.
 */
public class ShopFragment extends Fragment {

    View shopView;

    LinearLayout SmithyLvUp_layout;
    TextView smithyLv_text;
    TextView smithyMoney_text;

    ListView shopList;
    MainActivity mainActivity;
    ShopListAdapter adapter;

    int smithyUp_Money;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shopView = inflater.inflate(R.layout.shop_list, container, false);
        shopList = (ListView) shopView.findViewById(R.id.Shop_listview);
        smithyLv_text = (TextView) shopView.findViewById(R.id.Shop_listview_smithyLv);
        smithyMoney_text = (TextView) shopView.findViewById(R.id.Shop_listview_smithyMoney);

        SmithyLvUp_layout = (LinearLayout) shopView.findViewById(R.id.Shop_SmithyLvUp);

        mainActivity = (MainActivity) getActivity();

        adapter = new ShopListAdapter(mainActivity);
        shopList.setAdapter(adapter);

        smithyUp_Money = (int) (MainActivity.userinfo.getuSmithyLv() * 5000 * 1.2);

        smithyLv_text.setText("제작 숙련도 Lv. " + MainActivity.userinfo.getuSmithyLv());
        smithyMoney_text.setText(smithyUp_Money + " G");

        SmithyLvUp_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        SmithyLvUp_layout.setBackgroundColor(0xFF9C6A00);
                        break;
                    case MotionEvent.ACTION_UP:
                        SmithyLvUp_layout.setBackgroundColor(0xffffcc5f);
                        break;
                }
                return false;
            }
        });

        // ListView 클릭 리스너 설정
        SmithyLvUp_layout.setOnClickListener(SmithyLvUp_listener);

        return shopView;
    }

    View.OnClickListener SmithyLvUp_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 대장간 레벨 업
            if (MainActivity.userinfo.getuGold() >= smithyUp_Money) {
                MainActivity.userinfo.setuGold(MainActivity.userinfo.getuGold() - smithyUp_Money);
                MainActivity.userinfo.setuSmithyLv(MainActivity.userinfo.getuSmithyLv() + 1);

                mainActivity.Reset_View();
                adapter.notifyDataSetChanged();

                smithyLv_text.setText("제작 숙련도 Lv. " + MainActivity.userinfo.getuSmithyLv());
                smithyUp_Money = (int) (MainActivity.userinfo.getuSmithyLv() * 5000 * 1.2);
                smithyMoney_text.setText(smithyUp_Money + " G");
            } else {
                Toast.makeText(getContext(), "비용이 모자릅니다", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
