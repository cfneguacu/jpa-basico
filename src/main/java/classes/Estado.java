package classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sigla;

    @OneToMany(
            mappedBy = "estado",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Aluno> alunos = new ArrayList<>();

    public Estado(){}

    public Estado(String nome, String sigla){
        this.nome = nome;
        this.sigla = sigla;
    }

    public Estado(String nome, String sigla, List<Aluno> alunos){
        this.nome = nome;
        this.sigla = sigla;
        this.alunos = alunos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estado{");
        sb.append("id=").append(id);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", sigla=").append(sigla);
        sb.append(", alunos='").append(alunos).append('\'');
        sb.append("}");
        return sb.toString();
    }
}
