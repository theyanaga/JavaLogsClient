package parsing.entities.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class NameOfTest {
    private final String name;

    public NameOfTest(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
