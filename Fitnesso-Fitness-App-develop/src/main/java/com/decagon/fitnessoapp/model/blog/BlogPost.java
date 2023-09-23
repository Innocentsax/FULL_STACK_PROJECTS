package com.decagon.fitnessoapp.model.blog;

import com.decagon.fitnessoapp.model.user.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @com.sun.istack.NotNull
    private String title;

    @com.sun.istack.NotNull
    private String content;

    /*@NotNull
    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;*/
    @com.sun.istack.NotNull
    private String authorName;

    @com.sun.istack.NotNull
    private String image;

    @com.sun.istack.NotNull
    private String contact;

    @com.sun.istack.NotNull
    private String biography;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;
}
