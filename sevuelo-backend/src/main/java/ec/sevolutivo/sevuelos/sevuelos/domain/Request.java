package ec.sevolutivo.sevuelos.sevuelos.domain;

import ec.sevolutivo.sevuelos.sevuelos.domain.enumeration.RequestStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "passenger", length = 100, nullable = false)
    private String passenger;

    @NotNull
    @Size(max = 100)
    @Column(name = "destination", length = 100, nullable = false)
    private String destination;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @Size(max = 100)
    @Column(name = "comments", length = 100)
    private  String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassenger() {
        return this.passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", passenger='" + passenger + '\'' +
                ", destination='" + destination + '\'' +
                ", status=" + status +
                ", comments='" + comments + '\'' +
                '}';
    }
}
