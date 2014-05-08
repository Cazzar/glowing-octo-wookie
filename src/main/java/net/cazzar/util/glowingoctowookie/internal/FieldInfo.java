package net.cazzar.util.glowingoctowookie.internal;

import com.sun.istack.internal.NotNull;

public class FieldInfo {  
    private final String owner;
    private final String name;

    public FieldInfo(@NotNull String owner, @NotNull String name) {
        this.owner = owner;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldInfo)) return false;

        FieldInfo fieldInfo = (FieldInfo) o;

        return name.equals(fieldInfo.name);// && owner.equals(fieldInfo.owner);

    }

    @Override
    public int hashCode() {
//        int result = owner.hashCode();
//        result = 31 * result + name.hashCode();
//        return result;
        return name.hashCode();
    }

    public static FieldInfo get(String notation) {
        int index = notation.lastIndexOf('/');
        String owner = notation.substring(0, index);
        String name = notation.substring(index + 1);

        return new FieldInfo(owner, name);
    }
}
