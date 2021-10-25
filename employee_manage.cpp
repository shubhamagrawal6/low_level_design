#include<bits/stdc++.h>
using namespace std;

class Employee;
class System;

class Employee{
    int id;
    string name;
    int managerId;
    list<Employee> subordinates;
    int getUniqueId();


    public:
    Employee(string);
    int getId();
    string getName();
    int getManagerId();
    void setManagerId(int);
    list<Employee> getSubordinates();
    void addSubordinate(Employee&);
};

class System{
    list<Employee> employees;
    unordered_map<int, Employee*> employeeMap;

    public:
    System(){}
    void registerEmployee(Employee&);
    void registerManager(int, int);
    void printDetails(int);
    void printDetails(string);
    list<Employee> getSubordinates(int);
    list<Employee> getSubordinates(string);
};

Employee::Employee(string name){
    this->name = name;
    this->id = getUniqueId();
    managerId = 0;
}

int Employee::getUniqueId(){
    static int uniqueId = 1;
    return uniqueId++;
}

int Employee::getId(){
    return this->id;
}

string Employee::getName(){
    return this->name;
}

int Employee::getManagerId(){
    return this->managerId;
}

void Employee::setManagerId(int managerId){
    this->managerId = managerId;
}

list<Employee> Employee::getSubordinates(){
    return this->subordinates;
}

void Employee::addSubordinate(Employee& subordinate){
    (this->subordinates).push_back(subordinate);
}

void System::registerEmployee(Employee& employee){
    employees.push_back(employee);
    employeeMap[employee.getId()] = &employees.back();
}

void System::registerManager(int empId, int managerId){
    if(!employeeMap.count(empId) || !employeeMap.count(managerId)){
        cout<<"Either Employee of Manager is not registered! Please provide correct details."<<endl;
        return;
    }

    employeeMap[empId]->setManagerId(managerId);
    employeeMap[managerId]->addSubordinate(*(employeeMap[empId]));
}

void System::printDetails(int empId){
    if(employeeMap.find(empId) == employeeMap.end()){
        cout<<"Employee is not registered! Please provide correct details."<<endl;
        return;
    }

    cout<<"Id: "<<empId<<endl;
    cout<<"Name: "<<employeeMap[empId]->getName()<<endl;
    int managerId = employeeMap[empId]->getManagerId();
    cout<<"Manager: "<<employeeMap[managerId]->getName()<<endl;
}

void System::printDetails(string prefix){
    for(Employee employee: employees){
        if(employee.getName().substr(0, prefix.size()) == prefix){
            cout<<"Id: "<<employee.getId()<<endl;
            cout<<"Name: "<<employee.getName()<<endl;
            int managerId = employee.getManagerId();
            cout<<"Manager: "<<employeeMap[managerId]->getName()<<endl;
        }
    }
}

list<Employee> System::getSubordinates(int empId){
    if(employeeMap.find(empId) == employeeMap.end()){
        cout<<"Employee not registered! Provide correct ID and try again."<<endl;
        list<Employee> temp;
        return temp;
    }

    return employeeMap[empId]->getSubordinates();
}

list<Employee> System::getSubordinates(string name){
    for(Employee employee: employees){
        if(employee.getName() == name){
            return employee.getSubordinates();
        }
    }

    list<Employee> temp;
    return temp;
}

int main(){
    Employee employee("Achilees");
	Employee employee1("Hector");
	Employee employee2("Paris");
	Employee employee3("Helen");

	System system;
	system.registerEmployee(employee);
	system.registerEmployee(employee1);
	system.registerEmployee(employee2);
	system.registerEmployee(employee3);

	system.registerManager(employee1.getId(), employee.getId());
	system.registerManager(employee2.getId(), employee.getId());
	system.registerManager(employee3.getId(), employee.getId());
	system.printDetails(employee1.getId());
	
    cout<<"********************************************************************"<<endl;
	system.printDetails("He");

	cout<<"********************************************************************"<<endl;
	for(Employee e: system.getSubordinates(employee.getName())){
		cout<<e.getName()<<" "<<e.getId()<<endl;
	}

	cout<<"********************************************************************"<<endl;
	for(Employee e: system.getSubordinates(employee.getId())){
		cout<<e.getName()<<" "<<e.getId()<<endl;
	}

	cout<<"********************************************************************"<<endl;
	for(Employee e: system.getSubordinates(employee1.getId())){
		cout<<e.getName()<<" "<<e.getId()<<endl;
	}

    return 0;
}