package EmployeeDatabase;

import java.util.Arrays;
import java.util.List;

public class DataLoader {

    List<Employee> employees;

    DataLoader() {

        employees = Arrays.asList(
                new Employee(203214, "Andreas Ohlander", 85900, Position.PRODUCT_OWNER, "andreas@mail.com", "070882233", 110),
                new Employee(1000, "Elsa", 20000, Position.DEVELOPER, "elsa@mail.com", "070882233", 20),
                new Employee(2345, "Karen", 20000, Position.SUBORDINATE, "KAREN@mail.com", "070882233", 15),
                new Employee(1, "Tommy Olsson", 100, Position.CEO, "tommyolsson@mail.com", "070882233", 200),
                new Employee(123465, "Linn Edvardsson", 100000, Position.MANAGER,"linn@mail.com", "070882233", 100),
                new Employee(539895, "Jesper Lindberg", 76000, Position.DEVELOPER, "jesper@mail.com", "070882233", 80),
                new Employee(45660, "Josefin Törner", 900000, Position.CEO, "josefin@mail.com", "070882233", 100),
                new Employee(758938, "Ylva Fröjdmark", 33000, Position.SCRUM_MASTER, "ylva@mail.com", "070882233", 100),
                new Employee(319487, "Simba", 12000, Position.SUBORDINATE, "simba@mail.com", "070882233", 0),
                new Employee(31, "Fairy Godmother", 12000, Position.DEVELOPER, "fairy@mail.com", "070882233", 10),
                new Employee(329487, "The Phantom Blot", 500, Position.SUBORDINATE, "tpb@mail.com", "070882233", 0),
                new Employee(339487, "Beagle Boy 167–671", 12000, Position.MANAGER, "bb@mail.com", "070882233", 50),
                new Employee(349487, "Aurora S. Beautée", 150000, Position.CEO, "aurora@mail.com", "070882233", 70),
                new Employee(31999, "Old Reliable", 7000, Position.SUBORDINATE, "oldr@mail.com", "070882233", 1),
                new Employee(4297, "Pärlan", 50000, Position.DEVELOPER, "parlan@mail.com", "070882233", 160),
                new Employee(4539487, "Hen Wise t. Little", 12000, Position.MANAGER, "hwtl@mail.com", "070882233", 100),
                new Employee(469487, "Mrs Pongo", 150000, Position.CEO, "pongo@mail.com", "070882233", 40),
                new Employee(479487, "Mary Poppins", 50, Position.CEO, "marypoppins@mail.com", "070882233", 30),
                new Employee(489487, "Archimedes Who", 50, Position.SUBORDINATE, "archimedes@mail.com", "070882233", 189)
        );
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
