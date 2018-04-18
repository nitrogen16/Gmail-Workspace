package enums;

public enum FoldersEnum {
    INBOX("#inbox"),
    SENT_MAIL("#sent"),
    DRAFTS("@drafts"),
    BIN("#trash");

    private String url;

    FoldersEnum(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }

}
