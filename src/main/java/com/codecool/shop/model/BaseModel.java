package com.codecool.shop.model;
import java.lang.reflect.Field;

/**
 * BaseModel class is the top of the hierarchy tree. It has several subclass so it is
 * responsible for the necessary methods (constructor, getter and setter).
 * the necessary ID, name and description and include the getter and setter methods.
 * <p>
 * This class has several subclass, so it count and generate the necessary id, name and description
 * and include the getter and setter methods too.
 *
 * @author gem
 * @version 1.8
 */
public class BaseModel {

    protected int id;
    protected String name;
    protected String description;

    /**
     * Basic constructor with a single parameter.
     *
     * @param name brand name of the instance
     */
    public BaseModel(String name) {
        this.name = name;
    }

    /**
     * Extended constructor with more parameters.
     *
     * @param name brand name of the object
     * @param description short description from the object
     */
    public BaseModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Get the generated ID.
     *
     * @return the generated id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id the new id's for the given object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name to the object.
     *
     * @return the name passed to the constructor
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name variables for the given object.
     *
     * @param name the new name of the object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description passed to the constructor
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the name variables for the given object.
     *
     * @param description the new short description from the object
     */
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(this);
                if (value != null) {
                    sb.append(field.getName() + ":" + value + ",");
                }
            } catch (IllegalAccessException e) {

            }
        }
        return sb.toString();
    }

}
