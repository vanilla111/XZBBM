package team.redrock.common;

public enum QType {
    OTHER("其他"),
    COMPUTER("计算机"),
    ECONOMIC("经管"),
    ISL("安法学院"),
    SCIENCE("理学院"),
    BIOLOGY("生物学院"),
    OPTOELECTRONICS("光电学院"),
    AUTOMATION("自动化学院"),
    ADVANCE("先进制造学院"),
    INTERNATIONAL("国际"),
    COMMUNICATION("通信"),
    SOFTWARE("软件"),
    GIRLS("传媒"),
    PE("体育"),
    FOREIGN("外国语");

    private String qName;

    QType(String qName) {
        this.qName = qName;
    }

    public String getQType() {
        return qName;
    }
}
