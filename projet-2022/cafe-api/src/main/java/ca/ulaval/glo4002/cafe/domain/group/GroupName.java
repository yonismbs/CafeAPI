package ca.ulaval.glo4002.cafe.domain.group;

public record GroupName(String name) {
    public static final GroupName VOID = new GroupName(null);

    public String stringRepresentation() {
        return this.name;
    }

    public boolean isAbsent() {
        return this.equals(VOID);
    }
}
