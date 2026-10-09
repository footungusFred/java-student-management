import java.util.*;

public class StudentManagement {
    static class Student {
        String id, name;
        List<Double> grades = new ArrayList<>();

        Student(String id, String name) { this.id = id; this.name = name; }

        void addGrade(double g) { grades.add(g); }

        double getGPA() {
            if (grades.isEmpty()) return 0.0;
            double sum = 0;
            for (double g : grades) sum += g;
            return sum / grades.size();
        }

        public String toString() {
            return String.format("ID: %s | Name: %-15s | GPA: %.2f", id, name, getGPA());
        }
    }

    static Map<String, Student> students = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    static void addStudent() {
        System.out.print("Student ID: "); String id = sc.nextLine().trim();
        System.out.print("Student Name: "); String name = sc.nextLine().trim();
        if (students.containsKey(id)) { System.out.println("ID already exists."); return; }
        students.put(id, new Student(id, name));
        System.out.println("✅ Student added: " + name);
    }

    static void addGrade() {
        System.out.print("Student ID: "); String id = sc.nextLine().trim();
        Student s = students.get(id);
        if (s == null) { System.out.println("Student not found."); return; }
        System.out.print("Grade (0-100): ");
        try {
            double g = Double.parseDouble(sc.nextLine().trim());
            s.addGrade(g);
            System.out.printf("✅ Grade %.1f added for %s%n", g, s.name);
        } catch (NumberFormatException e) { System.out.println("Invalid grade."); }
    }

    static void searchStudent() {
        System.out.print("Enter name or ID: "); String q = sc.nextLine().trim().toLowerCase();
        boolean found = false;
        for (Student s : students.values()) {
            if (s.id.toLowerCase().contains(q) || s.name.toLowerCase().contains(q)) {
                System.out.println("  " + s);
                found = true;
            }
        }
        if (!found) System.out.println("No student found.");
    }

    static void listAll() {
        if (students.isEmpty()) { System.out.println("No students."); return; }
        System.out.println("
--- Student Records ---");
        students.values().stream()
            .sorted(Comparator.comparing(s -> s.name))
            .forEach(s -> System.out.println("  " + s));
        System.out.println();
    }

    static void removeStudent() {
        System.out.print("Student ID to remove: "); String id = sc.nextLine().trim();
        Student s = students.remove(id);
        if (s == null) System.out.println("Student not found.");
        else System.out.println("✅ Removed: " + s.name);
    }

    public static void main(String[] args) {
        System.out.println("=== Student Management System ===");
        System.out.println("Commands: add | grade | search | list | remove | quit\n");
        while (true) {
            System.out.print("> ");
            String cmd = sc.nextLine().trim().toLowerCase();
            switch (cmd) {
                case "add": addStudent(); break;
                case "grade": addGrade(); break;
                case "search": searchStudent(); break;
                case "list": listAll(); break;
                case "remove": removeStudent(); break;
                case "quit": System.out.println("Goodbye!"); return;
                default: System.out.println("Unknown command.");
            }
        }
    }
}
