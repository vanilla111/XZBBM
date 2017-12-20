package team.redrock.common;

public enum Jurisdiction {
    YOUKE(0, "游客"),
    DUMBASS(1, "学渣"),
    SUPERSCHOLAR(2, "学霸"),
    ADMIN(-1, "超级管理员");

    private int type;
    private String identity;

    Jurisdiction(int type, String identity) {
        this.type = type;
        this.identity = identity;
    }

    public int getType() {
        return type;
    }

    public String getIdentity() {
        return identity;
    }
}
