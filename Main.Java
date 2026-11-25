import java.util.*;

public class Main {
    static class Student {
        int roll, total;
        String name;
        boolean pass;
        Student(int roll, String name, int a, int b, int c) {
            this.roll = roll;
            this.name = name;
            total = a + b + c;
            pass = a >= 40 && b >= 40 && c >= 40;
        }
        double percentage() { return total / 3.0; }
        String grade() {
            double p = percentage();
            return !pass ? "F" : p >= 90 ? "A+" : p >= 75 ? "A" : p >= 60 ? "B" : "C";
        }
        void show() {
            System.out.printf(Locale.US, "%-6d %-18s %3d/300 %6.2f%% %-3s %s%n",
                roll, name, total, percentage(), grade(), pass ? "PASS" : "FAIL");
        }
    }
    static ArrayList<Student> students = new ArrayList<>();
    static HashMap<Integer, Student> index = new HashMap<>();
    static Scanner sc = new Scanner(System.in);
    static int number(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) return n;
            } catch (NumberFormatException e) { }
            System.out.println("Enter a valid number (" + min + " to " + max + ").");
        }
    }
    static void add() {
        int roll = number("Roll number: ", 1, Integer.MAX_VALUE);
        if (index.containsKey(roll)) {
            System.out.println("Roll number already exists."); return;
        }
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        int a = number("Java marks: ", 0, 100);
        int b = number("DSA marks: ", 0, 100);
        int c = number("DBMS marks: ", 0, 100);
        Student s = new Student(roll, name, a, b, c);
        students.add(s);
        index.put(roll, s);
        System.out.println("Student added.");
    }
    static void display() {
        if (students.isEmpty()) System.out.println("No records.");
        else for (Student s : students) s.show();
    }
    static void search() {
        Student s = index.get(number("Search roll: ", 1, Integer.MAX_VALUE));
        if (s == null) System.out.println("Student not found.");
        else s.show();
    }
    static void sort() {
        students.sort(Comparator.comparingInt((Student s) -> s.total).reversed());
        display();
    }
    static void summary() {
        if (students.isEmpty()) { System.out.println("No records."); return; }
        int sum = 0, passed = 0, best = -1;
        for (Student s : students) {
            sum += s.total;
            if (s.pass) { passed++; best = Math.max(best, s.total); }
        }
        System.out.printf(Locale.US, "Students: %d | Passed: %d | Failed: %d | Average: %.2f%%%n",
            students.size(), passed, students.size() - passed, sum / (3.0 * students.size()));
        System.out.println("Topper(s) among passing students:");
        if (best == -1) System.out.println("None");
        else for (Student s : students) if (s.pass && s.total == best) s.show();
    }
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nRESULT ANALYSIS SYSTEM\n1. Add student\n2. Display results\n3. Search by roll\n4. Sort by marks\n5. Class summary\n6. Exit");
            switch (number("Choice: ", 1, 6)) {
                case 1: add(); break;
                case 2: display(); break;
                case 3: search(); break;
                case 4: sort(); break;
                case 5: summary(); break;
                case 6: System.out.println("Goodbye!"); return;
            }
        }
    }
}
