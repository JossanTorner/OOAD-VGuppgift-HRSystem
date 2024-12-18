package EmployeeDatabase;

public enum Position {
    CEO("CEO"),
    MANAGER("Manager"),
    SUBORDINATE("Subordinate"),
    DEVELOPER("Developer"),
    PRODUCT_OWNER("Product Owner"),
    SCRUM_MASTER("Scrum-master");

    public final String title;

    Position(String title) {
        this.title = title;
    }
}
