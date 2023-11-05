
package Tasks;

public class Task {
     private int id;
     private String description;
     private String state;

    public Task() {
    }

    public Task(int id, String description, String state) {
        this.id = id;
        this.description = description;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
     
     
}
