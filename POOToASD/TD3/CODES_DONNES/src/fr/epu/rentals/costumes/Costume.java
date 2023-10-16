package fr.epu.rentals.costumes;

import fr.epu.rentals.RentableItem;

public class Costume implements RentableItem {

    private String name;
    private String description;

    private CostumeSize size;
    private boolean available;

    public Costume(String name, String description, CostumeSize size) {
        this.name = name;
        this.description = description;
        this.available = true; // Par défaut, un costume est disponible lors de sa création.
        this.size = size;
    }
    public Costume(String name, String description) {
        this(name, description, CostumeSize.ONE_SIZE);
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean match(String description) {
        return this.description.contains(description) || this.name.contains(description) || this.size.name().contains(description) ;
    }

    public void setUnavailable() {
        this.available = false;
    }

    public void setAvailable() {
        this.available = true;
    }
}

