package net.cazzar.util.glowingoctowookie.internal;

public class MethodInfo {
    private final String owner, name, signature;

    public MethodInfo(String owner, String name, String signature) {
        this.owner = owner;
        this.name = name;
        this.signature = signature;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodInfo)) return false;

        MethodInfo that = (MethodInfo) o;

        return name.equals(that.name) && owner.equals(that.owner) && signature.equals(that.signature);
    }

    @Override
    public int hashCode() {
        int result = owner.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + signature.hashCode();
        return result;
    }

    public static MethodInfo get(String notation, String signature) {
        int index = notation.lastIndexOf('/');
        String owner = notation.substring(0, index);
        String name = notation.substring(index + 1);

        return new MethodInfo(owner, name, signature);
    }

}
