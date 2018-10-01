package blacksmith.com.sandorln.blacksmith.Dto;

/**
 * Created by SanDorln on 2016-08-11.
 */
public class uDto {
    private int uGold;              // 유저가 가지고 있는 돈
    private int uMissionLv;         // 현재 진행 중인 Mission LEVEL
    private int uRoomLv;            // 유저의 Room LEVEL (무기 거치대)
    private int uSmithyLv;          // 유저의 대장간 LEVEL 제작 무기 다양화
    private int uNextMissionLv;     // 다음 Mission 으로 가기위한 단계 10단계일 시 다음 단계로
    private int uTouchLv;           // 터치 레벨
    private int uBuyerLv;              // 가게 홍보 LV (구매자들 증가)

    public uDto(int uGold, int uMissionLv, int uRoomLv, int uSmithyLv, int uNextMissionLv, int uTouchLv, int uBuyerLv) {
        this.uGold = uGold;
        this.uMissionLv = uMissionLv;
        this.uRoomLv = uRoomLv;
        this.uSmithyLv = uSmithyLv;
        this.uNextMissionLv = uNextMissionLv;
        this.uTouchLv = uTouchLv;
        this.uBuyerLv = uBuyerLv;
    }

    public int getuTouchLv() {
        return uTouchLv;
    }

    public int getuBuyerLv() {
        return uBuyerLv;
    }

    public void setuBuyerLv(int uBuyerLv) {
        this.uBuyerLv = uBuyerLv;
    }

    public void setuTouchLv(int uTouchLv) {
        this.uTouchLv = uTouchLv;
    }

    public int getuGold() {
        return uGold;
    }

    public void setuGold(int uGold) {
        this.uGold = uGold;
    }

    public int getuMissionLv() {
        return uMissionLv;
    }

    public void setuMissionLv(int uMissionLv) {
        this.uMissionLv = uMissionLv;
    }

    public int getuRoomLv() {
        return uRoomLv;
    }

    public void setuRoomLv(int uRoomLv) {
        this.uRoomLv = uRoomLv;
    }

    public int getuSmithyLv() {
        return uSmithyLv;
    }

    public void setuSmithyLv(int uSmithyLv) {
        this.uSmithyLv = uSmithyLv;
    }

    public int getuNextMissionLv() {
        return uNextMissionLv;
    }

    public void setuNextMissionLv(int uNextMissionLv) {
        this.uNextMissionLv = uNextMissionLv;
    }
}
