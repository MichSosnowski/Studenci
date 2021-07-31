package Database;

import java.io.Serializable;

public class Groups implements Serializable {
    private String term;

    public Groups(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
