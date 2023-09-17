package com.wakacast.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReportedCastCall extends BaseClass{
    private String reason;
    private String otherReasons;
    @ManyToOne
    private CastCall castCallReported;
    @OneToOne
    private User castCallReporter;
}
