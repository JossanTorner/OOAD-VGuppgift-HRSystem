package Controller;

import SubjectModel.HRModel;

public class Main {
    public static void main(String[] args) {
        HRModel model = new HRModel();
        HRController controller = new HRController(model);
    }
}