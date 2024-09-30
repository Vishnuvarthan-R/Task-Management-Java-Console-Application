import java.util.*;

class Task implements Comparable<Task> {
    private int id;
    private String title;
    private String description;
    private int priority;
    private Date dueDate;

    public Task(int id, String title, String description, int priority, Date dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int compareTo(Task other) {
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority); // Higher priority first
        }
        return this.dueDate.compareTo(other.dueDate); // Earlier due date first
    }

    @Override
    public String toString() {
        return "Task ID: " + id + ", Title: " + title + ", Priority: " + priority + ", Due: " + dueDate;
    }
}

class TaskManager {
    private int nextId = 1;
    private PriorityQueue<Task> taskQueue;
    private HashMap<Integer, Task> taskMap;

    public TaskManager() {
        taskQueue = new PriorityQueue<>();
        taskMap = new HashMap<>();
    }

    public void addTask(String title, String description, int priority, Date dueDate) {
        Task task = new Task(nextId++, title, description, priority, dueDate);
        taskQueue.add(task);
        taskMap.put(task.getId(), task);
        System.out.println("Task added successfully.");
    }

    public void deleteTask(int id) {
        Task task = taskMap.remove(id);
        if (task != null) {
            taskQueue.remove(task);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    public void updateTask(int id, String title, String description, int priority, Date dueDate) {
        Task task = taskMap.get(id);
        if (task != null) {
            task.setTitle(title);
            task.setDescription(description);
            task.setPriority(priority);
            task.setDueDate(dueDate);
            taskQueue.remove(task);
            taskQueue.add(task);
            System.out.println("Task updated successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    public void displayTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        PriorityQueue<Task> tempQueue = new PriorityQueue<>(taskQueue);
        while (!tempQueue.isEmpty()) {
            System.out.println(tempQueue.poll());
        }
    }
}

public class TaskManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        while (true) {
            System.out.println("Task Management System:");
            System.out.println("1. Add Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Update Task");
            System.out.println("4. View Tasks");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter priority (1-5): ");
                    int priority = scanner.nextInt();
                    System.out.print("Enter due date (yyyy-mm-dd): ");
                    String dateStr = scanner.next();
                    Date dueDate = new GregorianCalendar(
                            Integer.parseInt(dateStr.substring(0, 4)),
                            Integer.parseInt(dateStr.substring(5, 7)) - 1,
                            Integer.parseInt(dateStr.substring(8))
                    ).getTime();

                    taskManager.addTask(title, description, priority, dueDate);
                    break;

                case 2:
                    System.out.print("Enter task ID to delete: ");
                    int deleteId = scanner.nextInt();
                    taskManager.deleteTask(deleteId);
                    break;

                case 3:
                    System.out.print("Enter task ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter new description: ");
                    String newDescription = scanner.nextLine();
                    System.out.print("Enter new priority (1-5): ");
                    int newPriority = scanner.nextInt();
                    System.out.print("Enter new due date (yyyy-mm-dd): ");
                    String newDateStr = scanner.next();
                    Date newDueDate = new GregorianCalendar(
                            Integer.parseInt(newDateStr.substring(0, 4)),
                            Integer.parseInt(newDateStr.substring(5, 7)) - 1,
                            Integer.parseInt(newDateStr.substring(8))
                    ).getTime();

                    taskManager.updateTask(updateId, newTitle, newDescription, newPriority, newDueDate);
                    break;

                case 4:
                    taskManager.displayTasks();
                    break;

                case 5:
                    System.out.println("Exiting Task Management System.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
