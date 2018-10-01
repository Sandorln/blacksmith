package blacksmith.com.sandorln.blacksmith.Dto;

/**
 * Created by SanDorln on 2016-08-21.
 */
public class mDto {
    // 미션 데이터 용
    private int weapon_icon;    // 아이템 Drawable 아이콘 번호
    private int item_id;        // 아이템 고유 번호
    private int item_hp;        // 아이템 최대 내구도
    private int item_lv;        // 아이템 강화 레벨
    private int item_hp_sub;    // 아이템 강화 때마다 감소되는 내구도
    private float line_speed;   // 업그레이드 라인 스피드

    private String item_name;   // 아이템 이름
    private int clear_gold;     // 클리어시 보상

    public mDto() {
    }

    public float getLine_speed() {
        return line_speed;
    }

    public void setLine_speed(float line_speed) {
        this.line_speed = line_speed;
    }

    public int getItem_hp_sub() {
        return item_hp_sub;
    }

    public void setItem_hp_sub(int item_hp_sub) {
        this.item_hp_sub = item_hp_sub;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getItem_lv() {
        return item_lv;
    }

    public void setItem_lv(int item_lv) {
        this.item_lv = item_lv;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getWeapon_icon() {
        return weapon_icon;
    }

    public void setWeapon_icon(int weapon_icon) {
        this.weapon_icon = weapon_icon;
    }

    public int getItem_hp() {
        return item_hp;
    }

    public void setItem_hp(int item_hp) {
        this.item_hp = item_hp;
    }

    public int getClear_gold() {
        return clear_gold;
    }

    public void setClear_gold(int clear_gold) {
        this.clear_gold = clear_gold;
    }
}
