package promotions.model;

import javax.persistence.*;

@Entity
@Table(name = "promotii")
public class Promotii {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String someData;

    public Promotii(){}

    public Promotii(String data){
        this.someData = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSomeData() {
        return someData;
    }

    public void setSomeData(String someData) {
        this.someData = someData;
    }
}
