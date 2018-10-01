package blacksmith.com.sandorln.blacksmith.Dto;

/**
 * Created by SanDorln on 2016-08-26.
 */
public class nDto {

    private int nId;            // NPC 고유 번호
    private int nCharacter;     // 메인 화면 NPC 이미지
    private int nIcon;          // 대화 상자 NPC 아이콘
    private String nTalk;       // NPC 고유 대화
    private int nType;          // 0 : 장사꾼 , 1 : 모험가

    public nDto(int nId, String nTalk, int nType) {
        this.nId = nId;
        this.nTalk = nTalk;
        this.nType = nType;
    }

    public nDto(int nId, int nCharacter, int nIcon, String nTalk, int nType) {
        this.nId = nId;
        this.nCharacter = nCharacter;
        this.nIcon = nIcon;
        this.nTalk = nTalk;
        this.nType = nType;
    }

    public int getnType() {
        return nType;
    }

    public void setnType(int nType) {
        this.nType = nType;
    }

    public int getnCharacter() {
        return nCharacter;
    }

    public void setnCharacter(int nCharacter) {
        this.nCharacter = nCharacter;
    }

    public int getnIcon() {
        return nIcon;
    }

    public void setnIcon(int nIcon) {
        this.nIcon = nIcon;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getnTalk() {
        return nTalk;
    }

    public void setnTalk(String nTalk) {
        this.nTalk = nTalk;
    }
}
