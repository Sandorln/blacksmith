package blacksmith.com.sandorln.blacksmith.Dto;

/**
 * Created by SanDorln on 2016-08-11.
 */
public class iDto {
    private int iId;            // 아이템 고유 번호
    private String iName;       // 아이템 이름
    private int iSLv;           // 아이템 제작 레벨 (사용자의 대장간 LV 과 호응)
    private int iSellMoney;     // 판매 가격
    private int iMadeMoney;     // 제작 가격
    private int iHp;            // 내구도
    private int iLv;            // 레벨 업 시 판매 가격 및 제작 가격 증가
    private int iHp_Sub;        // 아이템 강화시 감소되는 내구도
    private int iIcon;          // 아이템 이미지
    private int iBuy;           // 해당 아이템을 구매했는지 여부
    private int iBuyMoney;      // 해당 아이템 구매 시
    private String iInformation; // 해당 아이템 설명

    public iDto(int iId, String iName, int iSLv, int iSellMoney, int iMadeMoney, int iHp, int iLv, int iHp_Sub, int iBuy, int iBuyMoney, String iInformation) {
        this.iId = iId;
        this.iName = iName;
        this.iSLv = iSLv;
        this.iSellMoney = iSellMoney;
        this.iMadeMoney = iMadeMoney;
        this.iHp = iHp;
        this.iLv = iLv;
        this.iHp_Sub = iHp_Sub;
        this.iBuy = iBuy;
        this.iBuyMoney = iBuyMoney;
        this.iInformation = iInformation;
    }

    public String getiInformation() {
        return iInformation;
    }

    public void setiInformation(String iInformation) {
        this.iInformation = iInformation;
    }

    public int getiBuyMoney() {
        return iBuyMoney;
    }

    public void setiBuyMoney(int iBuyMoney) {
        this.iBuyMoney = iBuyMoney;
    }

    public int getiBuy() {
        return iBuy;
    }

    public void setiBuy(int iBuy) {
        this.iBuy = iBuy;
    }

    public int getiIcon() {
        return iIcon;
    }

    public void setiIcon(int iIcon) {
        this.iIcon = iIcon;
    }

    public int getiHp_Sub() {
        return iHp_Sub;
    }

    public void setiHp_Sub(int iHp_Sub) {
        this.iHp_Sub = iHp_Sub;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public int getiSLv() {
        return iSLv;
    }

    public void setiSLv(int iSLv) {
        this.iSLv = iSLv;
    }

    public int getiSellMoney() {
        return iSellMoney;
    }

    public void setiSellMoney(int iSellMoney) {
        this.iSellMoney = iSellMoney;
    }

    public int getiMadeMoney() {
        return iMadeMoney;
    }

    public void setiMadeMoney(int iMadeMoney) {
        this.iMadeMoney = iMadeMoney;
    }

    public int getiHp() {
        return iHp;
    }

    public void setiHp(int iHp) {
        this.iHp = iHp;
    }

    public int getiLv() {
        return iLv;
    }

    public void setiLv(int iLv) {
        this.iLv = iLv;
    }
}
