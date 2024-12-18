package SubjectModel;

import ObserverView.*;
import EmployeeDatabase.*;

import java.util.ArrayList;
import java.util.List;

public class HRModel {

    private final EmployeeDatabase employeeDatabase;
    private List<Employee> currentSearchResult;
    private final List<Employee> filteredResult;
    private Employee selectedEmployee;
    private Employee changedEmployee;

    private final List<EmployeeDetailsObserver> detailsObservers;
    private final List<SearchResultObserver> searchObservers;
    private final List<FilterResultObserver> filterObservers;
    private final List<EmployeeChangeObserver> changeObservers;

    public HRModel() {
        employeeDatabase = new EmployeeDatabase();

        currentSearchResult = new ArrayList<>();
        filteredResult = new ArrayList<>();

        detailsObservers = new ArrayList<>();
        searchObservers = new ArrayList<>();
        filterObservers = new ArrayList<>();
        changeObservers = new ArrayList<>();
    }

    public List<Employee> getCurrentSearchResult() {
        return currentSearchResult;
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    public Employee getChangedEmployee(){
        return changedEmployee;
    }

    public List<Employee> getCurrentFilteredResult(){
        return filteredResult;
    }

    public void notifyDetailsObservers() {
        for(EmployeeDetailsObserver observer : detailsObservers) {
            observer.updateEmployeeDetails();
        }
    }

    public void notifySearchObservers() {
        for(SearchResultObserver observer : searchObservers) {
            observer.updateSearchResult();
        }
    }

    public void notifyFilterObservers() {
        for(FilterResultObserver observer : filterObservers) {
            observer.updateFilteredResult();
        }
    }

    public void notifyChangeObservers(){
        for(EmployeeChangeObserver observer : changeObservers){
            observer.updateEmployee();
        }
    }

    public void registerChangeObserver(EmployeeChangeObserver employeeChangeObserver){
        changeObservers.add(employeeChangeObserver);
    }

    public void registerDetailsObserver(EmployeeDetailsObserver employeeDetailsObserver) {
        detailsObservers.add(employeeDetailsObserver);
    }

    public void registerSearchObserver(SearchResultObserver searchResultObserver) {
        searchObservers.add(searchResultObserver);
    }

    public void registerFilterObserver(FilterResultObserver filterResultObserver) {
        filterObservers.add(filterResultObserver);
    }

    public void setSearchResultByName(String name) {
        currentSearchResult = employeeDatabase.searchByName(name);
        notifySearchObservers();
    }

    public void setSearchResultById(long id) {
        currentSearchResult = employeeDatabase.searchByID(id);
        notifySearchObservers();
    }

    public void filterSearchResultByPosition(String position) {
        if (position.equals("None")) {
            filteredResult.clear();
            filteredResult.addAll(currentSearchResult);
        } else {
            List<Employee> filteredList = new ArrayList<>();
            for (Employee employee : currentSearchResult) {
                if (employee.getPosition().title.equals(position)) {
                    filteredList.add(employee);
                }
            }
            filteredResult.clear();
            filteredResult.addAll(filteredList);
        }
        notifyFilterObservers();
    }

    public void setSelectedEmployee(long id) {
        List<Employee> foundEmployees = employeeDatabase.searchByID(id);
        if (foundEmployees != null) {
            selectedEmployee = foundEmployees.getFirst();
        }
        notifyDetailsObservers();
    }

    public void setChangedEmployee(Employee employee){
        changedEmployee = employee;
        notifyChangeObservers();
    }

}
