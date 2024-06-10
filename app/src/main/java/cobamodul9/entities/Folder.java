package cobamodul9.entities;

public class Folder{
    public int id;
    public String name;

    public Folder(String name) {
        this.name = name;
    }

    public Folder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
   
}