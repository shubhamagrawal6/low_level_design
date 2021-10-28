import java.io.*;
import java.util.*;

class Employee{
    private int id;
    private String name;
    private int managerId;
    private static int uniqueId = 1;
    private LinkedList<Employee> subordinates = new LinkedList<Employee>();

    private int getUniqueId(){
        return this.uniqueId++;
    }

    public Employee(String name){
        this.id = getUniqueId();
        this.name = name;
        this.managerId = 0;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getManagerId(){
        return this.managerId;
    }

    public void setManagerId(int managerId){
        this.managerId = managerId;
    }

    public LinkedList<Employee> getSubordinates(){
        return this.subordinates;
    }

    public void addSubordinate(Employee subordinate){
        this.subordinates.add(subordinate);
    }
}

class Sys{
    private LinkedList<Employee> employees = new LinkedList<Employee>();
    private HashMap<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();

    public Sys(){}
    
    public void registerEmployee(Employee employee){
        employees.add(employee);
        employeeMap.put(employee.getId(), employees.getLast());
    }

    public void registerManager(int empId, int managerId){
        if(employeeMap.containsKey(empId) == false || employeeMap.containsKey(managerId) == false){
            System.out.println("Either employee or manager is not registered! Please provide correct details.");
            return;
        }

        employeeMap.get(empId).setManagerId(managerId);
        employeeMap.get(managerId).addSubordinate(employeeMap.get(empId));
    }

    public void printDetails(int empId){
        if(employeeMap.containsKey(empId) == false){
            System.out.println("Employee is not registered! Please provide correct details.");
            return;
        }

        System.out.println("ID: " + empId);
        System.out.println("Name: " + employeeMap.get(empId).getName());
        int managerId = employeeMap.get(empId).getManagerId();
        System.out.println("Manager: " + employeeMap.get(managerId).getName());
    }

    public void printDetails(String prefix){
        for(Employee employee: this.employees){
            if(employee.getName().startsWith(prefix)){
                System.out.println("Id: " + employee.getId());
                System.out.println("Name: " + employee.getName());
                int managerId = employee.getManagerId();
                System.out.println("Manager: " + employeeMap.get(managerId).getName());
            }
        }
    }

    public LinkedList<Employee> getSubordinates(int empId){
        if(employeeMap.containsKey(empId) == false){
            System.out.println("Employee not registered! Provide correct ID and try again.");
            LinkedList<Employee> temp = new LinkedList<Employee>();
            return temp;
        }

        return employeeMap.get(empId).getSubordinates();
    }

    public LinkedList<Employee> getSubordinates(String name){
        for(Employee employee: this.employees){
            if(employee.getName() == name){
                return employee.getSubordinates();
            }
        }
    
        LinkedList<Employee> temp = new LinkedList<Employee>();
        return temp;
    }
}

public class employee_manage{
    public static void main(String[] args){
        Employee employee = new Employee("Achilees");
        Employee employee1 = new Employee("Hector");
        Employee employee2 = new Employee("Paris");
        Employee employee3 = new Employee("Helen");

        Sys sys = new Sys();
        sys.registerEmployee(employee);
        sys.registerEmployee(employee1);
        sys.registerEmployee(employee2);
        sys.registerEmployee(employee3);

        sys.registerManager(employee1.getId(), employee.getId());
        sys.registerManager(employee2.getId(), employee.getId());
        sys.registerManager(employee3.getId(), employee.getId());
        
        sys.printDetails(employee1.getId());
        
        System.out.println("********************************************************************");
        sys.printDetails("He");

        System.out.println("********************************************************************");
        for(Employee e: sys.getSubordinates(employee.getName())){
            System.out.println(e.getName() + " " + e.getId());
        }

        System.out.println("********************************************************************");
        for(Employee e: sys.getSubordinates(employee.getId())){
            System.out.println(e.getName() + " " + e.getId());
        }

        System.out.println("********************************************************************");
        for(Employee e: sys.getSubordinates(employee1.getId())){
            System.out.println(e.getName() + " " + e.getId());
        }
    }
}