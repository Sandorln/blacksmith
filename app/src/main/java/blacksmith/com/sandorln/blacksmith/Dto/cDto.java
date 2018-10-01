package blacksmith.com.sandorln.blacksmith.Dto;

/**
 * Created by SanDorln on 2016-08-11.
 */
public class cDto {
    private int cId;            // 캐릭터 스킬 고유 번호
    private String cName;       // 캐릭터 스킬 이름
    private int cBuy;           // 캐릭터 스킬 보유 여부
    private int cLv;            // 캐릭터 스킬현재 레벨
    private int cMoney;         // 구매 비용
    private String cInformation;// 캐릭터 스킬 정보

    public cDto(int cId, String cName, int cBuy, int cLv, int cMoney, String cInformation) {
        this.cId = cId;
        this.cName = cName;
        this.cBuy = cBuy;
        this.cLv = cLv;
        this.cMoney = cMoney;
        this.cInformation = cInformation;
    }

    public String getcInformation() {
        return cInformation;
    }

    public void setcInformation(String cInformation) {
        this.cInformation = cInformation;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getcBuy() {
        return cBuy;
    }

    public void setcBuy(int cBuy) {
        this.cBuy = cBuy;
    }

    public int getcLv() {
        return cLv;
    }

    public void setcLv(int cLv) {
        this.cLv = cLv;
    }

    public int getcMoney() {
        return cMoney;
    }

    public void setcMoney(int cMoney) {
        this.cMoney = cMoney;
    }
}
